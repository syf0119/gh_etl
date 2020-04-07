package com.wondertek.mr;

import com.wondertek.common.EventLogConstants;
import com.wondertek.demension.NewUsersKpi;
import com.wondertek.util.DateUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class NewUsersRunner extends Configured implements Tool {



    public static void main(String[] args) throws Exception {

        NewUsersRunner newUsersRunner = new NewUsersRunner();
        int run = ToolRunner.run(newUsersRunner, args);
        if (run == 0) System.out.println("complete");
        else System.out.println("job fail");


        Configuration conf = newUsersRunner.getConf();
       String newUsers = conf.get("collector_channel_new_users");
        System.out.println(newUsers);
    }

    @Override
    public int run(String[] strings) throws Exception {

        super.setConf(HBaseConfiguration.create());
        Configuration conf = super.getConf();
        DBConfiguration.configureDB(conf,"com.mysql.jdbc.Driver","jdbc:mysql://192.168.1.181:13306/syf_test","cloud","cloud");

        Job job = Job.getInstance(conf);

        job.setJarByClass(NewUsersRunner.class);


        job.setMapperClass(NewUsersMapper.class);
        job.setMapOutputKeyClass(NewUsersKpi.class);
        job.setMapOutputValueClass(NullWritable.class);


        job.setReducerClass(NewUsersReducer.class);
//        job.setOutputKeyClass(NewUsersKpi.class);
//        job.setOutputValueClass(IntWritable.class);

        job.setOutputFormatClass(DBOutputFormat.class);

        FileOutputFormat.setOutputPath(job,new Path("dasda"));

        job.setNumReduceTasks(1);

        //FileOutputFormat.setOutputPath(job, new Path("F:\\data"));

        DBOutputFormat.setOutput(job,"new_user_kpi","app_id","platform_id","channel","key_date","new_user_num");


        this.setHBaseInputConfig(job);


        boolean completion = job.waitForCompletion(true);

        return completion ? 0 : 1;
    }

//    @Override
//    public void setConf(Configuration configuration) {
//
//
//        //configuration.addResource("output-collector.xml");
//
//
//
//
//        this.conf = new Configuration();
//        configuration.addResource("hbase-site.xml");
//
//
//    }
//
//    @Override
//    public Configuration getConf() {
//        return this.conf;
//    }

    private void setHBaseInputConfig(Job job) throws IOException {
        FilterList filterList = new FilterList();

        filterList.addFilter(
                new SingleColumnValueFilter(EventLogConstants.EVENT_LOGS_FAMILY_NAME_BYTES,
                        Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME),
                        CompareFilter.CompareOp.EQUAL, Bytes.toBytes(EventLogConstants.EventEnum.LAUNCH.alias)));

        //过滤出p_ver列值为空的记录，p_ver如果不为空，则代表是客户端版本升级，不应该再算一次新增用户
        filterList.addFilter(
                new SingleColumnValueFilter(EventLogConstants.EVENT_LOGS_FAMILY_NAME_BYTES,
                        Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_P_VER),
                        CompareFilter.CompareOp.EQUAL, new NullComparator()));

        //user_id如果不为空的话，代表是注册帐号，所以这里过滤掉
        filterList.addFilter(
                new SingleColumnValueFilter(EventLogConstants.EVENT_LOGS_FAMILY_NAME_BYTES,
                        Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_USER_ID),
                        CompareFilter.CompareOp.EQUAL, new NullComparator()));

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
                EventLogConstants.LOG_COLUMN_NAME_USER_ID,
                EventLogConstants.LOG_COLUMN_NAME_P_VER
        };
        filterList.addFilter(this.getColumnFilter(columns));

        //  String runDate = conf.get(GlobalConstants.RUNNING_DATE_PARAMES);
        Connection conn;
        Admin admin = null;
        List<Scan> scanList = new ArrayList<>();
        try {
            conn = ConnectionFactory.createConnection(super.getConf());
            admin = conn.getAdmin();
            String tableName = "event_logs_"+ DateUtil.getYesterday();
            if (admin.tableExists(TableName.valueOf(tableName))) {
                Scan scan = new Scan();
                scan.setAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME, Bytes.toBytes(tableName));
                scan.setFilter(filterList);
                scanList.add(scan);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("创建HBaseAdmin发生异常", e);
        } finally {
            if (admin != null) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (scanList.isEmpty()) {
            throw new IOException("没有表存在，无法创建scan集合");
        }

        TableMapReduceUtil.initTableMapperJob(scanList, NewUsersMapper.class,
                NewUsersKpi.class, NullWritable.class, job, false);


    }


    private Filter getColumnFilter(String[] columns) {
        int length = columns.length;
        byte[][] filter = new byte[length][];
        for (int i = 0; i < length; i++) {
            filter[i] = Bytes.toBytes(columns[i]);
        }
        return new MultipleColumnPrefixFilter(filter);
    }
}
