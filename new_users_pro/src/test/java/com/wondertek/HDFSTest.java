package com.wondertek;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HDFSTest {
    public static void main(String[] args) throws IOException {
        FileSystem hdfs = FileSystem.get(new Configuration());

        File sourceFile = new File("C:\\Users\\syf\\Desktop\\223.72.189.23");
        File[] oneLoopFiles = sourceFile.listFiles();
        //第一层project_id 4/5
        for (File oneLoopFile : oneLoopFiles) {
            //第二层 create_time 20200313
            File[] twoLoopFiles = oneLoopFile.listFiles();
            for (File twoLoopFile : twoLoopFiles) {
                //第三层 mysql_id 578833
                List<String> resultJsonList = genJson(twoLoopFile);
                for (String jsos : resultJsonList) {
//                        System.out.println(json);
                    Path path = new Path("/flume/shirx/");
                    if (!hdfs.exists(path)) {
                        hdfs.create(path);
                    }
                    System.out.println(path);
                    FSDataOutputStream fsDataOutputStream = hdfs.create(new Path(path + twoLoopFile.getName() +"/"+ UUID.randomUUID().toString()));



                    int bufferSize = 60000;
                    int i = 0;
                    int sum = 0;

                    while (i < jsos.length()) {
                        int endIdx = java.lang.Math.min(jsos.length(), i + bufferSize);
                        String jsosPart = jsos.substring(i, endIdx);
                        fsDataOutputStream.writeUTF(jsosPart);
                        sum += jsosPart.length();
                        i += bufferSize;
                    }
                    assert sum == jsos.length();

                }

            }
        }
    }

    public static List<String> genJson(File file) throws IOException {
        File[] files = file.listFiles();
        BufferedReader bufferedReader;
        List<String> list = new ArrayList<>();
        String path = file.getPath();
        String id = path.substring(7).replaceAll("\\\\", "-");

        for (File file1 : files) {
            File[] listFiles = file1.listFiles();
            for (File listFile : listFiles) {
                bufferedReader = new BufferedReader(new FileReader(listFile));
                String readLine = bufferedReader.readLine();
                list.add(readLine);
            }
        }

        return combineJson(list, id);
    }

    public static List<String> combineJson(List<String> listStr, String id) {


        List<String> list1 = new ArrayList<>();

        for (String var1 : listStr) {
            if (var1.startsWith("{")) {
                JSONObject object = JSON.parseObject(var1);
                object.put("jsonId", id);
                list1.add(object.toJSONString());


            } else {

                String s = "{" + "jsonId" + ":" + id + ",str" + ":" + var1 + "}";
                list1.add(s);

            }
        }

        return list1;
    }

}
