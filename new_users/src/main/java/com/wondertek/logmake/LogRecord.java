package com.wondertek.logmake;

import com.wondertek.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class LogRecord {
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");


    private  static  final Random random = new Random();
    private final String head = RandomIp.getRandomIp()+"^A"+ new Date().getTime()+"^A"+"/college.png?";
    private EnEnum en = EnEnum.getNextEv();
    private int  pl =new Random().nextInt(4)+1;
    private String app_id = String.valueOf(random.nextInt(4)+1);
    private String  ch_id = ChannelEnum.getRandomChannel();
    private String c_ver = (random.nextInt(3)+1) + "." + random.nextInt(5) + "." + random.nextInt(4);
    private String c_user_id =UUID.randomUUID().toString();
    private String user_id=String.valueOf(random.nextInt(9) + random.nextInt(9) +  random.nextInt(9)+  random.nextInt(9)+  random.nextInt(9));
    private String c_time= simpleDateFormat.format(new Date());
    private String net_t=new Date().getTime()%2==0?"WIFI":"4G";
    public String toLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(head)
                .append(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME).append("=").append(this.en.name).append("&")
                .append(EventLogConstants.LOG_COLUMN_NAME_PLATFORM).append("=").append(this.pl).append("&")
                .append(EventLogConstants.LOG_COLUMN_NAME_APP).append("=").append(this.app_id).append("&")
                .append(EventLogConstants.LOG_COLUMN_NAME_CHANNEL).append("=").append(this.ch_id).append("&")
                .append(EventLogConstants.LOG_COLUMN_NAME_VERSION).append("=").append(this.c_ver).append("&")
                //.append("c_user_id").append("=").append(this.c_user_id).append("&")
                .append(EventLogConstants.LOG_COLUMN_NAME_USER_ID).append("=").append(this.getUser_id()).append("&")
                //.append("c_s_id").append("=").append(this.c_s_id).append("&")
                .append(EventLogConstants.LOG_COLUMN_NAME_CLIENT_TIME).append("=").append(this.c_time).append("&")
                .append(EventLogConstants.LOG_COLUMN_NAME_NET_TYPE).append("=").append(this.net_t).append("&")
                .append(EventLogConstants.LOG_COLUMN_NAME_UUID).append("=").append(UUID.randomUUID().toString());



        return sb.toString();


    }
    private  String getUser_id(){
        if(this.c_user_id.endsWith("5")) return "";
        return this.user_id;
    }

    public static String getLog(){

        return  new LogRecord().toLog();
    }



}
