package com.wondertek.bigdata.offline.common;

/**
 * @author zhangcheng
 */
public enum AreaEnum {
    COUNTRY("country"),
    PROVINCE("province"),
    CITY("city");

    public final String name;

    AreaEnum(String name) {
        this.name = name;
    }

    public static AreaEnum valueOfName(String name) {
        for (AreaEnum type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }

}
