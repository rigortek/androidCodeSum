package com.cw.java_language;

public class Singleton {
    // 静态内部类 static nested class实现单例模式
    private Singleton() {}

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static final Singleton getInstance() {
        return Singleton.SingletonHolder.INSTANCE;
    }
}
