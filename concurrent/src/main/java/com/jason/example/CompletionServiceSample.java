package com.jason.example;

import java.util.concurrent.*;

public class CompletionServiceSample {
    private static final int N_CPU = Runtime.getRuntime().availableProcessors();
    private ExecutorService executor;
    private ExecutorCompletionService<String> ecs;

    public CompletionServiceSample() {
        this.executor = Executors.newCachedThreadPool();
        this.ecs = new ExecutorCompletionService<String>(executor);
    }

    public void run() throws InterruptedException, ExecutionException {
        for(int i = 0;i<10;i++){
            ecs.submit(new PaiZhao());
        }
        executor.shutdown();
        for(int i = 0;i<10;i++){
            System.out.println(ecs.take().get());
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletionServiceSample css = new CompletionServiceSample();
        css.run();
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
