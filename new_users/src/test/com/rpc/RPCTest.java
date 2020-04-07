package com.rpc;

import com.rpc.client.RPCClient;

import java.io.IOException;

public class RPCTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        Object remoteProxyObj = RPCClient.getRemoteProxyObj(1);

        Class clazz=(Class)remoteProxyObj;

        Object instance = clazz.newInstance();

       Person person= (Person)instance;


      person.say();


    }
}
