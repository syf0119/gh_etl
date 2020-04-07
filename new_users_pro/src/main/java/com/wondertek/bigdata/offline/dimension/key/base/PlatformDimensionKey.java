package com.wondertek.bigdata.offline.dimension.key.base;

import com.wondertek.bigdata.offline.common.PlatFormEnum;
import com.wondertek.bigdata.offline.dimension.key.KeyBaseDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 终端类型
 *
 */
public class PlatformDimensionKey extends KeyBaseDimension {
    // 数据库主键id
    private int id;
    //终端id
    private String platformId;
    // 终端名称名称
    private String platformName;

    /**
     * 默认构造函数，必须给定
     */
    public PlatformDimensionKey() {
        super();
    }

    /**
     * 给定应用信息的构造函数
     * @param platformId 应用版本
     */
    public PlatformDimensionKey(String platformId, String platformName) {
        super();
        this.platformId = platformId;
        this.platformName = platformName;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(platformId);
        out.writeUTF(platformName);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.platformId = in.readUTF();
        this.platformName = in.readUTF();
    }

    @Override
    public int compareTo(KeyBaseDimension o) {
        if (o == this) {
            return 0;
        }
        PlatformDimensionKey bd = (PlatformDimensionKey) o;
        int result = Integer.compare(this.id, bd.getId());
        if (result == 0) {
            result = this.platformId.compareTo(bd.getPlatformId());
            if (result == 0) {
                result = this.platformName.compareTo(bd.getPlatformName());
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

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlatformDimensionKey that = (PlatformDimensionKey) o;

        if (id != that.id) {
            return false;
        }
        if (platformId != null ? !platformId.equals(that.platformId) : that.platformId != null) {
            return false;
        }
        return platformName != null ? platformName.equals(that.platformName) : that.platformName == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (platformId != null ? platformId.hashCode() : 0);
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        return result;
    }

//    public static List<PlatformDimensionKey> buildList(String platformId, String platformName) {
//        List<PlatformDimensionKey> result = new ArrayList<>();
//        result.add(new PlatformDimensionKey(platformId, platformName));
//        result.add(new PlatformDimensionKey(PlatFormEnum.ALL.alias, PlatFormEnum.ALL.name));
//        return result;
//    }
}
