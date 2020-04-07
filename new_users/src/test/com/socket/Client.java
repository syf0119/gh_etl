package com.socket;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 8889);


            OutputStream os = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(os);
            writer.write("客户端发送信息");


            writer.flush();

            writer.write("客户端发送信息2");





            socket.shutdownOutput();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String info;
            while ((info = reader.readLine()) != null) {
                System.out.println("客户端：" + info);
            }
            System.out.println("info"+info);






            socket.shutdownInput();
            reader.close();

            writer.close();

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
