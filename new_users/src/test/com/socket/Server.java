package com.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws IOException {
        int port = 8889;

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {

            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket);
            executors.execute(serverThread);
            System.out.println(socket.getLocalAddress() + ":" + socket.getLocalPort());


            System.out.println("开启线程");

        }


    }
}
