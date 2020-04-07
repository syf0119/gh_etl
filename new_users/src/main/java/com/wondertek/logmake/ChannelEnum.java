package com.wondertek.logmake;


import java.util.Random;

/**
 * @author zhangcheng
 */
public enum ChannelEnum {
    A360("360"),
    ALI("ali"),
    ANZHI("anzhi"),
    BAIDU("baidu"),
    CCTV4G("cctv4g"),
    HUAWEI("huawei"),
    JSYD("jsyd"),
    LENOVO("lenovo"),
    OPPO("oppo"),
    QQ("qq"),
    SMARTISAN("smartisan"),
    VIVO("vivo"),
    XIAOMI("xiaomi"),
    YICHENGTIANXIA("yichengtianxia");


    public final String name;


    ChannelEnum(String alias) {

        this.name = alias;

    }

    /**
     * 根据属性name的值获取对应的type对象
     * 
     * @param alias
     * @return
     */
    public static ChannelEnum valueOfName(String alias) {
        for (ChannelEnum type : values()) {
            if (type.name.equals(alias)) {
                return type;
            }
        }
        return null;
    }
    public static String getRandomChannel() {

       return  ChannelEnum.values()[new Random().nextInt(14)].name;



    }

}
