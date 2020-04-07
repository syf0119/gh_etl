package com.wondertek.bigdata.offline.dimension.key.base;

import com.wondertek.bigdata.offline.common.AreaEnum;
import com.wondertek.bigdata.offline.common.GlobalConstants;
import com.wondertek.bigdata.offline.dimension.key.KeyBaseDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 地域维度类
 * @author zhangcheng
 */
public class AreaDimensionKey extends KeyBaseDimension {
    /**
     * 数据库主键
     */
    private int id;
    /**
     * 国家
     */
    private String country = "";
    /**
     * 省份
     */
    private String province = "";
    /**
     * 城市
     */
    private String city = "";
    /**
     * 地域维度类型：country、province、city
     */
    private String type = "";
    /**
     * 无参构造函数，必须给定
     */
    public AreaDimensionKey() {
        super();
    }

    /**
     * 给定全部参数的构造函数
     *
     * @param id 数据库主键id
     * @param country 国家
     * @param province 省份
     * @param city 城市
     * @param type 地域维度类型
     */
    public AreaDimensionKey(int id, String country, String province, String city, String type) {
        this(country, province, city, type);
        this.id = id;
    }

    /**
     * 有参构造方法， 主要用于构造具体的时间维度对象
     * @param country 国家
     * @param province 省份
     * @param city 城市
     * @param type 地域维度类型
     */
    public AreaDimensionKey(String country, String province, String city, String type) {
        this.country = country == null ? "unknown" : country;
        this.province = province == null ? "unknown" : province;
        this.city = city == null ? "unknown" : city;
        this.type = type;
    }

//    public static List<AreaDimensionKey> buildList(String country, String province, String city, String type) {
//        List<AreaDimensionKey> result = new ArrayList<>();
//        AreaDimensionKey areaDimensionKey1 = new AreaDimensionKey(country, province, city, type);
//        AreaDimensionKey areaDimensionKey2 = new AreaDimensionKey(GlobalConstants.VALUE_OF_ALL, GlobalConstants.VALUE_OF_ALL, GlobalConstants.VALUE_OF_ALL, AreaEnum.COUNTRY.name);
//        result.add(areaDimensionKey1);
//        result.add(areaDimensionKey2);
//        return result;
//    }
//
//    public static List<AreaDimensionKey> buildList(String country, String province, String city) {
//        List<AreaDimensionKey> result = new ArrayList<>();
//        AreaDimensionKey areaDimensionKey1 = new AreaDimensionKey(country, province, province, AreaEnum.COUNTRY.name);
//        AreaDimensionKey areaDimensionKey2 = new AreaDimensionKey(country, province, province, AreaEnum.PROVINCE.name);
//        AreaDimensionKey areaDimensionKey3 = new AreaDimensionKey(country, province, city, AreaEnum.CITY.name);
//        result.add(areaDimensionKey1);
//        result.add(areaDimensionKey2);
//        result.add(areaDimensionKey3);
//        return result;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        AreaDimensionKey that = (AreaDimensionKey) o;

        if (id != that.id) {
            return false;
        }
        if (!country.equals(that.country)) {
            return false;
        }
        if (!province.equals(that.province)) {
            return false;
        }
        if (!city.equals(that.city)) {
            return false;
        }
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        result = 31 * result + country.hashCode();
        result = 31 * result + province.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    //序列化，将字节转化为二进制输出
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeUTF(this.country);
        out.writeUTF(this.province);
        out.writeUTF(this.city);
        out.writeUTF(this.type);
    }

    //反序列化，将输入二进制反序列化成字符流
    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.country = in.readUTF();
        this.province = in.readUTF();
        this.city = in.readUTF();
        this.type = in.readUTF();
    }

    @Override
    public int compareTo(KeyBaseDimension o) {
        if (o == this) {
            return 0;
        }
        AreaDimensionKey dd = (AreaDimensionKey) o;
        int tmp = Integer.compare(this.id, dd.getId());
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.country.compareTo(dd.getCountry());
        if (tmp != 0) {
            return tmp;
        }

        tmp = this.province.compareTo(dd.getProvince());
        if (tmp != 0) {
            return tmp;
        }

        tmp = this.city.compareTo(dd.getCity());
        if (tmp != 0) {
            return tmp;
        }
        return this.type.compareTo(dd.getType());
    }

}
