package com.wondertek.logmake;

import java.util.Random;

public enum PlEnum {

    Android("android",1),
    IOS("IOS",2),
    H5("H5",3),
    OMS("OMS",4);

    public final int id;
    public final String name;

    PlEnum( String name,int id) {
        this.id = id;
        this.name = name;
    }

    public static PlEnum getNextEv(){

        Random random = new Random();
        int nextInt = random.nextInt(4);
        PlEnum[] plEnums = PlEnum.values();
        return plEnums[nextInt];

    }


}
