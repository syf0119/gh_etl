package com.other;

import org.apache.hadoop.ipc.VersionedProtocol;

import java.io.IOException;

public interface MyProtocol extends VersionedProtocol {

     long versionID = 1L ;
    public String echo() throws IOException;

}