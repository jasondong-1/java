package com.jason.example.singleton;

/**
 * 这里演示的是一个线程安全的单例模式
 */
public class MultithreadingSingleton {
    private static MultithreadingSingleton instance = null;

    private MultithreadingSingleton() {

    }

    public static MultithreadingSingleton getInstance() {
        synchronized (MultithreadingSingleton.class) {
            if (instance == null) {
                instance = new MultithreadingSingleton();
            }
            return instance;
        }
    }

}

/**
 * 不安全
 */
class MultithreadingSingletonV2 {
    private static MultithreadingSingletonV2 instance = null;

    private MultithreadingSingletonV2() {

    }

    public static MultithreadingSingletonV2 getInstance() {
        if (instance == null) {
            synchronized (MultithreadingSingleton.class) {
                if (instance == null) {
                    instance = new MultithreadingSingletonV2();
                }
            }
        }
        return instance;

    }

}