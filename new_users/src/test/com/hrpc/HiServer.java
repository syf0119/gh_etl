package com.hrpc;

        import org.apache.hadoop.ipc.VersionedProtocol;

public interface HiServer extends VersionedProtocol {
    long versionID=123L;
    int sayHi(String message);




}
