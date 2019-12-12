package com.cw.classloader;

public class FatherClass extends GrandpaClass
{
    static
    {
        System.out.println("FatherClass init!");
    }

    public static int age = 123;

    public FatherClass()
    {
        System.out.println("init FatherClass");
    }
}