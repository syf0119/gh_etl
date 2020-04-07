package com.wondertek.bigdata.offline.common;

/**
 * @author zhangcheng
 */
public enum AppEnum {
    STATEGRID("1","电网头条"),
    LIAODIAN("2","辽电头条"),
    LINGANG("3","临港头条"),
    HUAWEI("4","华为VR音乐");


    public final String alias;
    public final String name;

    AppEnum(String alias,String name ) {
        this.alias = alias;
        this.name = name;
    }

    public static AppEnum valueOfAlias(String alias) {
        for (AppEnum type : values()) {
            if (type.alias.equals(alias)) {
                return type;
            }
        }
        return null;
    }

}
