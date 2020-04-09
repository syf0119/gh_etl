package com.wondertek;

import com.wondertek.bigdata.offline.common.EventLogConstants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;

public class HBaseTest {


    @Test
    public void test() throws IOException, InterruptedException {

        FilterList filterList = new FilterList();

        filterList.addFilter(new SingleColumnValueFilter(EventLogConstants.EVENT_LOGS_FAMILY_NAME_BYTES,
                Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME),
                CompareFilter.CompareOp.EQUAL, Bytes.toBytes(EventLogConstants.EventEnum.LAUNCH.alias)
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
        Configuration configuration = HBaseConfiguration.create();
        // configuration.set("hbase.zookeeper.quorum", "host144:2181");

        String tableName="event_logs_20200406";
       // String family="info";

        Connection conn = ConnectionFactory.createConnection(configuration);
        Table table = conn.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        scan.setFilter(filterList);
//        scan.setFilter(new PrefixFilter(Bytes.toBytes("e_la")));
//        scan.setFilter(new SingleColumnValueFilter(Bytes.toBytes(family),Bytes.toBytes("en"), CompareFilter.CompareOp.EQUAL,Bytes.toBytes("e_la")));
        //scan.setAttribute(tableName,Bytes.toBytes(tableName));



        ResultScanner results = table.getScanner(scan);




        for (Result result : results) {
            byte[] row = result.getRow();
            System.out.println(Bytes.toInt(row));

            Cell[] cells = result.rawCells();

            for (Cell cell : cells) {
                String value = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                System.out.print(value + ":");
                System.out.print(Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()) + "    ");
            }
            Thread.sleep(1000);
            //  System.out.println("+++++++++++++++++++++++++++");

        }


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
