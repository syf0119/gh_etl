package com.wondertek.util;

public class TestStringFormat {
    public static void main(String[] args) {
        String flag="tom";
        String format = String.format("Hi,%s", flag);
        System.out.println(format);
    }
}
