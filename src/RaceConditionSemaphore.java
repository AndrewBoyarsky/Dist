/*
 * Copyright © 2018 Apollo Foundation
 */

import java.util.concurrent.Semaphore;

public class RaceConditionSemaphore {
    private static Semaphore semaphore = new Semaphore(1);
    private static int i = 1000;
    final static int iterations = 100_000;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int m = iterations;
            while (m-- > 0) {
                try {
                    increment();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1=" + i);
            }
        });
        Thread t2 = new Thread(() -> {
            int m = iterations;
            while (m-- > 0) {
                try {
                    decrement();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2=" + i);
            }
        });
        t2.start();
        t1.start();
        System.out.println(i);
    }

    public static void increment() throws InterruptedException {
        semaphore.acquire();
        try {
            i++;
        } finally {
            semaphore.release();
        }
    }
    public static void decrement() throws InterruptedException {
        semaphore.acquire();
        try {
            i--;
        } finally {
            semaphore.release();
        }
    }
}
