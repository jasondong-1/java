package com.jason.example.cyclicbarrier;

public class Soldier {
    private final String no;

    public Soldier(String no) {
        this.no = no;
    }

    public String getNo() {
        return no;
    }

    public void fire() {
        System.out.println(Thread.currentThread().getName()+"-"+no+" fire!");
    }
}
