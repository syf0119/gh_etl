package com.wondertek.logmake;

import org.apache.commons.lang.time.FastDateFormat;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogMaker {
    public static void main(String[] args) throws IOException, InterruptedException {

        File logFile=new File("F://access_20200330");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true)));


        for (int i = 0; i < 500; i++) {
            Thread.sleep(1000);
            String log = LogRecord.getLog();
            System.out.println(log);
            System.out.println("-------------------------------");

        }

        writer.flush();
        writer.close();





    }
}
