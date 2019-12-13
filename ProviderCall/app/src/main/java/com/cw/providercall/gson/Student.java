package com.cw.providercall.gson;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


// Gson使用
public class Student {
    public String name;
    private int age;


    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @NonNull
    @Override
    public String toString() {
        return "name:" + name + ", " + "age:" + age;
    }

    public static void test() {
        String json = "{\"name\":\"Me is poly\", \"age\":1}";
        Student student = new Gson().fromJson(json, Student.class);
        System.out.println("----------------------------------------------------------------------");
        System.out.println(student.toString());

        System.out.println("----------------------------------------------------------------------");

        String jsonArray = "[{\"name\":\"Me is poly\", \"age\":1}, {\"name\":\"Me is leon\", \"age\":2}]";
        List<Student> students = new Gson().fromJson(jsonArray, new TypeToken<List<Student>>(){}.getType());
        System.out.println(students.toString());

        System.out.println("----------------------------------------------------------------------");


        String intJson = "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]";
        int [] iArray = new Gson().fromJson(intJson, int[].class);
        for (int i = 0; i < iArray.length; ++i) {
            System.out.print(iArray[i] + ",");
        }

        System.out.println("\n----------------------------------------------------------------------");
    }
}
