package com.wondertek.bigdata.offline.mr.stats.newusers;

import com.wondertek.bigdata.offline.common.EventLogConstants;
import com.wondertek.bigdata.offline.common.GlobalConstants;
import com.wondertek.bigdata.offline.dimension.key.stats.StatsInstallUser;
import com.wondertek.bigdata.offline.mr.TransformerOutputFormat;
import com.wondertek.bigdata.offline.util.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;

import java.io.IOException;
import java.util.ArrayList;

public class StatsInstallUserRunner implements Tool {

    private Configuration conf;

    @Override
    public int run(String[] strings) throws Exception {

        processArgs(this.conf,strings);

        Job job = Job.getInstance(this.conf);
        job.setJobName(this.getClass().getName());

        job.setJarByClass(StatsInstallUser.class);

        job.setMapperClass(StatsInstallUserMapper.class);

        job.setReducerClass(StatsInstallUserReducer.class);



        this.initHBaseConfig(job);

        job.setOutputFormatClass(TransformerOutputFormat.class);

        job.setNumReduceTasks(1);

        if (job.waitForCompletion(true)) {
            this.calculateTotalUsers(conf);
            return 0;
        } else {
            return -1;
        }


    }

    private void calculateTotalUsers(Configuration conf) {
    }

    private void initHBaseConfig(Job job) throws IOException {

        FilterList filterList = new FilterList();

        filterList.addFilter(new SingleColumnValueFilter(EventLogConstants.EVENT_LOGS_FAMILY_NAME_BYTES,
                Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME),
                CompareFilter.CompareOp.EQUAL, Bytes.toBytes(EventLogConstants.EventEnum.LAUNCH.name)
        ));

        filterList.addFilter(new SingleColumnValueFilter(EventLogConstants.EVENT_LOGS_FAMILY_NAME_BYTES,
                Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_USER_ID),
                CompareFilter.CompareOp.EQUAL, new NullComparator()

        ));


        String[] columns = new String[]{
                // 不管mapper中是否用到event的值，在column中都必须有
                EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME,
                EventLogConstants.LOG_COLUMN_NAME_APP,
                EventLogConstants.LOG_COLUMN_NAME_PLATFORM,
                EventLogConstants.LOG_COLUMN_NAME_CHANNEL,
                EventLogConstants.LOG_COLUMN_NAME_VERSION,
                EventLogConstants.LOG_COLUMN_NAME_COUNTRY,
                EventLogConstants.LOG_COLUMN_NAME_PROVINCE,
                EventLogConstants.LOG_COLUMN_NAME_CITY,
                EventLogConstants.LOG_COLUMN_NAME_ISP,
                EventLogConstants.LOG_COLUMN_NAME_UUID,
        };


        filterList.addFilter(this.getColumnFilter(columns));

        String runDate = conf.get(GlobalConstants.RUNNING_DATE_PARAMES);

        Connection conn = null;
        Admin admin = null;
        ArrayList<Scan> scans = new ArrayList<>();

        try {
            conn = ConnectionFactory.createConnection(this.conf);
            admin = conn.getAdmin();
            String tableName = EventLogConstants.HBASE_NAME_EVENT_LOGS + GlobalConstants.UNDERLINE + runDate.replace(GlobalConstants.KEY_SEPARATOR, "");
            if(admin.tableExists(TableName.valueOf(tableName))){
                 Scan scan = new Scan();
                scan.setAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME,Bytes.toBytes(tableName));
                scan.setFilter(filterList);

                scans.add(scan);


            }else{
                throw new TableNotFoundException(tableName);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (admin != null) {
                    admin.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        TableMapReduceUtil.initTableMapperJob(scans,StatsInstallUserMapper.class, StatsInstallUser.class, Text.class,job);


    }


    /**
     * 指定多个列名（Qualifier）前缀
     *
     * @param columns
     * @return
     */
    private Filter getColumnFilter(String[] columns) {
        int length = columns.length;
        byte[][] filter = new byte[length][];
        for (int i = 0; i < length; i++) {
            filter[i] = Bytes.toBytes(columns[i]);
        }
        return new MultipleColumnPrefixFilter(filter);
    }

    /**
     * 处理参数
     *
     * @param conf
     * @param args
     */
    private void processArgs(Configuration conf, String[] args) {
        String date = null;
        for (int i = 0; i < args.length; i++) {
            if ("-d".equals(args[i])) {
                if (i + 1 < args.length) {
                    date = args[++i];
                }
            }
        }
        //如果没有给出具体时间，则默认时间为查询时间当日的前一天
        if (StringUtils.isBlank(date) || !TimeUtil.isValidateRunningDate(date)) {
            date = TimeUtil.getYesterday();
        }
        conf.set(GlobalConstants.RUNNING_DATE_PARAMES, date);
    }


    @Override
    public void setConf(Configuration configuration) {
        configuration.addResource("output-collector.xml");
        configuration.addResource("query-mapping.xml");
        configuration.addResource("transformer-env.xml");
        this.conf = HBaseConfiguration.create(configuration);


    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }
}
