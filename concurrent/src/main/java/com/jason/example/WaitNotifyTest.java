package com.jason.example;

public class WaitNotifyTest {
    private boolean boo;

    public WaitNotifyTest() {
    }

    public boolean isBoo() {
        return boo;
    }

    public void setBoo(boolean boo) {
        synchronized (this) {
            this.boo = boo;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //this.notify();
            this.notifyAll();
        }
    }

    public void print() {
        synchronized (this) {
            while (!isBoo()) {
                try {
                    System.out.println(Thread.currentThread().getName()+ " 条件不满足，歇息一会儿");
                    //this.wait();
                    this.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + getClass().getSimpleName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WaitNotifyTest waitNotifyTest = new WaitNotifyTest();
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new WaitRunable(waitNotifyTest), WaitNotifyTest.class.getSimpleName() + i);
            thread.start();
        }
        Thread.sleep(4000);
        Thread thread = new Thread(new NotifyRunnable(waitNotifyTest, true));
        thread.start();
    }

}


class WaitRunable implements Runnable {
    private WaitNotifyTest waitNotifyTest;

    public WaitRunable(WaitNotifyTest waitNotifyTest) {
        this.waitNotifyTest = waitNotifyTest;
    }

    @Override
    public void run() {
        waitNotifyTest.print();
    }
}

class NotifyRunnable implements Runnable {
    private WaitNotifyTest waitNotifyTest;
    private boolean flag;

    public NotifyRunnable(WaitNotifyTest waitNotifyTest, boolean flag) {
        this.waitNotifyTest = waitNotifyTest;
        this.flag = flag;
    }

    @Override
    public void run() {
        waitNotifyTest.setBoo(flag);
    }
}