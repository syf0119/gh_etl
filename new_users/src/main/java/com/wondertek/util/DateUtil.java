package com.wondertek.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private DateUtil(){}


    private  static SimpleDateFormat simpleDateFormat;
    static {
        simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
    }

    public   static String formatDate2String(Date date){
            return simpleDateFormat.format(date);

    }
    public static String getYesterday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-11);
        Date time = calendar.getTime();
        return simpleDateFormat.format(time);
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.formatDate2String(new Date()));

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar);

        calendar.add(Calendar.DATE,-1);
        Date time = calendar.getTime();
        System.out.println(simpleDateFormat.format(time));


    }
}
