package com.wondertek.bigdata.offline.service.converter.server;

import com.mysql.jdbc.Statement;
import com.wondertek.bigdata.offline.dimension.key.KeyBaseDimension;
import com.wondertek.bigdata.offline.dimension.key.base.*;
import com.wondertek.bigdata.offline.service.converter.IDimensionConverter;
import com.wondertek.bigdata.offline.util.JdbcManager;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.ipc.ProtocolSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 实现了DimensionConverter
 */
public class DimensionConverterImpl implements IDimensionConverter {
    private static final Logger logger = LoggerFactory.getLogger(DimensionConverterImpl.class);
    private ThreadLocal<Connection> localConn = new ThreadLocal<>();

    private Map<String, Integer> cache = new LinkedHashMap<String, Integer>() {
        /**
         *用于服务器端保存维度和对应的id值
         * 超过5000条时，删除旧条目
         */
        private static final long serialVersionUID = -3084359201061689731L;

        @Override
        protected boolean removeEldestEntry(Entry<String, Integer> eldest) {
            // 缓存容量， 如果这里返回true，那么删除最早加入的数据
            return this.size() > 5000;
        }
    };

    private Connection getConnection() throws SQLException {
        Connection conn ;
        synchronized (this) {
            Configuration conf = HBaseConfiguration.create();
            conf.addResource("output-collector.xml");
            conf.addResource("query-mapping.xml");
            conf.addResource("transformer-env.xml");
            conn = localConn.get();
            try {
                        if (conn == null || conn.isClosed() || !conn.isValid(3)) {
                            conn = JdbcManager.getConnection(conf, "report");
                        }
                    } catch (SQLException e) {
                        try {
                            if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e1) {
                    // nothings
                    e1.printStackTrace();
                }
                conn = JdbcManager.getConnection(conf, "report");
            }
            this.localConn.set(conn);
        }
        return conn;
    }

