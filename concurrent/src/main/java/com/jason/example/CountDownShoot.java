package com.jason.example;


import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 射击，所有士兵都到位后才能射击，一同射击
 */

public class CountDownShoot {
    public static void main(String[] args) {
        int num = 6;
        final CountDownLatch countDownLatch = new CountDownLatch(num);
        final Random random = new Random();
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread("战士-" + i) {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " 到位！");
                        Thread.sleep(random.nextInt(3000));
                        System.out.println(Thread.currentThread().getName() + " 准备就绪！");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                    try {
                        countDownLatch.await();
                        System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getName() + "  shoot！");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            thread.start();

        }

    }
}
