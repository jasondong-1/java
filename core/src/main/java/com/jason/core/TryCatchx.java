package com.jason.core;

import java.io.File;
import java.util.Arrays;

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
