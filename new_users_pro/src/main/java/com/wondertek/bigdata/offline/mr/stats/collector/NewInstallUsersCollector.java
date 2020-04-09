package com.wondertek.bigdata.offline.mr.stats.collector;

import com.wondertek.bigdata.offline.dimension.key.KeyBaseDimension;
import com.wondertek.bigdata.offline.dimension.key.stats.StatsInstallUser;
import com.wondertek.bigdata.offline.dimension.value.BaseStatsValueWritable;
import com.wondertek.bigdata.offline.dimension.value.MapWritableValue;
import com.wondertek.bigdata.offline.service.converter.IDimensionConverter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewInstallUsersCollector implements IOutputCollector {
    @Override
    public void collect(Configuration conf, KeyBaseDimension key, BaseStatsValueWritable value, PreparedStatement pstmt, IDimensionConverter converter) throws IOException {
        StatsInstallUser newInstallUsers = (StatsInstallUser) key;

        MapWritableValue mapWritableValue = (MapWritableValue) value;
        Writable writable = mapWritableValue.getValue().get(new IntWritable(-1));
        IntWritable num = (IntWritable) writable;








        int i=0;
/**
 * `date_dimension_id`,
 `app_dimension_id`,
 `platform_dimension_id`,
 `ch_dimension_id`,
 `operator_dimension_id`,
 `ver_`,
 `area_dimension_id`,
 `new_users`)
 */

        try {
            pstmt.setInt(++i,converter.getDimensionIdByValue(newInstallUsers.getDateDimensionKey()));
            pstmt.setInt(++i,converter.getDimensionIdByValue(newInstallUsers.getAppDimensionKey()));
            pstmt.setInt(++i,converter.getDimensionIdByValue(newInstallUsers.getPlatformDimensionKey()));
            pstmt.setInt(++i,converter.getDimensionIdByValue(newInstallUsers.getChannelDimensionKey()));
            pstmt.setInt(++i,converter.getDimensionIdByValue(newInstallUsers.getIspDimensionKey()));
            pstmt.setInt(++i,converter.getDimensionIdByValue(newInstallUsers.getVersionDimensionKey()));
            pstmt.setInt(++i,converter.getDimensionIdByValue(newInstallUsers.getAreaDimensionKey()));
            pstmt.setInt(++i,num.get());
            pstmt.setInt(++i,num.get());
            pstmt.addBatch();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
