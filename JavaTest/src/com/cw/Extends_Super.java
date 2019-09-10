package com.cw;

public class Extends_Super {

    public void main(String[] args) {

        // convert error
//        Plate<Fruit> p = new Plate<Apple>(new Apple());

        Plate<? extends Food> p = new Plate<Fruit>(new Fruit());
        Plate<? extends Food> p2 = new Plate<Apple>(new Apple());

        Plate<? super Fruit> p3 = new Plate<Fruit>(new Fruit());
        Plate<? super Fruit> p4 = new Plate<Food>(new Food());

        // convert error
//        Plate<? super Fruit> p5 = new Plate<Apple>(new Apple());
    }

    // 食物 - 一级类
    class Food {}

    //水果 - 二级类
    class Fruit extends Food {}

    // 苹果 - 三级类
    class Apple extends Fruit {}

    // 盘子
    // T代表同一种类型
    class Plate<T> {
        private T item;

        public Plate(T t) {
            item = t;
        }

        public void set(T t) {
            item = t;
        }

        public T get() {
            return item;
        }
    }
}
