package com.wondertek.bigdata.offline.util;

import com.wondertek.bigdata.offline.common.GlobalConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;

import java.sql.*;

/**
 * jdbc管理
 * 
 *
 */
public class JdbcManager {
    /**
     * 根据配置获取获取关系型数据库的jdbc连接
     * 
     * @param conf
     *            hadoop配置信息
     * @param flag
     *            区分不同数据源的标志位
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(Configuration conf, String flag) throws SQLException {
        String driverStr = String.format(GlobalConstants.JDBC_DRIVER, flag);
        String urlStr = String.format(GlobalConstants.JDBC_URL, flag);
        String usernameStr = String.format(GlobalConstants.JDBC_USERNAME, flag);
        String passwordStr = String.format(GlobalConstants.JDBC_PASSWORD, flag);

        String driverClass = conf.get(driverStr).trim();
        String url = conf.get(urlStr).trim();
        String username = conf.get(usernameStr).trim();
        String password = "";
        if (StringUtils.isNotBlank(conf.get(passwordStr))){
            password = conf.get(passwordStr).trim();
        }
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            // nothing
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection getPhoenixConnection(Configuration conf) throws SQLException {
        String driverClass = conf.get(GlobalConstants.PHOENIX_JDBC_DRIVER).trim();
        String url = conf.get(GlobalConstants.PHOENIX_JDBC_URL).trim();
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            // nothing
            e.printStackTrace();
        }
        return DriverManager.getConnection(url);
    }

    /**
     * 关闭数据库连接
     * 
     * @param conn
     * @param stmt
     * @param rs
     */
    public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // nothigns
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // nothings
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // nothings
            }
        }
    }
}
