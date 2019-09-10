package com.cw;


public class Thread_Runable {

    public void testTheadRunable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.print("Thead -> Runnable");
            }
        }).start();
    }
}
