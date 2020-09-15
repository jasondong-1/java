package com.jason.example;

public class SuperSubClock extends SuperFoo{
    public static void main(String[] args) {
        SuperSubClock ssc = new SuperSubClock();
        ssc.getStop();
    }
}

abstract class SuperFoo{
    private Boolean stop;


    public Boolean getStop() {
        synchronized (this){
            System.out.println(this.getClass().getName());
            return stop;
        }

    }

    public void setStop(Boolean stop) {
        synchronized (this){
            this.stop = stop;
        }

    }
}
