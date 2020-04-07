package com.wondertek.bigdata.offline.dimension.key.stats;

import com.wondertek.bigdata.offline.dimension.key.KeyBaseDimension;
import com.wondertek.bigdata.offline.dimension.key.KeyStatsDimension;
import com.wondertek.bigdata.offline.dimension.key.base.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class StatsInstallUser extends KeyStatsDimension {

    private DateDimensionKey dateDimensionKey = new DateDimensionKey();
    private AppDimensionKey appDimensionKey = new AppDimensionKey();
    private PlatformDimensionKey platformDimensionKey = new PlatformDimensionKey();


    private ChannelDimensionKey channelDimensionKey = new ChannelDimensionKey();

    private VersionDimensionKey versionDimensionKey = new VersionDimensionKey();

    private AreaDimensionKey areaDimensionKey = new AreaDimensionKey();

    private IspDimensionKey ispDimensionKey = new IspDimensionKey();

    private KpiDimensionKey kpiDimensionKey = new KpiDimensionKey();

    @Override
    public int compareTo(KeyBaseDimension o) {
       if(this==o) return 0;
        StatsInstallUser other = (StatsInstallUser) o;
        int tmp = this.dateDimensionKey.compareTo(other.dateDimensionKey);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.appDimensionKey.compareTo(other.appDimensionKey);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.platformDimensionKey.compareTo(other.platformDimensionKey);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.kpiDimensionKey.compareTo(other.kpiDimensionKey);

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

        this.dateDimensionKey.write(out);
        this.appDimensionKey.write(out);
        this.platformDimensionKey.write(out);
        this.kpiDimensionKey.write(out);

        this.channelDimensionKey.write(out);
        this.versionDimensionKey.write(out);
        this.areaDimensionKey.write(out);
        this.ispDimensionKey.write(out);

    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.dateDimensionKey.readFields(in);
        this.appDimensionKey.readFields(in);
        this.platformDimensionKey.readFields(in);
        this.kpiDimensionKey.readFields(in);

        this.channelDimensionKey.readFields(in);
        this.versionDimensionKey.readFields(in);
        this.areaDimensionKey.readFields(in);
        this.ispDimensionKey.readFields(in);
    }

    public DateDimensionKey getDateDimensionKey() {
        return dateDimensionKey;
    }

    public void setDateDimensionKey(DateDimensionKey dateDimensionKey) {
        this.dateDimensionKey = dateDimensionKey;
    }

    public AppDimensionKey getAppDimensionKey() {
        return appDimensionKey;
    }

    public void setAppDimensionKey(AppDimensionKey appDimensionKey) {
        this.appDimensionKey = appDimensionKey;
    }

    public PlatformDimensionKey getPlatformDimensionKey() {
        return platformDimensionKey;
    }

    public void setPlatformDimensionKey(PlatformDimensionKey platformDimensionKey) {
        this.platformDimensionKey = platformDimensionKey;
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

    public KpiDimensionKey getKpiDimensionKey() {
        return kpiDimensionKey;
    }

    public void setKpiDimensionKey(KpiDimensionKey kpiDimensionKey) {
        this.kpiDimensionKey = kpiDimensionKey;
    }

    public StatsInstallUser(DateDimensionKey dateDimensionKey, AppDimensionKey appDimensionKey, PlatformDimensionKey platformDimensionKey, ChannelDimensionKey channelDimensionKey, VersionDimensionKey versionDimensionKey, AreaDimensionKey areaDimensionKey, IspDimensionKey ispDimensionKey, KpiDimensionKey kpiDimensionKey) {
        this.dateDimensionKey = dateDimensionKey;
        this.appDimensionKey = appDimensionKey;
        this.platformDimensionKey = platformDimensionKey;
        this.channelDimensionKey = channelDimensionKey;
        this.versionDimensionKey = versionDimensionKey;
        this.areaDimensionKey = areaDimensionKey;
        this.ispDimensionKey = ispDimensionKey;
        this.kpiDimensionKey = kpiDimensionKey;
    }

    public StatsInstallUser() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatsInstallUser)) return false;
        StatsInstallUser that = (StatsInstallUser) o;
        return Objects.equals(getDateDimensionKey(), that.getDateDimensionKey()) &&
                Objects.equals(getAppDimensionKey(), that.getAppDimensionKey()) &&
                Objects.equals(getPlatformDimensionKey(), that.getPlatformDimensionKey()) &&
                Objects.equals(getChannelDimensionKey(), that.getChannelDimensionKey()) &&
                Objects.equals(getVersionDimensionKey(), that.getVersionDimensionKey()) &&
                Objects.equals(getAreaDimensionKey(), that.getAreaDimensionKey()) &&
                Objects.equals(getIspDimensionKey(), that.getIspDimensionKey()) &&
                Objects.equals(getKpiDimensionKey(), that.getKpiDimensionKey());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDateDimensionKey(), getAppDimensionKey(), getPlatformDimensionKey(), getChannelDimensionKey(), getVersionDimensionKey(), getAreaDimensionKey(), getIspDimensionKey(), getKpiDimensionKey());
    }
}
