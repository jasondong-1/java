package com.jason.example;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(3);
        final Random random = new Random();
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread("countdown-" + i) {
                @Override
                public void run() {
                    try {
                        Thread.sleep(random.nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                    System.out.println(Thread.currentThread().getName() + " " + "countdown");
                }
            };
            thread.start();
        }
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread("await-" + i) {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + " " + "await");
                }
            };
            thread.start();
        }
    }
}
