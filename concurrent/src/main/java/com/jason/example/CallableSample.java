package com.jason.example;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CallableSample {
    private static final int N_CPU = Runtime.getRuntime().availableProcessors();
    private ThreadPoolExecutor executors = new ThreadPoolExecutor(2, 2 * N_CPU, 5, SECONDS, new ArrayBlockingQueue<Runnable>(5), new ThreadPoolExecutor.CallerRunsPolicy());

    public void run(int n) throws InterruptedException, ExecutionException, TimeoutException {
        Future<String>[] futures = new Future[n];
        for (int i = 0; i < n; i++) {
            futures[i] = executors.submit(new PaiZhao());
        }
        Thread.sleep(3000);

        for (Future<String> f : futures) {
            System.out.println(f.get(3000l, TimeUnit.MILLISECONDS));
        }
        executors.shutdown();
        System.out.println("terminate " + executors.isTerminated());
        System.out.println("shutdown " + executors.isShutdown());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CallableSample sample = new CallableSample();
        sample.run(20);
    }

    private class PaiZhao implements Callable<String> {
        private String[] arrs = new String[]{
                "浙F455226",
                "浙F4adsflk",
                "浙Fsdfasdf",
                "浙Fasdfasdf",
                "浙Fasdfasdf",
                "浙F45464966"
        };

        @Override
        public String call() throws Exception {
            //模拟计算耗时
            Thread.sleep(3000);
            return arrs[(int) (Math.random() * arrs.length)];
        }
    }
}

