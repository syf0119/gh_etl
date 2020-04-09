package com.wondertek.bigdata.offline.mr.stats.newusers;

import com.wondertek.bigdata.offline.common.DateEnum;
import com.wondertek.bigdata.offline.common.EventLogConstants;
import com.wondertek.bigdata.offline.common.GlobalConstants;
import com.wondertek.bigdata.offline.dimension.key.base.DateDimensionKey;
import com.wondertek.bigdata.offline.dimension.key.stats.StatsInstallUser;
import com.wondertek.bigdata.offline.dimension.value.MapWritableValue;
import com.wondertek.bigdata.offline.mr.TransformerOutputFormat;
import com.wondertek.bigdata.offline.util.JdbcManager;
import com.wondertek.bigdata.offline.util.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatsInstallUserRunner implements Tool {

    private Configuration conf;

    @Override
    public int run(String[] strings) throws Exception {

        processArgs(this.conf,strings);

        Job job = Job.getInstance(this.conf);
        job.setJobName(this.getClass().getName());

        job.setJarByClass(StatsInstallUserRunner.class);

        job.setMapperClass(StatsInstallUserMapper.class);

        job.setReducerClass(StatsInstallUserReducer.class);

        job.setOutputKeyClass(StatsInstallUser.class);
        job.setOutputValueClass(MapWritableValue.class);



        this.initHBaseConfig(job);

        job.setOutputFormatClass(TransformerOutputFormat.class);

        job.setNumReduceTasks( 1);

        if (job.waitForCompletion(true)) {
            this.calculateTotalUsers(conf);
            return 0;
        } else {
            return -1;
        }


    }

    private void calculateTotalUsers(Configuration conf) {
        // 获取数据库连接
        java.sql.Connection conn = null;
        try {
            conn = JdbcManager.getConnection(conf, GlobalConstants.WAREHOUSE_OF_REPORT);

            long date = TimeUtil.parseString2Long(conf.get(GlobalConstants.RUNNING_DATE_PARAMES));
            // 开始获取当前时间维度
            DateDimensionKey currentDayDimension = DateDimensionKey.buildDate(date, DateEnum.DAY);

            // 获取当前时间维度的维度id
            int currentDayDimensionId = getDateDimensionId(conn, currentDayDimension);

            // 开始获取上一个时间维度的维度信息
            DateDimensionKey preDayDimension = DateDimensionKey
                    .buildDate(date - GlobalConstants.DAY_OF_MILLISECONDS, DateEnum.DAY);

            // 开始获取上一个时间维度的维度id
            int preDayDimensionId = getDateDimensionId(conn, preDayDimension);

            // 可以开始更新数据
            // 1、更新stats_user_channel表中的总用户值
            this.updateNewInstallUsers(conn, currentDayDimensionId, preDayDimensionId);
        } catch (Exception e) {
            throw new RuntimeException("更新总访客数量出现异常", e);
        } finally {
            JdbcManager.closeConnection(conn, null, null);
        }
    }
    /**
     * 根据指定的参数获取数据库中对于的维度id，如果数据库中不存在，那么返回-1
     *
     * @param conn
     * @param dateDimension
     * @return
     * @throws SQLException
     */
    private int getDateDimensionId(java.sql.Connection conn, DateDimensionKey dateDimension)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(
                    "SELECT `id` FROM `dimension_date` WHERE `year` = ? AND `season` = ? AND `month` = ? AND `week` = ? AND `day` = ? AND `type` = ? AND `calendar` = ?");
            int i = 0;
            pstmt.setInt(++i, dateDimension.getYear());
            pstmt.setInt(++i, dateDimension.getSeason());
            pstmt.setInt(++i, dateDimension.getMonth());
            pstmt.setInt(++i, dateDimension.getWeek());
            pstmt.setInt(++i, dateDimension.getDay());
            pstmt.setString(++i, dateDimension.getType());
            pstmt.setDate(++i, new java.sql.Date(dateDimension.getCalendar().getTime()));
            rs = pstmt.executeQuery();
            System.out.println(pstmt);
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } finally {
            JdbcManager.closeConnection(null, pstmt, rs);
        }
    }
    /**
     * 修改stats_user_channel表中的总用户数
     *
     * @param conn
     * @param currentDateDimensionId
     * @param preDateDimensionId
     * @throws SQLException
     */
    private void updateNewInstallUsers(java.sql.Connection conn, int currentDateDimensionId,
                                                  int preDateDimensionId) throws SQLException {
        if (currentDateDimensionId == -1) {
            return;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Map<String, Integer> valueMap = new HashMap<>();
            // 1、获取上一个时间维度的值
            if (preDateDimensionId != -1) {
                // 上一个时间维度存在
                pstmt = conn.prepareStatement(
                        "SELECT app_dimension_id,platform_dimension_id,ch_dimension_id,area_dimension_id,operator_dimension_id,ver_,total_install_users from stats_install_user where date_dimension_id = ?");
                pstmt.setInt(1, preDateDimensionId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int appDimensionId = rs.getInt("app_dimension_id");
                    int platformDimensionId = rs.getInt("platform_dimension_id");
                    int channelDimensionId = rs.getInt("ch_dimension_id");
                    int area_dimension_id = rs.getInt("area_dimension_id");
                    int operator_dimension_id = rs.getInt("operator_dimension_id");
                    int ver_ = rs.getInt("ver_");
                    int totalUsers = rs.getInt("total_install_users");
                    valueMap.put(String.valueOf(appDimensionId + GlobalConstants.KEY_SEPARATOR + platformDimensionId + GlobalConstants.KEY_SEPARATOR + channelDimensionId+GlobalConstants.KEY_SEPARATOR+area_dimension_id+GlobalConstants.KEY_SEPARATOR+operator_dimension_id+GlobalConstants.KEY_SEPARATOR+ver_), totalUsers);
                }
            }

            // 关闭连接
            JdbcManager.closeConnection(null, pstmt, rs);

            // 2、获取当前时间维度的新增访客
            pstmt = conn.prepareStatement(
                    "SELECT app_dimension_id,platform_dimension_id,ch_dimension_id,area_dimension_id,operator_dimension_id,ver_,new_install_users from stats_install_user where date_dimension_id = ?");
            pstmt.setInt(1, currentDateDimensionId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int appDimensionId = rs.getInt("app_dimension_id");
                int platformDimensionId = rs.getInt("platform_dimension_id");
                int channelDimensionId = rs.getInt("ch_dimension_id");
                int area_dimension_id = rs.getInt("area_dimension_id");
                int operator_dimension_id = rs.getInt("operator_dimension_id");
                int ver_ = rs.getInt("ver_");
                int newUsers = rs.getInt("new_install_users");
                String key = String.valueOf(appDimensionId + GlobalConstants.KEY_SEPARATOR + platformDimensionId + GlobalConstants.KEY_SEPARATOR + channelDimensionId+GlobalConstants.KEY_SEPARATOR+area_dimension_id+GlobalConstants.KEY_SEPARATOR+operator_dimension_id+GlobalConstants.KEY_SEPARATOR+ver_);
                if (valueMap.containsKey(key)) {
                    newUsers += valueMap.get(key);
                }
                valueMap.put(key, newUsers);
            }

            // 关闭连接
            JdbcManager.closeConnection(null, pstmt, rs);

            // 3、插入数据库
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO `stats_install_user`(`date_dimension_id`,`app_dimension_id`,`platform_dimension_id`,"
                    + " `ch_dimension_id`,`area_dimension_id`,`operator_dimension_id`,`ver_`, `total_install_users`, `created`) "
                    + " VALUES(?, ?, ?, ?, ?, ?,?,?,?) ON DUPLICATE KEY UPDATE `total_install_users` = ?");
            int count = 0;
            for (Map.Entry<String, Integer> entry : valueMap.entrySet()) {
                String[] keys = entry.getKey().split(GlobalConstants.KEY_SEPARATOR);
                pstmt.setInt(1, currentDateDimensionId);
                pstmt.setInt(2, Integer.valueOf(keys[0]));
                pstmt.setInt(3, Integer.valueOf(keys[1]));
                pstmt.setInt(4, Integer.valueOf(keys[2]));
                pstmt.setInt(5, Integer.valueOf(keys[3]));
                pstmt.setInt(6, Integer.valueOf(keys[4]));
                pstmt.setInt(7, Integer.valueOf(keys[5]));
                pstmt.setInt(8, entry.getValue());
                pstmt.setDate(9, new Date(System.currentTimeMillis()));
                pstmt.setInt(10, entry.getValue());

                pstmt.addBatch();
                if (++count > 500) {
                    pstmt.executeBatch();
                    conn.commit();
                }
            }
        } finally {
            try {
                pstmt.executeBatch();
                conn.commit();
                conn.setAutoCommit(true);
            } catch (Exception e) {
                // nothings
            } finally {
                JdbcManager.closeConnection(null, pstmt, rs);
            }
        }
    }


    private void initHBaseConfig(Job job) throws IOException {

        FilterList filterList = new FilterList();

        filterList.addFilter(new SingleColumnValueFilter(EventLogConstants.EVENT_LOGS_FAMILY_NAME_BYTES,
                Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME),
                CompareFilter.CompareOp.EQUAL, Bytes.toBytes(EventLogConstants.EventEnum.LAUNCH.alias)
        ));

        filterList.addFilter(new SingleColumnValueFilter(EventLogConstants.EVENT_LOGS_FAMILY_NAME_BYTES,
                Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_USER_ID),
                CompareFilter.CompareOp.EQUAL, new NullComparator()

        ));


        String[] columns = new String[]{
                // 不管mapper中是否用到event的值，在column中都必须有
                EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME,
                EventLogConstants.LOG_COLUMN_NAME_APP,
                EventLogConstants.LOG_COLUMN_NAME_PLATFORM,
                EventLogConstants.LOG_COLUMN_NAME_CHANNEL,
                EventLogConstants.LOG_COLUMN_NAME_VERSION,
                EventLogConstants.LOG_COLUMN_NAME_COUNTRY,
                EventLogConstants.LOG_COLUMN_NAME_PROVINCE,
                EventLogConstants.LOG_COLUMN_NAME_CITY,
                EventLogConstants.LOG_COLUMN_NAME_ISP,
                EventLogConstants.LOG_COLUMN_NAME_UUID,
        };


        filterList.addFilter(this.getColumnFilter(columns));

        String runDate = conf.get(GlobalConstants.RUNNING_DATE_PARAMES);

        Connection conn = null;
        Admin admin = null;
        ArrayList<Scan> scans = new ArrayList<>();

        try {
            conn = ConnectionFactory.createConnection(this.conf);
            admin = conn.getAdmin();
            String tableName = EventLogConstants.HBASE_NAME_EVENT_LOGS + GlobalConstants.UNDERLINE + runDate.replace(GlobalConstants.KEY_SEPARATOR, "");
            if(admin.tableExists(TableName.valueOf(tableName))){
                 Scan scan = new Scan();
                scan.setAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME,Bytes.toBytes(tableName));
                scan.setFilter(filterList);

                scans.add(scan);


            }else{
                throw new TableNotFoundException(tableName);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (admin != null) {
                    admin.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        TableMapReduceUtil.initTableMapperJob(scans,StatsInstallUserMapper.class, StatsInstallUser.class, Text.class,job);


    }


    /**
     * 指定多个列名（Qualifier）前缀
     *
     * @param columns
     * @return
     */
    private Filter getColumnFilter(String[] columns) {
        int length = columns.length;
        byte[][] filter = new byte[length][];
        for (int i = 0; i < length; i++) {
            filter[i] = Bytes.toBytes(columns[i]);
        }
        return new MultipleColumnPrefixFilter(filter);
    }

    /**
     * 处理参数
     *
     * @param conf
     * @param args
     */
    private void processArgs(Configuration conf, String[] args) {
        String date = null;
        for (int i = 0; i < args.length; i++) {
            if ("-d".equals(args[i])) {
                if (i + 1 < args.length) {
                    date = args[++i];
                }
            }
        }
        //如果没有给出具体时间，则默认时间为查询时间当日的前一天
        if (StringUtils.isBlank(date) || !TimeUtil.isValidateRunningDate(date)) {
            date = TimeUtil.getYesterday();
        }
        conf.set(GlobalConstants.RUNNING_DATE_PARAMES, date);
    }


    @Override
    public void setConf(Configuration configuration) {
        configuration.addResource("output-collector.xml");
        configuration.addResource("query-mapping.xml");
        configuration.addResource("transformer-env.xml");
        this.conf = HBaseConfiguration.create(configuration);


    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }
}
