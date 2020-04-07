package com.wondertek.bigdata.offline.mr;

import com.wondertek.bigdata.offline.common.GlobalConstants;
import com.wondertek.bigdata.offline.common.KpiEnum;
import com.wondertek.bigdata.offline.dimension.key.KeyBaseDimension;
import com.wondertek.bigdata.offline.dimension.value.BaseStatsValueWritable;
import com.wondertek.bigdata.offline.mr.stats.collector.IOutputCollector;
import com.wondertek.bigdata.offline.service.converter.IDimensionConverter;
import com.wondertek.bigdata.offline.service.converter.client.DimensionConverterClient;
import com.wondertek.bigdata.offline.util.JdbcManager;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 自定义输出到MySQL的outputformat类
 */
public class TransformerOutputFormat extends OutputFormat<KeyBaseDimension, BaseStatsValueWritable> {

    /**
     * 返回一个具体定义如何输出数据的对象, recordwriter被称为数据的输出器
     * getRecordWriter用于返回一个RecordWriter的实例，Reduce任务在执行的时候就是利用这个实例来输出Key/Value的。
     * （如果Job不需要Reduce，那么Map任务会直接使用这个实例来进行输出。）
      */
    @Override
    public RecordWriter<KeyBaseDimension, BaseStatsValueWritable> getRecordWriter(TaskAttemptContext context)
            throws IOException, InterruptedException {
        Configuration conf = context.getConfiguration();
        /**
         * 使用RPC方式创建converter，很重要，通过配置获取维度id
         */
        IDimensionConverter converter = DimensionConverterClient.createDimensionConverter(conf);
        Connection conn ;

        try {
            conn = JdbcManager.getConnection(conf, GlobalConstants.WAREHOUSE_OF_REPORT);
            // 关闭自动提交机制
            conn.setAutoCommit(false);
        } catch (Exception e) {
            throw new RuntimeException("获取数据库连接失败", e);
        }
        return new TransformerRecordWriter(conn, conf, converter);
    }

    /**
     * 执行reduce时，会验证输出目录是否存在，
     * checkOutputSpecs是 在JobClient提交Job之前被调用的（在使用InputFomat进行输入数据划分之前），用于检测Job的输出路径。
     * 比如，FileOutputFormat通过这个方法来确认在Job开始之前，Job的Output路径并不存在，然后该方法又会重新创建这个Output 路径。
     * 这样一来，就能确保Job结束后，Output路径下的东西就是且仅是该Job输出的。
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {
        // 这个方法在自己实现的时候不需要关注，如果你非要关注，最多检查一下表数据存在

    }

    /**
     * getOutputCommitter则 用于返回一个OutputCommitter的实例
     * @param context
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        return new FileOutputCommitter(FileOutputFormat.getOutputPath(context), context);
    }

    /**
     * 自定义的数据输出器
     */
    public class TransformerRecordWriter extends RecordWriter<KeyBaseDimension, BaseStatsValueWritable> {

        private Connection conn = null;
        private Configuration conf = null;
        private IDimensionConverter converter = null;
        private Map<KpiEnum, PreparedStatement> kpiTypeSQLMap = new HashMap<>();
        private Map<KpiEnum, Integer> batchMap = new HashMap<>();

        public TransformerRecordWriter(Connection conn, Configuration conf, IDimensionConverter converter) {
            super();
            this.conn = conn;
            this.conf = conf;
            this.converter = converter;
        }

        /**
         * 输出数据, 当在reduce中调用context.write方法的时候，底层调用的是该方法
         * 将Reduce输出的Key/Value写成特定的格式
         * @param key
         * @param value
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        public void write(KeyBaseDimension key, BaseStatsValueWritable value)
                throws IOException, InterruptedException {

            KpiEnum kpiEnum = value.getKpi();

            String sql = this.conf.get(kpiEnum.name);
            PreparedStatement pstmt;
            int count = 1;
            try {
                if (kpiTypeSQLMap.get(kpiEnum) == null) {
                    // 第一次创建
                    pstmt = this.conn.prepareStatement(sql);
                    kpiTypeSQLMap.put(kpiEnum, pstmt);
                } else {
                    // 标示已经存在
                    pstmt = kpiTypeSQLMap.get(kpiEnum);
                    if (!batchMap.containsKey(kpiEnum)) {
                        batchMap.put(kpiEnum, count);
                    }
                    count = batchMap.get(kpiEnum);
                    count++;
                }
                batchMap.put(kpiEnum, count);

                // 针对特定的MR任务有特定的输出器:IOutputCollector
                String collectorClassName = conf.get(GlobalConstants.OUTPUT_COLLECTOR_KEY_PREFIX + kpiEnum.name);
                Class<?> clazz = Class.forName(collectorClassName);
                // 创建对象, 要求实现子类一定要有一个无参数的构造方法
                IOutputCollector collector = (IOutputCollector) clazz.newInstance();
                collector.collect(conf, key, value, pstmt, converter);


                // 批量提交
                if (count % conf.getInt(GlobalConstants.JDBC_BATCH_NUMBER, GlobalConstants.DEFAULT_JDBC_BATCH_NUMBER) == 0) {
                    pstmt.executeBatch(); // 批量提交
                    conn.commit();
                    batchMap.remove(kpiEnum); // 移除已经存在的输出数据
                }
            } catch (Exception e) {
                throw new IOException("数据输出产生异常", e);
            }
        }

        /**
         * 关闭资源使用，最终一定会调用
         * 负责对输出做最后的确认并关闭输出
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        public void close(TaskAttemptContext context) throws IOException, InterruptedException {
                try {
                try {
                    for (Map.Entry<KpiEnum, PreparedStatement> entry : this.kpiTypeSQLMap.entrySet()) {
                        entry.getValue().executeBatch();
                    }
                } catch (Exception e) {
                    throw new IOException("输出数据出现异常", e);
                } finally {
                    try {
                        if (conn != null) {
                            conn.commit();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (conn != null) {
                            for (Map.Entry<KpiEnum, PreparedStatement> entry : this.kpiTypeSQLMap.entrySet()) {
                                try {
                                    entry.getValue().close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } finally {
                // 关闭远程连接
                DimensionConverterClient.stopDimensionConverterProxy(converter);
            }
        }

    }

}
