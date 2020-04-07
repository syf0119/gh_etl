package com.wondertek.bigdata.offline.dimension.key.base;

import com.wondertek.bigdata.offline.dimension.key.KeyBaseDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 应用维度（应用名称和应用版本号）
 *
 */
public class AppDimensionKey extends KeyBaseDimension {
    /**
     * 数据库主键id
     */
    private int id;
    /**
     * 应用id
     */
    private String appId;
    /**
     * 应用名称
     */
    private String appName;

    /**
     * 默认构造函数，必须给定
     */
    public AppDimensionKey() {
        super();
    }

    /**
     * 给定应用信息的构造函数
     * @param appName 应用版本
     */
    public AppDimensionKey(String appId, String appName) {
        super();
        this.appId = appId;
        this.appName = appName;
    }


    //序列化 将字节流转化为二进制输出
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(appId);
        out.writeUTF(appName);
    }

    //反序列化，将输入二进制转化为字符流
    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.appId = in.readUTF();
        this.appName = in.readUTF();
    }


    //重写compareTo方法,比较此对象和指定对象的顺序
    @Override
    public int compareTo(KeyBaseDimension o) {
        if (o == this) {
            return 0;
        }
        AppDimensionKey bd = (AppDimensionKey) o;
        int result = Integer.compare(this.id, bd.getId());
        if (result == 0) {
            result = this.appId.compareTo(bd.getAppId());
            if (result == 0) {
                result = this.appName.compareTo(bd.getAppName());
                return result;
            }
            return result;
        }
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppDimensionKey that = (AppDimensionKey) o;
        if (id != that.id) {
            return false;
        }
        if (appId != null ? !appId.equals(that.appId) : that.appId != null) {
            return false;
        }
        return appName != null ? appName.equals(that.appName) : that.appName == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (appId != null ? appId.hashCode() : 0);
        result = 31 * result + (appName != null ? appName.hashCode() : 0);
        return result;
    }
}
