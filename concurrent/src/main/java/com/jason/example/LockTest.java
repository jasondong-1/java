package com.jason.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    public static void main(String[] args) {
        Foo2 foo2 = new Foo2();
        for (int i = 0; i <= 10; i++) {
            Thread thread = new Thread(foo2, "thread-" + i);
            thread.start();
        }
    }
}

class Foo2 implements Runnable {
    Lock lock = new ReentrantLock(false);
    private int count;

    @Override
    public void run() {
        add();
        try {
            add2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void add() {
        lock.lock();
        try {
            int a = count;
            Thread.sleep(200);
            count = a + 1;
            System.out.println(Thread.currentThread().getName() + " add " + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


    //如果其他线程因为异常一直持有锁未释放，该方法可以保证线程可以执行其他任务
    private void add2() throws InterruptedException {
        if (lock.tryLock(1, TimeUnit.SECONDS)) {
            try {
                int a = count;
                Thread.sleep(200);
                count = a + 1;
                System.out.println(Thread.currentThread().getName() + " add2 " + count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("do something else");
        }


    }
}
