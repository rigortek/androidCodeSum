package com.cw.java_language;

public class SelfDefineClass implements Comparable {
    private int index = 0;

    public SelfDefineClass(int index) {
        this.index = index;
        System.out.println("SelfDefineClass:" + index);
    }

    @Override
    public int compareTo(Object o) {
        SelfDefineClass sdc = (SelfDefineClass) o;
        return index - sdc.index;
    }
}
