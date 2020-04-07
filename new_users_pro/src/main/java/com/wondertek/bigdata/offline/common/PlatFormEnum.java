package com.wondertek.bigdata.offline.common;

/**
 * @author zhangcheng
 */
public enum PlatFormEnum {
    ANDROID("1","android"),
    IOS("2","ios"),
    H5("3","h5"),
    OMS("4","oms");

    public final String alias;
    public final String name;


    PlatFormEnum( String alias, String name) {
        this.name = name;
        this.alias = alias;
    }

    public static PlatFormEnum valueOfAlias(String alias) {
        for (PlatFormEnum type : values()) {
            if (type.alias.equals(alias)) {
                return type;
            }
        }
        return null;
    }

}