    public DimensionConverterImpl() {
        // 添加关闭的钩子，jvm关闭时，会触发该线程执行
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("开始关闭数据库......");
                Connection conn = localConn.get();
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                    }
                }
                logger.info("关闭数据库成功！");
            }
        }));
    }

    /**
     *
     * @param value 具体维度对象
     * @return  返回维度ID
     * @throws IOException
     */
    @Override
    public int getDimensionIdByValue(KeyBaseDimension value) throws IOException {
        /**
         * 根据值获取ID 即先根据值到mysql数据表中查询出记录，如果存在记录，则直接取id，如果没有则先插入一条新的记录然后在取id
         */
        String cacheKey = DimensionConverterImpl.buildCacheKey(value); // 获取cache
        // key
        if (this.cache.containsKey(cacheKey)) {
            return this.cache.get(cacheKey);
        }
        Connection conn;
        try {
            // 1. 查看数据库中是否有对应的值，有则返回
            // 2. 如果第一步中，没有值；先插入我们dimension数据， 获取id
            String[] sqls = null; // 具体执行sql数组
            if (value instanceof DateDimensionKey) {
                sqls = this.buildDateSql();
            } else if (value instanceof AppDimensionKey) {
                sqls = this.buildAppSql();
            } else if (value instanceof PlatformDimensionKey) {
                sqls = this.buildPlatformSql();
            } else if (value instanceof ChannelDimensionKey) {
                sqls = this.buildChannelSql();
            } else if (value instanceof VersionDimensionKey) {
                sqls = this.buildVersionSql();
            } else if (value instanceof AreaDimensionKey) {
                sqls = this.buildAreaSql();
            } else if (value instanceof IspDimensionKey) {
                sqls = this.buildIspSql();
            } else {
                throw new IOException("不支持此dimensionid的获取:" + value.getClass());
            }

            conn = this.getConnection(); // 获取连接
            int id;
            synchronized (this) {
                id = this.executeSql(conn, cacheKey, sqls, value);
            }
            return id;
        } catch (Throwable e) {
            logger.error("操作数据库出现异常", e);
            throw new IOException(e);
        }
    }

    /**
     * 创建cache key
     *
     * @param dimension
     * @return
     */
    public static String buildCacheKey(KeyBaseDimension dimension) {
        StringBuilder sb = new StringBuilder();
        if (dimension instanceof DateDimensionKey) {
            sb.append("date_dimension");
            DateDimensionKey date = (DateDimensionKey) dimension;
            sb.append(date.getYear()).append(date.getSeason()).append(date.getMonth());
            sb.append(date.getWeek()).append(date.getDay()).append(date.getType());
        } else if (dimension instanceof AppDimensionKey) {
            sb.append("app_dimension");
            AppDimensionKey app = (AppDimensionKey) dimension;
            sb.append(app.getAppId()).append(app.getAppName());
        } else if (dimension instanceof PlatformDimensionKey) {
            sb.append("platform_dimension");
            PlatformDimensionKey platform = (PlatformDimensionKey) dimension;
            sb.append(platform.getPlatformId()).append(platform.getPlatformName());
        } else if (dimension instanceof ChannelDimensionKey) {
            sb.append("channel_dimension");
            ChannelDimensionKey channel = (ChannelDimensionKey) dimension;
            sb.append(channel.getName());
        } else if (dimension instanceof VersionDimensionKey) {
            sb.append("version_dimension");
            VersionDimensionKey version = (VersionDimensionKey) dimension;
            sb.append(version.getName());
        } else if (dimension instanceof AreaDimensionKey) {
            sb.append("area_dimension");
            AreaDimensionKey area = (AreaDimensionKey) dimension;
            sb.append(area.getCountry()).append(area.getProvince()).append(area.getCity()).append(area.getType());
        } else if (dimension instanceof IspDimensionKey) {
            sb.append("isp_dimension");
            IspDimensionKey isp = (IspDimensionKey) dimension;
            sb.append(isp.getName());
        }

        if (sb.length() == 0) {
            throw new RuntimeException("无法创建指定dimension的cachekey：" + dimension.getClass());
        }
        return sb.toString();
    }

    /**
     * 设置参数
     *
     * @param pstmt
     * @param dimension
     * @throws SQLException
     */
    private void setArgs(PreparedStatement pstmt, KeyBaseDimension dimension) throws SQLException {
        int i = 0;
        if (dimension instanceof DateDimensionKey) {
            DateDimensionKey date = (DateDimensionKey) dimension;
            pstmt.setInt(++i, date.getYear());
            pstmt.setInt(++i, date.getSeason());
            pstmt.setInt(++i, date.getMonth());
            pstmt.setInt(++i, date.getWeek());
            pstmt.setInt(++i, date.getDay());
            pstmt.setString(++i, date.getType());
            pstmt.setDate(++i, new Date(date.getCalendar().getTime()));
        } else if (dimension instanceof AppDimensionKey) {
            AppDimensionKey app = (AppDimensionKey) dimension;
            pstmt.setString(++i, app.getAppId());
            pstmt.setString(++i, app.getAppName());
        } else if (dimension instanceof PlatformDimensionKey) {
            PlatformDimensionKey platform = (PlatformDimensionKey) dimension;
            pstmt.setString(++i, platform.getPlatformId());
            pstmt.setString(++i, platform.getPlatformName());
        } else if (dimension instanceof ChannelDimensionKey) {
            ChannelDimensionKey channel = (ChannelDimensionKey) dimension;
            pstmt.setString(++i, channel.getName());
        } else if (dimension instanceof VersionDimensionKey) {
            VersionDimensionKey version = (VersionDimensionKey) dimension;
            pstmt.setString(++i, version.getName());
        } else if (dimension instanceof AreaDimensionKey) {
            AreaDimensionKey area = (AreaDimensionKey) dimension;
            pstmt.setString(++i, area.getCountry());
            pstmt.setString(++i, area.getProvince());
            pstmt.setString(++i, area.getCity());
            pstmt.setString(++i, area.getType());
        } else if (dimension instanceof IspDimensionKey) {
            IspDimensionKey isp = (IspDimensionKey) dimension;
            pstmt.setString(++i, isp.getName());
        }
    }

    /**
     * 创建date dimension相关sql
     *
     * @return
     */
    private String[] buildDateSql() {
        String querySql = "SELECT `id` FROM `dimension_date` WHERE `year` = ? AND `season` = ? AND `month` = ? AND `week` = ? AND `day` = ? AND `type` = ? AND `calendar` = ?";
        String insertSql = "INSERT INTO `dimension_date`(`year`, `season`, `month`, `week`, `day`, `type`, `calendar`) VALUES(?, ?, ?, ?, ?, ?, ?)";
        return new String[] { querySql, insertSql };
    }

    private String[] buildAppSql() {
        String querySql = "SELECT `id` FROM `dimension_app` WHERE `app_id` = ? AND `app_name` = ?";
        String insertSql = "INSERT INTO `dimension_app`(`app_id`, `app_name`) VALUES(?, ?)";
        return new String[] { querySql, insertSql };
    }

    private String[] buildPlatformSql() {
        String querySql = "SELECT `id` FROM `dimension_platform` WHERE `platform_id` = ? AND `platform_name` = ?";
        String insertSql = "INSERT INTO `dimension_platform`(`platform_id`, `platform_name`) VALUES(?, ?)";
        return new String[] { querySql, insertSql };
    }

    private String[] buildChannelSql() {
        String querySql = "SELECT `id` FROM `dimension_channel` WHERE `channel` = ?";
        String insertSql = "INSERT INTO `dimension_channel`(`channel`) VALUES(?)";
        return new String[] { querySql, insertSql };
    }

    private String[] buildVersionSql() {
        String querySql = "SELECT `id` FROM `dimension_version` WHERE `version` = ?";
        String insertSql = "INSERT INTO `dimension_version`(`version`) VALUES(?)";
        return new String[] { querySql, insertSql };
    }

    private String[] buildAreaSql() {
        String querySql = "SELECT `id` FROM `dimension_area` WHERE `country` = ? AND `province` = ? AND `city` = ? AND `type` = ?";
        String insertSql = "INSERT INTO `dimension_area`(`country`, `province`, `city`, `type`) VALUES(?, ?, ?, ?)";
        return new String[] { querySql, insertSql };
    }

    private String[] buildIspSql() {
        // TODO: 2020.4.8 把isp改为operator
        String querySql = "SELECT `id` FROM `dimension_isp` WHERE `isp` = ?";
        String insertSql = "INSERT INTO `dimension_isp`(`operator`) VALUES(?)";
        return new String[] { querySql, insertSql };
    }

    /**
     * 具体执行sql的方法
     *
     * @param conn
     * @param cacheKey
     * @param sqls
     * @param dimension
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("resource")
    private int executeSql(Connection conn, String cacheKey, String[] sqls, KeyBaseDimension dimension)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sqls[0]); // 创建查询sql的pstmt对象
            // 设置参数
            this.setArgs(pstmt, dimension);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // 返回值
            }else {
                // 代码运行到这儿，表示该dimension在数据库中不存储，进行插入，后面一个参数的作用是将mysql的自增长的主键id返回
                pstmt = conn.prepareStatement(sqls[1], Statement.RETURN_GENERATED_KEYS);
                // 设置参数
                this.setArgs(pstmt, dimension);
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys(); // 获取返回的自动生成的id
                if (rs.next()) {
                    this.cache.put(cacheKey, rs.getInt(1));
                    return rs.getInt(1); // 获取返回值
                }
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Throwable e) {
                    // nothing
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Throwable e) {
                    // nothing
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("从数据库获取id失败");
    }

    @Override
    public long getProtocolVersion(String protocol, long clientVersion) throws IOException {
        return IDimensionConverter.versionID;
}

    @Override
    public ProtocolSignature getProtocolSignature(String protocol, long clientVersion,
                                                  int clientMethodsHash) throws IOException {
        return null;
    }

}
