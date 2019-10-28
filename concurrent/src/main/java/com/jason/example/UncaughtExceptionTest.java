package com.jason.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class UncaughtExceptionTest {
    private final BlockingQueue<String> channels = new ArrayBlockingQueue<String>(100);
    private volatile boolean inited = false;
    private volatile int index = 0;
    private static final Logger LOGGER = LoggerFactory.getLogger(UncaughtExceptionTest.class);
    private volatile boolean stop =false;
    private void put() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            try {
                channels.put("jason-" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while(!channels.isEmpty()){
            Thread.sleep(200);
        }
        stop=true;

    }

    public void init() {
        synchronized (this) {
            if (inited) {
                return;
            }
            Thread t = new Thread(new Runnablex());
            String tname = "lala-" + index++;
            t.setName(tname);
            LOGGER.info("创建线程：{}", tname);
            t.setUncaughtExceptionHandler(new ExceptionCaughter());
            t.start();
            inited = true;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        UncaughtExceptionTest ut = new UncaughtExceptionTest();
        ut.init();
        ut.put();
    }

    private class Runnablex implements Runnable {

        @Override
        public void run() {
            String msg = null;
            while (!stop) {
                try {
                    msg = channels.poll(100, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("====="+msg);
                int i = msg.length()-1;
               if(Integer.valueOf(new String(String.valueOf(msg.charAt(i))))==5){
                   throw new NullPointerException("xxxxxxxxxxxx");
               }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ExceptionCaughter implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            LOGGER.info("线程 {} 抛出异常： {} ", t.getName(), e.getMessage(), e);
            synchronized (this) {
                inited = false;
            }
            init();
        }
    }
}



