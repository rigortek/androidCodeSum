package com.cw;

import java.util.ArrayList;

public class Extends_Super {

    public void main() {

        // convert error
//        Plate<Fruit> p = new Plate<Apple>(new Apple());

        Plate<? extends Food> p = new Plate<Fruit>(new Fruit());
        Plate<? extends Food> p2 = new Plate<Apple>(new Apple());

        Plate<? super Fruit> p3 = new Plate<Fruit>(new Fruit());
        Plate<? super Fruit> p4 = new Plate<Food>(new Food());

        // convert error
//        Plate<? super Fruit> p5 = new Plate<Apple>(new Apple());


        ArrayList<? super Food> foods = new ArrayList<Food>();
        foods.add(new Food());
        foods.add(new Fruit());
        foods.add(new Apple());

        Food food = (Food) foods.get(0);
        System.out.print(food.nameFood);

        Fruit fruit = (Fruit) foods.get(1);
        System.out.print(fruit.nameFood);
        System.out.print(fruit.nameFruit);

        Apple apple = (Apple) foods.get(2);
        System.out.print(apple.nameFood);
        System.out.print(apple.nameFruit);
        System.out.print(apple.nameApple);
    }

    // 食物 - 一级类
    class Food {
        public String nameFood = "Food\n";
    }

    //水果 - 二级类
    class Fruit extends Food {
        public String nameFruit = "Fruit\n";
    }

    // 苹果 - 三级类
    class Apple extends Fruit {
        public String nameApple = "Apple\n";
    }

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
