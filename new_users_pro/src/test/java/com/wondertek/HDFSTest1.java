package com.wondertek;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HDFSTest1 {
    public static  int count =0;

    public static void main(String[] args) throws IOException {
                FileSystem hdfs = FileSystem.get(new Configuration());
//        FSDataOutputStream outputStream = hdfs.create(new Path("/flume/syf/data"));
//        outputStream.writeUTF("hello asdasdas");


        // RemoteIterator<LocatedFileStatus> listFiles = hdfs.listFiles(new Path("/flume/syf/out"), true);

        //System.out.println(readLine);
        File file = new File("F:\\nas");
        List<File> list = new ArrayList<>();

        fileTree(file, list);

        for (File file1 : list) {

            //F:\nas\4\20200401\1
            //F:\nas\5\20200401\2
            List<String> jsons = genJson(file1);
            //System.out.println(jsons);

            for (String json : jsons) {
                System.out.println("-------------------------------------------");
                System.out.println(json);
                Path path = new Path("/flume/syf");
                if(!hdfs.exists(path)){
                  //  hdfs.create(path);
                }
                FSDataOutputStream fsDataOutputStream = hdfs.create(new Path("/flume/syf/"+ UUID.randomUUID().toString()));

               fsDataOutputStream.writeUTF(json);


            }


        }
        System.out.println(count);

    }

//
//        System.out.println(list);
//

//        boolean delete = hdfs.delete(new Path("/flume/syf/out1"), true);
//        FSDataInputStream inputStream = hdfs.open(new Path("/flume/syf/access_20200330"));
//       byte[] buffer=new byte[1];
//       int len;
//
//     while ((len=inputStream.read(buffer))>0){
//            String s = new String(buffer, 0, len);
//            //System.out.println(s);
//            break;
//
//
//        }
//

//
//     outputStream.writeUTF("hello world!!!!!!!!!!!!!!");
//
//
//
//
//    }
    public static void fileTree(File file,List<File> list){
        //System.out.println(file.getParent());
        if(file.getParent().endsWith("20200401")){
            //System.out.println("最新的文件"+file);
            list.add(file);
        }
      // System.out.println(file.getName());
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File file1 : files) {
                fileTree(file1,list);
            }
        }



    }
    //F:\nas\4\20200401\1
    public static List<String> genJson(File file) throws IOException {
        File[] files = file.listFiles();
        BufferedReader bufferedReader;
        List<String> list=new ArrayList<>();
//F:\nas\4\20200401\1
        String path = file.getPath();

        String id = path.substring(7).replaceAll("\\\\", "-");
        //  path.substring(7).




        for (File file1 : files) {

            bufferedReader = new BufferedReader(new FileReader(file1));
            String readLine = bufferedReader.readLine();
            list.add(readLine);

            count++;


        }

       return  combineJson(list,id);


    }
    //static  List<String> list1=new ArrayList<>();
    public static List<String> combineJson(List<String> listStr,String id){


 List<String> list1=new ArrayList<>();
//        JSONArray jsonArray1 = JSON.parseArray(var1);

        for (String var1 : listStr) {
            if(var1.startsWith("{")){
                JSONObject object = JSON.parseObject(var1);
                object.put("jsonId",id);
                list1.add(object.toJSONString());


            }else {

                String s ="{" + "jsonId" + ":"+id+",str"+":"+var1+"}";
                list1.add(s);

                //数组
//                JSONArray jsonArray1 = JSON.parseArray(var1);
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("id",id);
//                jsonArray1.add(jsonObject);
//
//                list.add(jsonArray1.toJSONString());


            }
        }


//        JSONObject json2 = JSON.parseObject(var2);
//        JSONObject json3 = JSON.parseObject(var3);
//
//
//        json2.put("id",id);
//        json3.put("id",id);


        return list1;
    }
    @Test
    public void test(){
        String str="F:\\nas\\4\\20200401\\1";

        System.out.println(str.substring(7).replaceAll("\\\\","-"));

    }
    @Test
    public void test2() throws IOException {
        FileSystem hdfs = FileSystem.get(new Configuration());
        FSDataOutputStream outputStream = hdfs.create(new Path("/flume/syf/data"));
        outputStream.writeUTF("hello asdasdas");

    }
}