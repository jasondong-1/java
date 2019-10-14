package com.jason.example;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) {
        SemaChannel<String> semaChannel = new SemaChannel<>(3, false);
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new PutRunnable<String>(semaChannel, new String("jason")));
            thread.start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(semaChannel.take());
        }
    }
}


class SemaChannel<E> {
    private LinkedBlockingQueue<E> queue = new LinkedBlockingQueue<>();
    private Semaphore semaphore;

    public SemaChannel(int permits, boolean fair) {
        semaphore = new Semaphore(permits, fair);
    }

    public void put(E e) {
        try {
            semaphore.acquire();
            System.out.println("开始put");
            queue.put(e);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public E take() {
        E ee = null;
        try {
            ee = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ee;
    }
}

class PutRunnable<E> implements Runnable {

    private E e;
    private SemaChannel<E> semaChannel;

    public PutRunnable(SemaChannel<E> semaChannel, E e) {
        this.semaChannel = semaChannel;
        this.e = e;
    }

    @Override
    public void run() {
        semaChannel.put(e);
    }
}