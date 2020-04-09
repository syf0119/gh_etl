package com.wondertek;

import org.apache.hadoop.conf.Configuration;

import java.sql.*;
import java.util.Arrays;

public class JDBCTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // String sql= "INSERT INTO stats_install_user ( date_dimension_id, app_dimension_id, platform_dimension_id, ch_dimension_id, operator_dimension_id,ver_, area_dimension_id, new_install_users )VALUES(?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE new_install_users = ?";
        Configuration conf = new Configuration();
        conf.addResource("query-mapping.xml");
        String sql ="SELECT app_dimension_id,platform_dimension_id,ch_dimension_id,total_install_users from stats_install_user where date_dimension_id = 1";
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.181:13306/syf_test?useUnicode=true&amp;characterEncoding=utf8&amp;autoReconnect=true&amp;failOverReadOnly=false", "cloud", "cloud");
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            int users = resultSet.getInt("total_install_users");
            System.out.println(users);
        }


        ps.close();
        connection.close();

    }
}
