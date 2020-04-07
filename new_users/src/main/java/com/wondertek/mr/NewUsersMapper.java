package com.wondertek.mr;

import com.wondertek.common.EventLogConstants;
import com.wondertek.demension.NewUsersKpi;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;

import java.io.IOException;

public class NewUsersMapper extends TableMapper<NewUsersKpi,NullWritable>{



    NewUsersKpi newUsersKpi = new NewUsersKpi();

    private byte[] family = EventLogConstants.EVENT_LOGS_FAMILY_NAME_BYTES;
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {

        String appId = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_APP)));
        String platformId = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_PLATFORM)));
        String channel = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_CHANNEL)));
//        String version = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_VERSION)));
//        String country = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_COUNTRY)));
//        String province = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_PROVINCE)));
//        String city = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_CITY)));
//        String isp = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_ISP)));
//        String uuid = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_UUID)));
 //       String en = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME)));
     //   String pVer = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_P_VER)));
 //       String userId = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_USER_ID)));


//if("e_la".equals(en)&&pVer==null&&userId==null){
//    newUsersKpi.setAppId(appId);
//    newUsersKpi.setChannel(channel);
//    newUsersKpi.setPlatformId(platformId);
//    System.out.println(en+" "+pVer+" "+userId);
//   );
//}

    newUsersKpi.setAppId(Integer.valueOf(appId));
    newUsersKpi.setChannel(channel);
    newUsersKpi.setPlatformId(Integer.valueOf(platformId));


        context.write(newUsersKpi, NullWritable.get());

    }
    }

