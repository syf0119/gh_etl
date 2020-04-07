package com.wondertek;

import com.wondertek.bigdata.offline.common.DateEnum;
import com.wondertek.bigdata.offline.dimension.key.base.DateDimensionKey;
import com.wondertek.bigdata.offline.service.converter.IDimensionConverter;
import com.wondertek.bigdata.offline.service.converter.client.DimensionConverterClient;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;

public class ConverterTest {
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        IDimensionConverter proxy = RPC.getProxy(IDimensionConverter.class, IDimensionConverter.versionID, new InetSocketAddress("localhost", 8889), conf);


        DateDimensionKey dateDimensionKey = DateDimensionKey.buildDate(new Date().getTime(), DateEnum.DAY);

        int id = proxy.getDimensionIdByValue(dateDimensionKey);
        System.out.println(id);


    }
}
