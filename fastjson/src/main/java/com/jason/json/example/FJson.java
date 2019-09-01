package com.jason.json.example;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class FJson {
    public static void main(String[] args) {
        Person p = new Person(2,"aa");
        Person p2 = new Person(3,"bb");
        String s = JSON.toJSONString(p);
        String s2 = JSON.toJSONString(p2);
        System.out.println(s);
        System.out.println(s2);
        System.out.println(JSON.parseObject(s,Person.class));
        System.out.println(JSON.parseObject(s2,Person.class));
    }
}


class Person implements Serializable {
    private static final long serialVersionUID = 3744321643479117230L;
    private int age;
    private String name;

    public Person() {
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}