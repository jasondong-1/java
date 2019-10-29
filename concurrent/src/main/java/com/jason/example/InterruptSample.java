package com.jason.example;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptSample {
    public static void read() {
        try {
            ReadRunnable readRunnable = new ReadRunnable("pom.xml");
            Thread t = new Thread(readRunnable);
            t.start();
            Thread.sleep(3);
            while (!readRunnable.stop.compareAndSet(false, true)) {
            }
            ;
            t.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loop() {
        Thread t = new Thread(new LoopRunnable());
        t.start();
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
        System.out.println("=================================="+t.isInterrupted());
    }

    public static void main(String[] args) {
        //read();
        loop();
    }

    /**
     * 用于测试度方法等阻塞方法对interrupt不响应
     * 可以注释掉十三行进行测试
     */
    private static class ReadRunnable implements Runnable {
        private BufferedReader reader;
        private AtomicBoolean stop = new AtomicBoolean(false);

        public ReadRunnable(String filename) throws FileNotFoundException, UnsupportedEncodingException {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
        }

        @Override
        public void run() {
            String s;
            try {
                while (!stop.get()) {
                    if ((s = reader.readLine()) == null) {
                        break;
                    }
                    System.out.println(s);
                    /*try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }*/
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static class LoopRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                System.out.println(i);
            }
        }
    }
}