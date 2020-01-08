package com.jason.core;

public class HookTest {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("byebye");
            }
        });
        for (int i = 0; i < 5; i++) {
            System.out.println(String.format("倒计时：%d", i));
        }
    }
}
