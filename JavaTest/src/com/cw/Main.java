package com.cw;

import com.cw.classloader.SonClass;

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

//        new Thread_Runable().testTheadRunable();
//        new Extends_Super().main();

        System.out.println("-----------------------------------------------------------");
        // 对于静态字段，只有直接定义这个字段的类才会被初始化
        System.out.print(SonClass.age);

        System.out.println("-----------------------------------------------------------");
        System.out.println(new SonClass());

        System.out.println("-----------------------------------------------------------");
        System.out.println(new SonClass[10]);   // 创建对象数组，并没有创建对象

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


