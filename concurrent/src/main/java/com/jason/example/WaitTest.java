package com.jason.example;

public class WaitTest {

    public void func1() throws InterruptedException {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.println("func1");
                waitx();
            }
        }
    }

    public void waitx() throws InterruptedException {
        wait(1000);
    }

    public void func2() throws InterruptedException {
        synchronized (this){
            for (int i = 0; i < 10; i++) {
                System.out.println("func2");
                wait(1000);
            }
        }

    }


    public static void main(String[] args) {
        final WaitTest wt = new WaitTest();
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    wt.func1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    wt.func2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        t2.start();
    }
}


