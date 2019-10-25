package com.jason.core;

public class TryCatchx {

    public static void test1() {
        try {
            Integer.valueOf("kk");
        } catch (Exception e) {
            throw e;
        }
        System.out.println("test1");
    }

    public static void test2() {
        try {
            Integer.valueOf("kk");
            Thread.sleep(411);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("test2");
    }

    public static void main(String[] args) {
        test1();
        test2();
    }
}
