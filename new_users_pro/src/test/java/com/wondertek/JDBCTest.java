package com.wondertek;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class JDBCTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.181:13306/syf_test?useUnicode=true&amp;characterEncoding=utf8&amp;autoReconnect=true&amp;failOverReadOnly=false", "cloud", "cloud");


        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dimension_app (app_id, app_name) VALUES ('111','111')");
        preparedStatement.addBatch();

        preparedStatement.addBatch("INSERT INTO dimension_app (app_id, app_name) VALUES ('222','222')");
        int[] ints1 = preparedStatement.executeBatch();
        System.out.println(Arrays.toString(ints1));
        int[] ints2 = preparedStatement.executeBatch();
        System.out.println(Arrays.toString(ints2));

        preparedStatement.close();
connection.close();

    }
}
