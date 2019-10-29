package com.jason.core;

import java.util.Map;

public class SimpleTest {
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    // Packing and unpacking ctl
    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    public static void main(String[] args) {
        System.out.println("CAPACITY "+Integer.toBinaryString(CAPACITY));
        System.out.println("RUNNING "+Integer.toBinaryString(RUNNING));
        System.out.println("SHUTDOWN "+Integer.toBinaryString(SHUTDOWN));
        System.out.println("STOP "+Integer.toBinaryString(STOP));
        System.out.println("TIDYING "+Integer.toBinaryString(TIDYING));
        System.out.println("TERMINATED "+Integer.toBinaryString(TERMINATED));
        System.out.println(STOP>RUNNING);
        System.out.println("   "+Integer.toBinaryString(Integer.MAX_VALUE));
        System.out.println("-1 "+ Integer.toBinaryString(-1));
        for (int i = 1; i < 10; i++) {
            System.out.println(workerCountOf(i));
        }
    }
}
