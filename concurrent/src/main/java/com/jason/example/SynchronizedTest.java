package com.jason.example;

public class SynchronizedTest {
    public static void main(String[] args) {
        Foo foo = new Foo();
        for (int i = 0; i <= 10; i++) {
            Thread thread = new Thread(foo, "thread-" + i);
            thread.start();
        }
    }
}


class Foo implements Runnable {
    private int count;

    @Override
    public void run() {
        add();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        add2();
        add3();
    }

    //内部锁使用方法1，加载方法上
    private synchronized void add() {
        int a = count;
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count = a + 1;
        System.out.println(Thread.currentThread().getName() + " add " + count);
    }

    //内部锁使用方法2，加在方法块上
    private void add2() {
        synchronized (this) {
            int a = count;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count = a + 1;
            System.out.println(Thread.currentThread().getName() + " add2 " + count);
        }
    }

    //该方法用于测试 内部锁可以防止锁泄露的发生
    private void add3() {
        synchronized (this) {
            int a = count;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count = a + 1;
            System.out.println(Thread.currentThread().getName() + " add3 " + count);
            throw new IndexOutOfBoundsException("");
        }
    }

}