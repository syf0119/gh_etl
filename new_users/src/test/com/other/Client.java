package com.other;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Client {


    public static void main(String[] args) throws IOException {
        InetSocketAddress addr = new InetSocketAddress("localhost", 9000) ;
        MyProtocol proxy = RPC.getProxy(MyProtocol.class, MyProtocol.versionID, addr,
                new Configuration()) ;

        System.out.println(proxy);
        proxy.echo() ;
    }
}
