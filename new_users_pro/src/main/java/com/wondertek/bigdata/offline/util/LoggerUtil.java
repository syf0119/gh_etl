package com.wondertek.bigdata.offline.util;

import com.wondertek.bigdata.offline.common.EventLogConstants;
import com.wondertek.bigdata.offline.common.GlobalConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志抽取工具类
 */
public class LoggerUtil {
    private static final Logger logger = Logger.getLogger(LoggerUtil.class);
    private static final IPSeekerExt ipSeeker = IPSeekerExt.getInstance();
//    private static final Configuration conf = new Configuration();
//    private static Connection conn;

    /**
     * 初始化jdbc连接
     */
//    static {
//        conf.addResource("transformer-env.xml");
//        try {
//            conn = JdbcManager.getConnection(conf, GlobalConstants.WAREHOUSE_OF_REPORT);
//            // 关闭自动提交机制
//            conn.setAutoCommit(false);
//        } catch (Exception e) {
//            throw new RuntimeException("获取数据库连接失败", e);
//        }
//    }

    /**
     * 解析日志，返回一个map集合，如果处理失败，那么empty的map集合<br/>
     * 失败：分割失败，url解析出现问题等等
     *
     * @param logText 日志
     * @return 行日志，转成map对象，如果业务日志没有（？后面没有），返回map为null
     */
    public static Map<String, String> handleLog(String logText) throws UnsupportedEncodingException {
        Map<String, String> clientInfo = new HashMap<>();
        if (StringUtils.isNotBlank(logText)) {
            //先转码在解析字段
            //转移字段字面的%
            String url = logText.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            logText = URLDecoder.decode(url, "UTF-8");
            String[] splits = logText.trim().split(EventLogConstants.LOG_SEPA_A);
            // 数据格式正确，可以分割
            if (splits.length == 3) {
                // 第一列：IP地址
                clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_IP, splits[0].trim());
                // 第二列：服务器时间
                long nginxTime = TimeUtil.parseNginxServerTime2Long(splits[1].trim());
                if (nginxTime != -1) {
                    clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME, String.valueOf(nginxTime));
                }
                // 第三列：处理业务请求参数
                String requestStr = splits[2];
                int index = requestStr.indexOf("?");
                if (index > -1) {
                    // 有请求参数的情况下，获取？后面的参数
                    String requestBody = requestStr.substring(index + 1);
                    // 处理请求参数
                    handleRequestBody(clientInfo, requestBody);
                } else {
                    // 没有请求参数
                    clientInfo.clear();
                }
            }
        }
        return clientInfo;
    }

    /**
     * 解析ip
     *
     * @param clientInfo
     */
    public static void parseIp(Map<String, String> clientInfo) {
        String IP = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_IP);
        if (!IP.equals("-")) {
            IPSeekerExt.RegionInfo info = ipSeeker.analysisIp(IP);
            clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_CITY, info.getCity());
            clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_PROVINCE, info.getProvince());
            clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_COUNTRY, info.getCountry());
            clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_ISP, info.getIsp());
        }else{
            clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_CITY,GlobalConstants.DEFAULT_VALUE);
            clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_PROVINCE, GlobalConstants.DEFAULT_VALUE);
            clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_COUNTRY, GlobalConstants.DEFAULT_VALUE);
            clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_ISP, GlobalConstants.DEFAULT_VALUE);
        }
    }

    /**
     * 解析属性表
     *
     * @param clientInfo
     * @param key
     */
    public static void parseAttrInfo(Map<String, String> clientInfo, String key) {
        String attrString = clientInfo.get(key);
        if (StringUtils.isNotBlank(attrString)) {
            String[] attrPair = attrString.split(GlobalConstants.SPLIT_SYMBOL);
            for (int i = 0; i < attrPair.length; i++) {
                String attr = attrPair[i];
                if (attr.startsWith(GlobalConstants.LESS) && attr.endsWith(GlobalConstants.GREATER)) {
                    attr = attr.substring(1, attr.length() - 1);
                    String[] kvs = attr.split(GlobalConstants.EQUAL);
                    if (kvs.length == 2 && StringUtils.isNotBlank(kvs[0]) && StringUtils.isNotBlank(kvs[1])) {
                        clientInfo.put(kvs[0], kvs[1]);
                    }
                }
            }
        }
    }

    /**
     * 处理业务请求参数
     *
     * @param clientInfo
     * @param requestBody
     */
    private static void handleRequestBody(Map<String, String> clientInfo, String requestBody) {
        String[] requestParames = requestBody.split(EventLogConstants.LOG_SEPA_AND);
        for (String param : requestParames) {
            if (StringUtils.isNotBlank(param)) {
                int index = param.indexOf(EventLogConstants.LOG_PARAM_EQUAL);
                if (index < 0) {
                    logger.debug("没法进行解析:" + param);
                    continue;
                }
                String key, value = null;
                try {
                    key = param.substring(0, index);
//                    value = URLDecoder.decode(param.substring(index + 1), EventLogConstants.LOG_PARAM_CHARSET);
                      value=param.substring(index + 1);
                } catch (Exception e) {
                    logger.debug("value值decode时候出现异常，value: " + value, e);
                    continue;
                }
                // key 和 value都不为空时，保留到map
                if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                    clientInfo.put(key, value);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {





        Map<String, String> map = handleLog("-^A1548201186.861^A/dw.png?en=e_ca&cont_id=1000057464&ca=1&cpid=149&api_v=1.0&pl=1&uuid=20190108195025700-Z0053084069113286999&ch_id=&n_id=&disp_time=");
parseIp(map);





        System.out.println(map);
    }
}
