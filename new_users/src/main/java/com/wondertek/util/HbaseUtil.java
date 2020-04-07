package com.wondertek.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public class HbaseUtil {

    public static void main(String[] args) throws IOException {

       // Configuration configuration = HBaseConfiguration.create();
        Configuration configuration = new Configuration();
        configuration.addResource("hbase-site.xml");
        Connection connection = ConnectionFactory.createConnection(configuration);



        Table table = connection.getTable(TableName.valueOf("event_logs_" + DateUtil.getYesterday()));
        TableName name = table.getName();
        System.out.println(name);

        table.close();
        connection.close();
    }
}
