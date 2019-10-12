package com.jason.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {
    private volatile boolean boo;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Condition con2 = lock.newCondition();

    public ConditionTest() {
    }

    public boolean isBoo() {
        return boo;
    }

    public void setBoo(boolean boo) {
        try {
            lock.lock();
            this.boo = boo;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void print() {
        try {
            lock.lock();
            while (!isBoo()) {
                System.out.println(Thread.currentThread().getName() + " 条件不满足，歇息一会儿");
                //condition.await();
                condition.await(1000, TimeUnit.MILLISECONDS);
            }
            System.out.println(Thread.currentThread().getName() + " : " + getClass().getSimpleName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
      final ConditionTest con = new ConditionTest();
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new AwaitRnnnable(con));
            thread.start();
        }

        Thread.sleep(4000);
        Thread thread = new Thread(new SignalRunnable(con, true));
        thread.start();
    }

}

class AwaitRnnnable implements Runnable {
    private ConditionTest conditionTest;

    public AwaitRnnnable(ConditionTest conditionTest) {
        this.conditionTest = conditionTest;
    }

    @Override
    public void run() {
        conditionTest.print();
    }
}

class SignalRunnable implements Runnable {
    private ConditionTest conditionTest;
    private boolean boo;

    public SignalRunnable(ConditionTest conditionTest, boolean boo) {
        this.conditionTest = conditionTest;
        this.boo = boo;
    }

    @Override
    public void run() {
        conditionTest.setBoo(boo);
    }
}