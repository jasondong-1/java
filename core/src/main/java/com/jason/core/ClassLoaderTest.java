package com.jason.core;

public class ClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println();
    }
}
