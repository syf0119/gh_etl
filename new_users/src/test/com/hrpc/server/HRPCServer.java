package com.hrpc.server;

import com.hrpc.HiServer;
import com.hrpc.HiServerImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetAddress;

public class HRPCServer {
    public static void main(String[] args) throws IOException {
        RPC.Builder builder = new RPC.Builder(new Configuration());
        builder.setProtocol(HiServer.class);
        builder.setInstance(new HiServerImpl());
        builder.setBindAddress("localhost");
        builder.setNumHandlers(5);
        builder.setPort(8889);





        RPC.Server server = builder.build();

        int port = server.getPort();
        System.out.println(port);
        String hostName = InetAddress.getLocalHost().getHostAddress();
        System.out.println(hostName);


        server.start();
        System.out.println("start server");


       // server.stop();


    }
}
