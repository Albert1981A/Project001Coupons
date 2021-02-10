package com.AlbertAbuav.testUtils;

import com.AlbertAbuav.db.ConnectionPool;

public class TestThreads {
    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hi... Im Thread number 1");
                ConnectionPool.getInstance();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hi... Im Thread number 2");
                ConnectionPool.getInstance();
            }
        });

        t1.start();
        t2.start();
    }
}
