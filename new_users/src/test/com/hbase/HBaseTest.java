package com.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;

public class HBaseTest {


    @Test
    public void test() throws IOException, InterruptedException {
//        Configuration configuration = HBaseConfiguration.create();
//        // configuration.set("hbase.zookeeper.quorum", "host144:2181");
//
//        Connection conn = ConnectionFactory.createConnection(configuration);
//        Table table = conn.getTable(TableName.valueOf("event_logs_20200330"));
//        Scan scan = new Scan();
//        ResultScanner results = table.getScanner(scan);
//        for (Result result : results) {
//            byte[] row = result.getRow();
//            System.out.println(Bytes.toInt(row));
//
//            Cell[] cells = result.rawCells();
//
//            for (Cell cell : cells) {
//                String value = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
//                System.out.print(value + ":");
//                System.out.print(Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()) + "    ");
//            }
//            Thread.sleep(1000);
//            //  System.out.println("+++++++++++++++++++++++++++");
//
//        }


    }

}
