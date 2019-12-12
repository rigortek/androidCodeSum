package com.cw.java_language;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class SetTest {
    public static void hashSetTest() {
        HashSet hashSet = new HashSet();

        hashSet.add(8);
        hashSet.add(9);
        hashSet.add(10);

        hashSet.add(5);
        hashSet.add(6);
        hashSet.add(7);

        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(3);
        hashSet.add(4);


//        hashSet.add(new SelfDefineClass(1));
//        hashSet.add(new SelfDefineClass(2));
//        hashSet.add(new SelfDefineClass(3));
//        hashSet.add(new SelfDefineClass(4));
//        hashSet.add(new SelfDefineClass(5));

        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            System.out.println("HashSet: " + iterator.next());
        }

    }

    public static void treeSetTest() {
        TreeSet treeSet = new TreeSet();

        // TreeSet 会自动按自然排序法给元素排序，HashSet 不会
        //如果不需要使用排序功能,应该使用 HashSet,因为其性能优于 TreeSet
        treeSet.add(8);
        treeSet.add(9);
        treeSet.add(10);

        treeSet.add(5);
        treeSet.add(6);
        treeSet.add(7);

        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(3);
        treeSet.add(4);

        // 如果 TreeSet 传入的是自定义的对象,必须让该对象实现 Comparable 接口
//        treeSet.add(new SelfDefineClass(3));
//        treeSet.add(new SelfDefineClass(4));
//        treeSet.add(new SelfDefineClass(5));
//
//        treeSet.add(new SelfDefineClass(1));
//        treeSet.add(new SelfDefineClass(2));

        Iterator iterator = treeSet.iterator();
        while (iterator.hasNext()) {
            System.out.println("TreeSet: " + iterator.next());
        }
    }
}
