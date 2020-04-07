package com.wondertek;

public class ThreadTest {
    public static void main(String[] args) {
        ThreadLocal<String> threadLocal=new ThreadLocal<>();
        String s = threadLocal.get();
        threadLocal.set("hello");



        System.out.println(threadLocal.get());
    }
}
