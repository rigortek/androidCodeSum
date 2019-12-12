package com.cw.java_language;

import java.util.Set;

import static java.lang.Thread.NORM_PRIORITY;

public class JavaMain {

    private static final Object object = new Object();
//    void waitTest() {  // Exception in thread "Thread-0" java.lang.IllegalMonitorStateException
    void waitTest() {
        for (int i = 0; i < 5; ++i) {
            try {
                Thread.sleep(1000);
                System.out.println("sleep " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("send notify start");

        synchronized(this) {
//            notify();  // 只有一个线程会收到通知
            notifyAll();
        }

        System.out.println("send notify end");
    }


    public static void main(String[] args) {
        // 不可以实例化
//        AbstractClass ac = new AbstractClass();
        // 不可以实例化
//        ITestInterface ii = new ITestInterface();
        ImplementClass ic = new ImplementClass();

        SetTest.hashSetTest();

        System.out.println("-------------------------------------------------");

        SetTest.treeSetTest();

        System.out.println("-------------------------------------------------");
        JavaMain javaMain = new JavaMain();

        new Thread(new Runnable() {
            @Override
            public void run() {
                javaMain.waitTest();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (javaMain) {
                    System.out.println("begin wait 1");
                    try {
                        javaMain.wait();
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                    }
                    System.out.println("end wait 1");
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (javaMain) {
                    System.out.println("begin wait 2");
                    try {
                        javaMain.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("end wait 2");
                }
            }
        }).start();


    }
}
