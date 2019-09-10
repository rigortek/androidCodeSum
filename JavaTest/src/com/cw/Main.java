package com.cw;

import java.util.Random;

// https://www.jetbrains.com/idea/download/#section=linux
// https://blog.csdn.net/qq_35246620/article/details/80522720

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(getRequestId(16));

        String bt_update_package = "UP_bt_update_package.BIn";
        if (bt_update_package.toLowerCase().endsWith(".bin")) {
            System.out.println("match");
        } else {
            System.out.println("not match");
        }

        new Thread_Runable().testTheadRunable();
    }


    public static String getRequestId(int strlen) {
        char[] chr = {'0','a','A'};
        Random random = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        int r = 0;
        for (int i = 0; i < strlen; i++) {
            r = Math.abs(random.nextInt());
            if (0 == (r % 3)) {
                sb.append((char)(chr[r % 3] + Math.abs(random.nextInt()) % 10));
            } else {
                sb.append((char)(chr[r % 3] + Math.abs(random.nextInt()) % 26));
            }
        }
        return sb.toString();
    }
}


