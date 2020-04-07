package com.wondertek.bigdata.offline;


import com.wondertek.bigdata.offline.mr.etl.AnalyserLogDataRunner;
import com.wondertek.bigdata.offline.mr.stats.newusers.StatsInstallUserRunner;
import org.apache.hadoop.util.ToolRunner;

public class ApplicationLauncher {
    public static void main(String[] args) {

        try {
            int exitCode = ToolRunner.run(new AnalyserLogDataRunner(), args);
            if(exitCode==1){
                System.out.println("AnalyserLogDataRunner运行失败");
            }else{
                System.out.println("AnalyserLogDataRunner运行成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int exitCode = ToolRunner.run(new StatsInstallUserRunner(), args);
            if(exitCode==1){
                System.out.println("NewInstallUsersRunner运行失败");
            }else{
                System.out.println("NewInstallUsersRunner运行成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
