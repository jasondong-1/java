package com.jason.example.singleton;

/**
 * 单线程模式下的单利模式
 */
public class SingleThreadSingleton {
    private static SingleThreadSingleton instance;

    private SingleThreadSingleton() {
    }

    public static SingleThreadSingleton getInstance() {
        if (instance == null) {
            instance = new SingleThreadSingleton();
        }
        return instance;
    }
}
