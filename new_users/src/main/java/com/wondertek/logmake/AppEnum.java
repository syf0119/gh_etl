package com.wondertek.logmake;

import java.util.Random;

public enum AppEnum {
//1：电网头条
//2：辽电头条
//3：临港头条
//4：华为VR音乐

    StateGridNews("电网头条",1),
    lDNewS("辽电头条",2),
    LGNews("临港头条",3),
    HWVRMusic("华为VR音乐",4);
    public String name;
    public int id;
    AppEnum(String name,int id){
        this.name=name;
        this.id=id;
    }

    public static AppEnum getNextEv(){

        Random random = new Random();
        int nextInt = random.nextInt(4);
        AppEnum[] appEnums = AppEnum.values();
        return appEnums[nextInt];





    }



}
