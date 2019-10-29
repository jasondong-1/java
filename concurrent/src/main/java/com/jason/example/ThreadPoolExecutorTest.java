package com.jason.example;


import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        ThreadPoolExecutor pools = new ThreadPoolExecutor(
                2,
                8,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        //pools.prestartCoreThread();
        //pools.prestartAllCoreThreads();
        int n = 0;
        for (int i = 0; i < 20; i++) {
            final int finalI = i;
            pools.execute(new Runnable() {
                @Override
                public void run() {
                    for (int m = 0; m < 10; m++) {
                        System.out.println(finalI);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }
        List<Runnable> list = pools.shutdownNow();
        //pools.shutdown();
        //pools.awaitTermination(100,TimeUnit.SECONDS);
        System.out.println("=====================" + list.size());
        //pools.getCompletedTaskCount();
        //pools.getCorePoolSize();
    }
}
