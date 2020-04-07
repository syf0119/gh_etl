package com.wondertek.bigdata.offline.mr.etl;

import com.wondertek.bigdata.offline.common.EventLogConstants;
import com.wondertek.bigdata.offline.common.GlobalConstants;
import com.wondertek.bigdata.offline.util.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.MultiTableOutputFormat;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;


public class AnalyserLogDataRunner implements Tool {
    private Configuration conf;
    private static final Logger logger = Logger.getLogger(AnalyserLogDataMapper.class);


    @Override
    public int run(String[] strings) throws Exception {


        Configuration conf = this.getConf();
        this.processArgs(conf,strings);


        //开始创建job
        Job job = Job.getInstance(conf);
        job.setJobName("AnalyserLogDataETL");
        this.initJob(job);
        this.setInputPaths(job);

        job.setMapperClass(AnalyserLogDataMapper.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
        job.setMapOutputValueClass(Put.class);
        job.setNumReduceTasks(0);



        TableMapReduceUtil.addDependencyJars(job);

        job.setOutputFormatClass(MultiTableOutputFormat.class);


       return  job.waitForCompletion(true)?0:1;



    }

    /**
     *
     * @param job
     * @throws IOException
     */
    private void setInputPaths(Job job) throws IOException {
        Configuration configuration = job.getConfiguration();
        FileSystem fileSystem = FileSystem.get(configuration);
        String date = configuration.get(GlobalConstants.RUNNING_DATE_PARAMES);
        String hdfsPath = TimeUtil.parseLong2String(TimeUtil.parseString2Long(date), "yyyy/MM/dd");
        hdfsPath=GlobalConstants.HDFS_LOGS_PATH_PREFIX+hdfsPath;
        // TODO: 2020.3.31 测试
        hdfsPath="/flume/syf";

        Path inputPath = new Path(hdfsPath);
        if (fileSystem.exists(inputPath)){

            FileInputFormat.addInputPath(job,inputPath);
            logger.debug("输入路径是"+hdfsPath);
        }else{
            throw new FileNotFoundException("文件路径不存在："+hdfsPath);
        }



    }

    /**
     *
     * @param job
     * @throws IOException
     */
    private void initJob(Job job) throws IOException {
        // hbase表后缀
        String tableNameSuffix = TimeUtil.parseLong2String(
                TimeUtil.parseString2Long(job.getConfiguration().get(GlobalConstants.RUNNING_DATE_PARAMES)),
                TimeUtil.HBASE_TABLE_NAME_SUFFIX_FORMAT);
        String eventLogsStr= EventLogConstants.HBASE_NAME_EVENT_LOGS+GlobalConstants.UNDERLINE+tableNameSuffix;
        boolean override = conf.getBoolean(GlobalConstants.RUNNING_OVERRIDE_ETL_HBASE_TABLE, true);
        Connection conn = ConnectionFactory.createConnection(job.getConfiguration());
        Admin admin=null;
        try {
            admin=conn.getAdmin();
            TableName eventLogs = TableName.valueOf(eventLogsStr);
            createTable(admin,eventLogs,override);


        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(admin!=null){
                admin.close();
            }
            if(conn!=null){
                conn.close();
            }
        }


    }

    private  void createTable(Admin admin, TableName tableName, boolean override) throws IOException {
        HTableDescriptor htd = new HTableDescriptor(tableName);
        htd.addFamily(new HColumnDescriptor(EventLogConstants.EVENT_LOGS_FAMILY_NAME));
        // 表是否存在
        if (admin.tableExists(tableName)) {
            logger.debug("创建表："+tableName.toString());
            if (override) {
                if (admin.isTableEnabled(tableName)) {
                    admin.disableTable(tableName);
                }
                admin.deleteTable(tableName);
                admin.createTable(htd);
            }
        } else {
            admin.createTable(htd);

        }
    }


    /**
     * 处理输入参数，日期的默认值为昨天
     *
     * @param conf
     * @param args 参数格式：-d 2016-02-02,如果具体日志没有，默认date是昨天
     */
    private void processArgs(Configuration conf, String[] args) {
        String date = null;
        for (int i = 0; i < args.length; i++) {
            if ("-d".equals(args[i])) {
                if (i + 1 < args.length) {
                    date = args[++i];
                    break;
                }
            }
        }
        // 如果参数为空，定义默认参数date为昨天
        if (StringUtils.isBlank(date) || !TimeUtil.isValidateRunningDate(date)) {
            // 默认给定昨天
            date = TimeUtil.getYesterday();
        }
        conf.set(GlobalConstants.RUNNING_DATE_PARAMES, date);
    }

    @Override
    public void setConf(Configuration configuration) {
        this.conf= HBaseConfiguration.create();

    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }
}
