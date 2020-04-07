package com.wondertek.bigdata.offline.mr.etl;


import com.wondertek.bigdata.offline.common.EventLogConstants;
import com.wondertek.bigdata.offline.common.GlobalConstants;
import com.wondertek.bigdata.offline.util.LoggerUtil;
import com.wondertek.bigdata.offline.util.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.zip.CRC32;


public class AnalyserLogDataMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put> {
    private static final Logger logger = Logger.getLogger(AnalyserLogDataMapper.class);
    private String eventLogsTable;
    private long currentDayMillis = 0;
    private CRC32 crc32=new CRC32();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        String date = context.getConfiguration().get(GlobalConstants.RUNNING_DATE_PARAMES);
        String tableNameSuffix = TimeUtil.parseLong2String(TimeUtil.parseString2Long(date), TimeUtil.HBASE_TABLE_NAME_SUFFIX_FORMAT);
        this.eventLogsTable=EventLogConstants.HBASE_NAME_EVENT_LOGS+GlobalConstants.UNDERLINE+tableNameSuffix;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(TimeUtil.parseString2Long(date));
        this.currentDayMillis = calendar.getTimeInMillis();

    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String record = value.toString();
        //System.out.println(record);
        //把日志解析成kv类型
        Map<String, String> clientInfo = LoggerUtil.handleLog(record);



        if(clientInfo.isEmpty()) {
            logger.debug("无效数据:"+value);

        }else{

            // TODO: 2020.3.31 校验数据，把校验不通过的数据落地到另外一个地方


            String eventAliasName = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME);
            EventLogConstants.EventEnum event = EventLogConstants.EventEnum.valueOfAlias(eventAliasName);

            if(event!=null){
                switch (event){
                    case LAUNCH:
                        this.handleUserAccessData(clientInfo, context);
                        break;
                }
            }



        }




    }

    /**
     *
     * @param clientInfo
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */

    private void handleUserAccessData(Map<String, String> clientInfo,  Context context) throws IOException, InterruptedException {

        ImmutableBytesWritable table=new ImmutableBytesWritable(Bytes.toBytes(this.eventLogsTable));
        //根据ip解析出国省市
        LoggerUtil.parseIp(clientInfo);

        //logger.debug("clientInfo："+clientInfo);

        outPut(table,clientInfo,context);



    }

    /**
     *
     * @param table
     * @param clientInfo
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    private void outPut(ImmutableBytesWritable table, Map<String, String> clientInfo, Context context) throws IOException, InterruptedException {

        String event=clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME);
        String serverTime = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME);
        String idField=null;

        if(EventLogConstants.EventEnum.LAUNCH.alias.equals(event)){
            idField=clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_UUID);
        }

        byte[] rowKey = this.generateRowKey(idField, Long.valueOf(serverTime), clientInfo);

        Put put = new Put(rowKey);
        for (Map.Entry<String, String> stringEntry : clientInfo.entrySet()) {
            if(StringUtils.isNotBlank(stringEntry.getKey())&&StringUtils.isNotBlank(stringEntry.getValue())){
                put.addColumn(EventLogConstants.EVENT_LOGS_FAMILY_NAME_BYTES,Bytes.toBytes(stringEntry.getKey()),Bytes.toBytes(stringEntry.getValue()));
            }
        }
        context.write(table,put);


    }

    /**
     *
     * @param id
     * @param serverTime
     * @param clientInfo
     * @return
     */
    private byte[] generateRowKey(String id, long serverTime, Map<String, String> clientInfo) {
        // 当天的过去的毫秒数
        byte[] bf1 = Bytes.toBytes((int) (serverTime - this.currentDayMillis));
        // 重置
        this.crc32.reset();
        if (StringUtils.isNotBlank(id)) {
            this.crc32.update(id.getBytes());
        }
        this.crc32.update(Bytes.toBytes(clientInfo.hashCode()));

        byte[] bf2 = Bytes.toBytes(this.crc32.getValue());

        byte[] buffer = new byte[bf1.length + bf2.length];
        // bf1和bf2，合并到buffer
        System.arraycopy(bf1, 0, buffer, 0, bf1.length);
        System.arraycopy(bf2, 0, buffer, bf1.length, bf2.length);
        return buffer;
    }

    /**
     *
     * @param event
     * @param platform
     * @param clientInfo
     * @return
     */
    private boolean checkFieldIntegrity(EventLogConstants.EventEnum event,
                                        String platform, Map<String, String> clientInfo) {
        // 基本参数校验：event,platform,serverTime
        boolean result = event != null && StringUtils.isNotBlank(platform)
                && StringUtils.isNotBlank(clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME));
        if (result) {
            // 针对具体的event进行字段信息的判断
            String uuid = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_UUID);
            String sessionId = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_SESSION_ID);

            switch (platform) {
                // H5页面请求参数校验
                case EventLogConstants.PlatformNameConstants.PC_WEBSITE_SDK:
                    result = result && StringUtils.isNotBlank(uuid) && StringUtils.isNotBlank(sessionId);
                    switch (event) {
                        // 页面访问的必须参数校验
                        case PAGEVIEW:
                            result = result && StringUtils.isNotBlank(clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_CURRENT_URL));
                            break;
                        case LAUNCH:
                            // 没有特殊要求，JS SDK的参数校验就可以了
                            break;
                        default:
                            // 不应该出现其他事件类型
                            result = false;
                            break;
                    }
                    break;
                // APP请求参数校验
                case EventLogConstants.PlatformNameConstants.JAVA_SERVER_SDK:
                    switch (event) {
                        case PAGEVIEW:
                            result = result;
                            break;
                        case LAUNCH:
                            // 没有特殊要求，JS SDK的参数校验就可以了
                            break;
                        default:
                            // 不应该出现此event
                            result = false;
                            break;
                    }
                    break;
                default:
                    // 不应该出现的平台
                    result = false;
                    break;
            }
        }
        return result;
    }

}
