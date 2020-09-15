package com.jason.example;

import java.util.concurrent.TimeUnit;

public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("被打断......");
                    }
                    System.out.println("hello");
                }
            }
        };
        t.start();
        TimeUnit.SECONDS.sleep(5);
        t.interrupt(); //不会打断
        System.out.println("interrupt");

    }


}
