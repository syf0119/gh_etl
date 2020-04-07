package com.wondertek.logmake;

import java.util.Random;

public enum EnEnum {

    Launch("e_la", 0),

    PageView("e_pv", 1),
    Search("e_st", 2),
    Start("e_au", 3);

    public final int id;
    public final String name;


    EnEnum(String name, int id) {
        this.id = id;
        this.name = name;
    }
    public static EnEnum getNextEv(){

        Random random = new Random();
        int nextInt = random.nextInt(4);
        EnEnum[] evEnums = EnEnum.values();
        return evEnums[nextInt];




    }


}
