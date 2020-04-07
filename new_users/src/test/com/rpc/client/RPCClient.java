package com.rpc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RPCClient {

    public static Object getRemoteProxyObj(int id) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 8889);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeInt(id);
        objectOutputStream.flush();
        socket.shutdownOutput();


        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        Object readObject = objectInputStream.readObject();

        socket.shutdownInput();


        objectInputStream.close();
        objectOutputStream.close();
        socket.close();

        return readObject;

    }


}
