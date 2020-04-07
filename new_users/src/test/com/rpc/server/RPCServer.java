package com.rpc.server;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RPCServer {

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
private static Map<Integer,String> map=new HashMap<>();



    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(8889);
            RPCServer.register(1,"hello client");
            while (true){
                Socket accept = serverSocket.accept();
                executorService.execute(new ServerThread(accept));
            }




        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static  void register(int id,String str ){
        RPCServer.map.put(id,str);
    }

    static  class ServerThread implements Runnable{
        private Socket socket;

        public ServerThread(Socket socket){
            this.socket=socket;
        }

        @Override
        public void run() {
            ObjectInputStream objectInputStream=null;
            ObjectOutputStream objectOutputStream=null;
            try {
                 objectInputStream = new ObjectInputStream(socket.getInputStream());
                int id = objectInputStream.readInt();

                socket.shutdownInput();
                String str = map.get(id);
                objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeUTF(str);
                objectOutputStream.flush();

                socket.shutdownOutput();





            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(objectInputStream!=null){
                    try {
                        objectInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(objectOutputStream!=null){
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }



    }







}
