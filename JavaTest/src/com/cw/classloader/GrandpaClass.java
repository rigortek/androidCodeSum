package com.cw.classloader;

public class GrandpaClass
{
    static
    {
        System.out.println("GrandpaClass init");
    }

    public GrandpaClass()
    {
        System.out.println("init GrandpaClass");
    }
}
