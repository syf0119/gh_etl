package com.wondertek.bigdata.offline.common;

/**
 * 全局常量类
 * @author zhangcheng
 */
public class GlobalConstants {

    public static final String DATE_FORMAT = "yyyyMMdd";
    /**
     * 一天的毫秒数
     */
    public static final int DAY_OF_MILLISECONDS = 86400000;
    /**
     * 存储在HDFS路径上的日志文件前缀
     */
    public static final String HDFS_LOGS_PATH_PREFIX = "/flume/nginxlogs/";
    /**
     * 定义的运行时间变量名
     */
    public static final String RUNNING_DATE_PARAMES = "RUNNING_DATE";
    /**
     * 定义的运行时etl操作是否覆盖hbase表，如果为参数值为true，表示覆盖，那么表示重新创建，否则不进行重新创建
     */
    public static final String RUNNING_OVERRIDE_ETL_HBASE_TABLE = "";
    /**
     * 默认值
     */
    public static final String DEFAULT_VALUE = "unknown";
    /**
     * 维度信息表中指定全部列值
     */
    public static final String VALUE_OF_ALL = "all";

    /**
     * 定义的output collector的前缀
     */
    public static final String OUTPUT_COLLECTOR_KEY_PREFIX = "collector_";

    /**
     * 指定连接表配置为report
     */
    public static final String WAREHOUSE_OF_REPORT = "report";

    /**
     * 批量执行的key
     */
    public static final String JDBC_BATCH_NUMBER = "mysql.batch.number";

    /**
     * phoenix 批量执行大小
     */
    public static final String PHOENIX_BATCH_NUMBER = "phoenix.batch.number";

    /**
     * 默认批量大小
     */
    public static final int DEFAULT_JDBC_BATCH_NUMBER = 500;

    /**
     * driver 名称
     */
    public static final String JDBC_DRIVER = "mysql.%s.driver";

    /**
     * JDBC URL
     */
    public static final String JDBC_URL = "mysql.%s.url";

    /**
     * username名称
     */
    public static final String JDBC_USERNAME = "mysql.%s.username";

    /**
     * password名称
     */
    public static final String JDBC_PASSWORD = "mysql.%s.password";

    /**
     * phoenix jdbc url
     */
    public static final String PHOENIX_JDBC_URL = "phoenix.jdbc.url";

    /**
     * phoenix jdbc driver class name
     */
    public static final String PHOENIX_JDBC_DRIVER = "phoenix.jdbc.driver";

    /**
     * 开始日期
     */
    public static final String BEGIN_DATE = "bd";

    /**
     * 结束日期
     */
    public static final String END_DATE = "ed";

    /**
     * 横杠连接符
     */
    public static final String KEY_SEPARATOR = "-";

    /**
     * #连接符
     */
    public static final String SPECIAL_CONNECTOR = "#";

    /**
     * 等号
     */
    public static final String EQUAL = "=";

    /**
     * 小于
     */
    public static final String LESS = "<";

    /**
     * 大于
     */
    public static final String GREATER = ">";

    /**
     * 分号
     */
    public static final String SPLIT_SYMBOL = ";";

    /**
     * 逗号
     */
    public static final String COMMA_SYMBOL = ",";

    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";

    public static final String SLASH = "/";
}
