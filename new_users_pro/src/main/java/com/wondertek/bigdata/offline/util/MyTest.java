package com.wondertek.bigdata.offline.util;

import org.apache.log4j.Logger;

import static org.apache.log4j.Logger.getLogger;
import java.io.IOException;

public class MyTest {
    public static void main(String[] args) throws IOException {
//        IPSeekerExt.RegionInfo info = IPSeekerExt.getInstance().analysisIp("42.236.186.228");
//        System.out.println(info);
        //long time = new Date().getTime();

      //  System.out.println("^A");

//        Configuration configuration = new Configuration();
//        String runningDate = configuration.get("RUNNING_DATE");
       // System.out.println(TimeUtil.getYesterday());

//        String log="36.57.60.252^A1585637736407^A/college.png?en=Search&pl=H5&app_id=lDNewS&ch_id=0&c_ver=2.3.0&c_user_id=0a826cb5-3467-46af-921a-05bd5b520ecd&user_id=21&c_s_id=30&c_time=20200331&net_t=4G";
//        Map<String, String> clientInfo = LoggerUtil.handleLog(log);
//        System.out.println(clientInfo);
//
//        String eventAliasName = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME);
//        System.out.println(eventAliasName);
//        EventLogConstants.EventEnum event = EventLogConstants.EventEnum.valueOfAlias(eventAliasName);
//        System.out.println(event);
//
//        Configuration conf = HBaseConfiguration.create();
//        Connection connection = ConnectionFactory.createConnection(conf);
//
//        Scan scan=new Scan();
//        Table table = connection.getTable(TableName.valueOf("event_logs_20200330"));
//        ResultScanner scanner = table.getScanner(scan);

        Logger logger = getLogger(MyTest.class);
        logger.warn("\033[31;0m" + "1 好好学习" + "\033[0m");




    }
}
