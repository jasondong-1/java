package com.jason.example;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class JJThreadFactory implements ThreadFactory {
    private final String prefix;
    private AtomicInteger index = new AtomicInteger(0);
    private final ThreadGroup group;

    public JJThreadFactory(String prefix) {
        this.prefix = prefix;
        group = Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        String threadName = prefix + "-" + index.getAndIncrement();
        Thread thread = new Thread(group, r, threadName);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }

        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }

        return thread;
    }

    public static void main(String[] args) {
        JJThreadFactory jf = new JJThreadFactory("jason");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Thread t = Thread.currentThread();
                System.out.println(t.getName());
            }
        };

        for (int i = 0; i < 5; i++) {
            Thread t = jf.newThread(runnable);
            t.start();
        }
    }
}
