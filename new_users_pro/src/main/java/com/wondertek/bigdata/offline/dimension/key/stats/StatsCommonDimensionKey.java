package com.wondertek.bigdata.offline.dimension.key.stats;

import com.wondertek.bigdata.offline.common.GlobalConstants;
import com.wondertek.bigdata.offline.dimension.key.KeyBaseDimension;
import com.wondertek.bigdata.offline.dimension.key.KeyStatsDimension;
import com.wondertek.bigdata.offline.dimension.key.base.AppDimensionKey;
import com.wondertek.bigdata.offline.dimension.key.base.DateDimensionKey;
import com.wondertek.bigdata.offline.dimension.key.base.KpiDimensionKey;
import com.wondertek.bigdata.offline.dimension.key.base.PlatformDimensionKey;
import org.apache.commons.lang.StringUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Administrator on 2017/9/15.
 */
public class StatsCommonDimensionKey extends KeyStatsDimension {

    private DateDimensionKey dateDimensionKey = new DateDimensionKey();
    private AppDimensionKey appDimensionKey = new AppDimensionKey();
    private PlatformDimensionKey platformDimensionKey = new PlatformDimensionKey();
    private KpiDimensionKey kpiDimensionKey = new KpiDimensionKey();

    public StatsCommonDimensionKey(){super();}

    public StatsCommonDimensionKey(DateDimensionKey date, AppDimensionKey app, PlatformDimensionKey platform, KpiDimensionKey kpi) {
        super();
        this.dateDimensionKey = date;
        this.appDimensionKey = app;
        this.platformDimensionKey = platform;
        this.kpiDimensionKey = kpi;
    }

    @Override
    public int compareTo(KeyBaseDimension o) {
        if (this == o) {
            return 0;
        }
        StatsCommonDimensionKey other = (StatsCommonDimensionKey) o;
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
        return tmp;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        this.dateDimensionKey.write(out);
        this.appDimensionKey.write(out);
        this.platformDimensionKey.write(out);
        this.kpiDimensionKey.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.dateDimensionKey.readFields(in);
        this.appDimensionKey.readFields(in);
        this.platformDimensionKey.readFields(in);
        this.kpiDimensionKey.readFields(in);
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

    public KpiDimensionKey getKpiDimensionKey() {
        return kpiDimensionKey;
    }

    public void setKpiDimensionKey(KpiDimensionKey kpiDimensionKey) {
        this.kpiDimensionKey = kpiDimensionKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StatsCommonDimensionKey that = (StatsCommonDimensionKey) o;

        if (dateDimensionKey != null ? !dateDimensionKey.equals(that.dateDimensionKey) : that.dateDimensionKey != null) {
            return false;
        }
        if (appDimensionKey != null ? !appDimensionKey.equals(that.appDimensionKey) : that.appDimensionKey != null) {
            return false;
        }
        if (platformDimensionKey != null ? !platformDimensionKey.equals(that.platformDimensionKey) : that.platformDimensionKey != null) {
            return false;
        }
        return kpiDimensionKey != null ? kpiDimensionKey.equals(that.kpiDimensionKey) : that.kpiDimensionKey == null;
    }

    @Override
    public int hashCode() {
        int result = dateDimensionKey != null ? dateDimensionKey.hashCode() : 0;
        result = 31 * result + (appDimensionKey != null ? appDimensionKey.hashCode() : 0);
        result = 31 * result + (platformDimensionKey != null ? platformDimensionKey.hashCode() : 0);
        result = 31 * result + (kpiDimensionKey != null ? kpiDimensionKey.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String appId = this.appDimensionKey.getAppId();
        String platformId = this.platformDimensionKey.getPlatformId();
        if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(platformId)) {
            return kpiDimensionKey.getKpiName() + GlobalConstants.KEY_SEPARATOR + appId + GlobalConstants.KEY_SEPARATOR + platformId;
        }
        return "";
    }
}
