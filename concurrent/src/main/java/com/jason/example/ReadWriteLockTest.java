package com.jason.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    public static void main(String[] args) {
        Foo3 foo3 = new Foo3();
        for (int i = 0; i < 11; i++) {
            Thread thread = null;
            if (i == 7) {
                thread = new Thread(new Runnabler(foo3, "w"));
            } else {
                thread = new Thread(new Runnabler(foo3, "r"));
            }
            thread.start();
        }
    }
}

class Foo3 {
    private int count;
    private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
    private final Lock read = rwlock.readLock();
    private final Lock write = rwlock.writeLock();

    public int getCount() throws InterruptedException {
        read.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 获得读锁：" + count);
            Thread.sleep(1000);
            return count;
        } finally {
            read.unlock();
        }
    }

    public void add() {
        write.lock();
        try {
            this.count += 1;
            System.out.println(Thread.currentThread().getName() + " 获得写锁：" + count);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            write.unlock();
        }

    }
}

class Runnabler implements Runnable {
    private Foo3 foo3;
    private String s;

    public Runnabler(Foo3 foo3, String s) {
        this.foo3 = foo3;
        this.s = s;
    }

    @Override
    public void run() {
        if ("r".equals(s)) {
            try {
                foo3.getCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            foo3.add();
        }

    }
}