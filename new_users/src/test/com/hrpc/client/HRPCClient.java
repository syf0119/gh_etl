package com.hrpc.client;



import com.hrpc.HiServer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;


public class HRPCClient {

    public static void main(String[] args) throws IOException {
      //  long version = RPC.getProtocolVersion(HiServer.class);
        HiServer hiServer = RPC.getProxy(HiServer.class, 123L, new InetSocketAddress("localhost", 8889), new Configuration());
//        int id = hiServer.sayHi("a");
//        System.out.println(id);
        //System.out.println(hiServer);

        int sayHi = hiServer.sayHi("eqwe");
        System.out.println(sayHi);


    }
}