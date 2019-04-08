/*
 * Copyright © 2018 Apollo Foundation
 */

import java.util.concurrent.atomic.AtomicInteger;

public class RaceConditionAtomic {
    private static AtomicInteger i = new AtomicInteger(1000);
    final static int iterations = 100_000;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int m = iterations;
            while (m-- > 0) {
                i.incrementAndGet();
                System.out.println("t1=" + i);
            }
        });
        Thread t2 = new Thread(() -> {
            int m = iterations;
            while (m-- > 0) {
                i.decrementAndGet();
                System.out.println("t2=" + i);
            }
        });
        t2.start();
        t1.start();
        System.out.println(i);
    }
}
