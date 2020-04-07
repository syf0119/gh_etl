package com.wondertek.bigdata.offline.mr.stats.newusers;

import com.wondertek.bigdata.offline.common.*;
import com.wondertek.bigdata.offline.dimension.key.base.*;
import com.wondertek.bigdata.offline.dimension.key.stats.StatsInstallUser;
import com.wondertek.bigdata.offline.util.TimeUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class StatsInstallUserMapper extends TableMapper<StatsInstallUser, Text> {
    private DateDimensionKey dateKey;
    private KpiDimensionKey InstallUserKpi = new KpiDimensionKey(KpiEnum.NEW_INSTALL_USERS.name);

    private KpiDimensionKey updateUsersKpi = new KpiDimensionKey(KpiEnum.UPDATE_USERS.name);
    private KpiDimensionKey newMembersKpi = new KpiDimensionKey(KpiEnum.NEW_MEMBERS.name);


    //total_install_users
    //update_users
    //new_members
    //total_membs
    //active_members
    //active_users
    //created


    private byte[] family = EventLogConstants.EVENT_LOGS_FAMILY_NAME_BYTES;

    @Override
    protected void setup(Context context) {
        long date = TimeUtil.parseString2Long(context.getConfiguration().get(GlobalConstants.RUNNING_DATE_PARAMES), TimeUtil.DATE_FORMAT);
        dateKey = DateDimensionKey.buildDate(date, DateEnum.DAY);


    }

    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        String uuid = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_UUID)));

        StatsInstallUser userKpi = new StatsInstallUser();


        result2UserKpi(value, userKpi);
        Text uuidText = new Text(uuid);


        context.write(userKpi, uuidText);


    }

    private void result2UserKpi(Result value, StatsInstallUser userKpi) {

        String appId = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_APP)));
        String platformId = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_PLATFORM)));
        String channel = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_CHANNEL)));
        String version = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_VERSION)));
        String country = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_COUNTRY)));
        String province = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_PROVINCE)));
        String city = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_CITY)));
        String isp = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_ISP)));


        userKpi.setAppDimensionKey(new AppDimensionKey(appId, AppEnum.valueOfAlias(appId).name));
        userKpi.setPlatformDimensionKey(new PlatformDimensionKey(platformId, PlatFormEnum.valueOf(platformId).name));


        userKpi.setChannelDimensionKey(new ChannelDimensionKey(ChannelEnum.valueOfName(channel).name));
        userKpi.setDateDimensionKey(dateKey);
        userKpi.setAreaDimensionKey(new AreaDimensionKey(country, province, city, AreaEnum.CITY.name));
        userKpi.setIspDimensionKey(new IspDimensionKey(isp));
        userKpi.setVersionDimensionKey(new VersionDimensionKey(version));
        userKpi.setKpiDimensionKey(InstallUserKpi);


    }
}
