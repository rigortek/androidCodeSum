package com.cw.classloader;

public class SonClass extends FatherClass
{
    static
    {
        System.out.println("SonClass init");
    }

    static int number;

    public SonClass()
    {
        System.out.println("init SonClass");
    }
}