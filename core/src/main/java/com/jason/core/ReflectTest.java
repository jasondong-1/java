package com.jason.core;

import java.lang.reflect.Field;

public class ReflectTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //实例化对象
        RFoo rFoo = new RFoo();
        System.out.println(rFoo);
        //利用反射获取属性
        Field field = rFoo.getClass().getDeclaredField("age");
        //因为属性是private，不可直接访问，设置可访问为true 即可访问
        field.setAccessible(true);
        //更改属性的值
        field.set(rFoo, 1000);
        System.out.println(rFoo);

        //获取属性的值
        int age = (int)field.get(rFoo);
        System.out.println(age);

    }
}

class RFoo{
    private int age = 100;

    @Override
    public String toString() {
        return "RFoo{" +
                "age=" + age +
                '}';
    }
}