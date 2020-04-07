package com.hrpc;

import org.apache.hadoop.ipc.ProtocolSignature;

import java.io.IOException;

public  class HiServerImpl implements HiServer {

       @Override
    public long getProtocolVersion(String s, long l) throws IOException {
        return versionID;
    }

    @Override
    public ProtocolSignature getProtocolSignature(String s, long l, int i) throws IOException {
        return new ProtocolSignature(HiServer.versionID,null);
    }


    @Override
    public int sayHi(String message) {

        System.out.println("asdasdad");
        if("a".equals(message)){
            return 1;
        }else{
            return 2;
        }
    }
}
