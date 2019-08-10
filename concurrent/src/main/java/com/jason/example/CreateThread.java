package com.jason.example;

public class CreateThread {
    public static void main(String[] args) {
        Thread threada = new ThreadA();
        threada.start();
        Thread threadb = new Thread(new Taskx());
        threadb.start();
    }
}

class ThreadA extends Thread {

    @Override
    public void run() {
        System.out.println(getClass().getName() + " :" + Thread.currentThread().getName());
    }
}

class Taskx implements Runnable {

    @Override
    public void run() {
        System.out.println(getClass().getName() + " :" + Thread.currentThread().getName());
    }
}