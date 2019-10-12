package com.jason.example.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ShootingPractice {
    private final ReadWriteLock rwlock = new ReentrantReadWriteLock(true);
    private final Lock read = rwlock.readLock();
    private final Lock write = rwlock.writeLock();
    //靶子的数量
    private final int n;
    //有几排士兵
    private final int line;
    //一组士兵
    private final Soldier[][] group;
    //用于确保所有战士同时射击
    private final CyclicBarrier start;
    //用于确保所有战士同事撤离
    private final CyclicBarrier modify;
    private volatile boolean canStop;
    private volatile boolean stop;

    private volatile int shootline;
    private final int practiceTime;

    public ShootingPractice(final int n, int line, int practiceTime) {
        this.n = n;
        this.line = line;
        this.practiceTime = practiceTime;
        this.group = new Soldier[line][n];
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < n; j++) {
                group[i][j] = new Soldier(i + "-" + j);
            }
        }
        this.start = new CyclicBarrier(n);
        this.modify = new CyclicBarrier(n, new Runnable() {
            @Override
            public void run() {
                if (!isCanStop()) {
                    shootline = (shootline + 1) % n;
                    System.out.println(shootline + "排准备射击！");
                } else {
                    stop = true;
                }

            }
        });
    }

    public void start() throws InterruptedException {
        Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            Thread thread = new Thread(new Shooting(i));
            threads[i] = thread;
            thread.start();
        }
        Thread.sleep(practiceTime * 1000);
        System.out.println("即将停止射击");
        setCanStop(true);
        //stop=true;
        for (Thread t : threads) {
            t.join();
        }
        System.out.println("训练结束");
    }

    class Shooting implements Runnable {
        private final int index;

        public Shooting(int index) {
            this.index = index;
        }

        @Override
        public void run() {

            while (!stop) {
                try {
                    Soldier soldier = group[shootline][index];
                    System.out.print(soldier.getNo() + "准备上场，  ");
                    System.out.println(soldier.getNo() + " 就绪！");
                    start.await();
                    soldier.fire();
                    modify.await();
                    if (index > 2) {
                        Thread.sleep(3000l);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isCanStop() {
        read.lock();
        try {
            return canStop;
        } finally {
            read.unlock();
        }

    }

    public void setCanStop(boolean canStop) {
        write.lock();
        try {
            this.canStop = canStop;
        } finally {
            write.unlock();
        }

    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public static void main(String[] args) throws InterruptedException {
        ShootingPractice practice = new ShootingPractice(5, 6, 5);
        practice.start();
    }
}
