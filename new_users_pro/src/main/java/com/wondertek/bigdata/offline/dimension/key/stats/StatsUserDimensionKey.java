package com.wondertek.bigdata.offline.dimension.key.stats;

import com.wondertek.bigdata.offline.common.GlobalConstants;
import com.wondertek.bigdata.offline.dimension.key.KeyBaseDimension;
import com.wondertek.bigdata.offline.dimension.key.KeyStatsDimension;
import com.wondertek.bigdata.offline.dimension.key.base.AreaDimensionKey;
import com.wondertek.bigdata.offline.dimension.key.base.ChannelDimensionKey;
import com.wondertek.bigdata.offline.dimension.key.base.IspDimensionKey;
import com.wondertek.bigdata.offline.dimension.key.base.VersionDimensionKey;
import org.apache.commons.lang.StringUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Administrator on 2017/9/15.
 * 进行用户分析(用户基本分析)定义的组合维度
 */
public class StatsUserDimensionKey extends KeyStatsDimension {

    private StatsCommonDimensionKey commonDimensionKey = new StatsCommonDimensionKey();

    private ChannelDimensionKey channelDimensionKey = new ChannelDimensionKey();

    private VersionDimensionKey versionDimensionKey = new VersionDimensionKey();

    private AreaDimensionKey areaDimensionKey = new AreaDimensionKey();

    private IspDimensionKey ispDimensionKey = new IspDimensionKey();

    public StatsUserDimensionKey() {
        super();
    }

    public StatsUserDimensionKey(StatsCommonDimensionKey commonDimensionKey, ChannelDimensionKey channelDimensionKey, VersionDimensionKey versionDimensionKey, AreaDimensionKey areaDimensionKey, IspDimensionKey ispDimensionKey) {
        super();
        this.commonDimensionKey = commonDimensionKey;
        this.channelDimensionKey = channelDimensionKey;
        this.versionDimensionKey = versionDimensionKey;
        this.areaDimensionKey = areaDimensionKey;
        this.ispDimensionKey = ispDimensionKey;
}

    @Override
    public int compareTo(KeyBaseDimension o) {
        if (this == o) {
            return 0;
        }

        StatsUserDimensionKey other = (StatsUserDimensionKey) o;
        int tmp = this.commonDimensionKey.compareTo(other.commonDimensionKey);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.channelDimensionKey.compareTo(other.channelDimensionKey);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.versionDimensionKey.compareTo(other.versionDimensionKey);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.areaDimensionKey.compareTo(other.areaDimensionKey);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.ispDimensionKey.compareTo(other.ispDimensionKey);
        return tmp;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        this.commonDimensionKey.write(out);
        this.channelDimensionKey.write(out);
        this.versionDimensionKey.write(out);
        this.areaDimensionKey.write(out);
        this.ispDimensionKey.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.commonDimensionKey.readFields(in);
        this.channelDimensionKey.readFields(in);
        this.versionDimensionKey.readFields(in);
        this.areaDimensionKey.readFields(in);
        this.ispDimensionKey.readFields(in);
    }

    public StatsCommonDimensionKey getCommonDimensionKey() {
        return commonDimensionKey;
    }

    public void setCommonDimensionKey(StatsCommonDimensionKey commonDimensionKey) {
        this.commonDimensionKey = commonDimensionKey;
    }

    public ChannelDimensionKey getChannelDimensionKey() {
        return channelDimensionKey;
    }

    public void setChannelDimensionKey(ChannelDimensionKey channelDimensionKey) {
        this.channelDimensionKey = channelDimensionKey;
    }

    public VersionDimensionKey getVersionDimensionKey() {
        return versionDimensionKey;
    }

    public void setVersionDimensionKey(VersionDimensionKey versionDimensionKey) {
        this.versionDimensionKey = versionDimensionKey;
    }

    public AreaDimensionKey getAreaDimensionKey() {
        return areaDimensionKey;
    }

    public void setAreaDimensionKey(AreaDimensionKey areaDimensionKey) {
        this.areaDimensionKey = areaDimensionKey;
    }

    public IspDimensionKey getIspDimensionKey() {
        return ispDimensionKey;
    }

    public void setIspDimensionKey(IspDimensionKey ispDimensionKey) {
        this.ispDimensionKey = ispDimensionKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StatsUserDimensionKey that = (StatsUserDimensionKey) o;

        if (commonDimensionKey != null ? !commonDimensionKey.equals(that.commonDimensionKey) : that.commonDimensionKey != null) {
            return false;
        }
        if (channelDimensionKey != null ? !channelDimensionKey.equals(that.channelDimensionKey) : that.channelDimensionKey != null) {
            return false;
        }
        if (versionDimensionKey != null ? !versionDimensionKey.equals(that.versionDimensionKey) : that.versionDimensionKey != null) {
            return false;
        }
        if (areaDimensionKey != null ? !areaDimensionKey.equals(that.areaDimensionKey) : that.areaDimensionKey != null) {
            return false;
        }
        return ispDimensionKey != null ? ispDimensionKey.equals(that.ispDimensionKey) : that.ispDimensionKey == null;
    }

    @Override
    public int hashCode() {
        int result = commonDimensionKey != null ? commonDimensionKey.hashCode() : 0;
        result = 31 * result + (channelDimensionKey != null ? channelDimensionKey.hashCode() : 0);
        result = 31 * result + (versionDimensionKey != null ? versionDimensionKey.hashCode() : 0);
        result = 31 * result + (areaDimensionKey != null ? areaDimensionKey.hashCode() : 0);
        result = 31 * result + (ispDimensionKey != null ? ispDimensionKey.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String commonKeyStr = this.commonDimensionKey.toString();
        String channel = this.channelDimensionKey.getName();
        String version = this.versionDimensionKey.getName();
        String province = this.areaDimensionKey.getProvince();
        String isp = this.ispDimensionKey.getName();
        if (StringUtils.isNotBlank(commonKeyStr)) {
            if (this.commonDimensionKey.getKpiDimensionKey().getKpiName().startsWith("channel") && StringUtils.isNotBlank(channel)) {
                return commonKeyStr + GlobalConstants.KEY_SEPARATOR + channel;
            }

            if (this.commonDimensionKey.getKpiDimensionKey().getKpiName().startsWith("version") && StringUtils.isNotBlank(version)) {
                return commonKeyStr + GlobalConstants.KEY_SEPARATOR + version;
            }

            if (this.commonDimensionKey.getKpiDimensionKey().getKpiName().startsWith("area") && StringUtils.isNotBlank(province) && StringUtils.isNotBlank(isp)) {
                return commonKeyStr + GlobalConstants.KEY_SEPARATOR + province + GlobalConstants.KEY_SEPARATOR + isp;
            }
        }
        return "";
    }
}
