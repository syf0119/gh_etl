package com.wondertek.util;

public class TestRuntime {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("hello");
            }
        });

        System.out.println("hi");
    }
}
