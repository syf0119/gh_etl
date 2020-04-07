package com.wondertek.demension;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class NewUsersKpi implements WritableComparable<NewUsersKpi>, DBWritable {

    private int appId;
    private int platformId;
    private String channel;
    private String key_date;
    private int new_user_num;

    public NewUsersKpi() {
    }

    public NewUsersKpi(int appId, int platformId, String channel, String key_date, int new_user_num) {
        this.appId = appId;
        this.platformId = platformId;
        this.channel = channel;
        this.key_date = key_date;
        this.new_user_num = new_user_num;
    }


    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getKey_date() {
        return key_date;
    }

    public void setKey_date(String key_date) {
        this.key_date = key_date;
    }

    public int getNew_user_num() {
        return new_user_num;
    }

    public void setNew_user_num(int new_user_num) {
        this.new_user_num = new_user_num;
    }

    @Override
    public String toString() {
        return "NewUsersKpi{" +
                "appId='" + appId + '\'' +
                ", platformId='" + platformId + '\'' +
                ", channel='" + channel + '\'' +
                ", key_date=" + key_date +
                ", new_user_num=" + new_user_num +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewUsersKpi that = (NewUsersKpi) o;
        return new_user_num == that.new_user_num &&
                Objects.equals(appId, that.appId) &&
                Objects.equals(platformId, that.platformId) &&
                Objects.equals(channel, that.channel) &&
                Objects.equals(key_date, that.key_date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(appId, platformId, channel, key_date, new_user_num);
    }

    @Override
    public int compareTo(NewUsersKpi o) {
        int temp = this.appId - o.appId;
        if (temp != 0) return temp;
        temp = this.platformId - o.platformId;
        if (temp != 0) return temp;
        temp = this.channel.compareTo(o.channel);
        return temp;
    }


    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.appId);
        dataOutput.writeInt(this.platformId);
        dataOutput.writeUTF(this.channel);
        //dataOutput.writeUTF(this.key_date);
        //dataOutput.writeInt(this.new_user_num);

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

        this.appId = dataInput.readInt();
        this.platformId = dataInput.readInt();
        this.channel = dataInput.readUTF();
        //this.key_date = dataInput.readUTF();
        //this.new_user_num = dataInput.readInt();


    }

    @Override
    public void write(PreparedStatement ps) throws SQLException {
        ps.setInt(1,this.appId);
        ps.setInt(2, this.platformId);
        ps.setString(3, this.channel);
        ps.setString(4, this.key_date);
        ps.setInt(5, this.new_user_num);


    }

    @Override
    public void readFields(ResultSet rs) throws SQLException {
        this.appId=rs.getInt(1);
        this.platformId=rs.getInt(2);
        this.channel=rs.getString(3);
        this.key_date=rs.getString(4);
        this.new_user_num=rs.getInt(5);


    }
}
