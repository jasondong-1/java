package com.jason.core;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class HookTest {
    public static void main(String[] args) throws Exception {
        final AtomicLong al = new AtomicLong(0);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("byebye");
                System.out.println(al.getAndAdd(1l));
            }
        });
        System.out.println(System.getProperty("user.dir"));
        for (int i = 0; i < 5; i++) {
            System.out.println(String.format("倒计时：%d", i));
            TimeUnit.SECONDS.sleep(1);
            //throw new Exception();
        }
        System.out.println(al.getAndIncrement());
        Thread.sleep(1000);
    }
}
