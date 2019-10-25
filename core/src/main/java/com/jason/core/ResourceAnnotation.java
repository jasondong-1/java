package com.jason.core;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

public class ResourceAnnotation {
    @Resource
    private Person p;

    @PostConstruct
    public void init(){
        p = new Person();
    }

    @PreDestroy
    public void destroy(){
        System.out.println("hello");
    }
    public Person getP() {
        return p;
    }

    public void setP(Person p) {
        this.p = p;
    }

    public static void main(String[] args) {
        ResourceAnnotation resourceAnnotation = new ResourceAnnotation();
        System.out.println(resourceAnnotation.p);
    }
}

class Person{
    private String name = "jason";
    private int age = 10;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

