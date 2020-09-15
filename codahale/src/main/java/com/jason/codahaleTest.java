package com.jason;

import com.codahale.metrics.*;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class codahaleTest {
    public static Queue<String> queue = new LinkedList<>();//队列

    public static void main(String[] args) throws InterruptedException {
        MetricRegistry registry = new MetricRegistry();
        ScheduledReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();


        reporter = Slf4jReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        //reporter.start(1, TimeUnit.SECONDS);
        registry.register(MetricRegistry.name(codahaleTest.class, "queue", "size"), new Gauge<Integer>() {
            public Integer getValue() {
                return queue.size();
            }
        });

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                queue.add("job - " + LocalDateTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        reporter.report();
        System.out.println("============");
        Thread.sleep(10000);
        /*Iterator<Gauge> it = registry.getGauges().values().iterator();
        it.hasNext();
        System.out.println(it.next().getValue());*/
    }

}
