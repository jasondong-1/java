package com.jason.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowAble2String {
    public static void main(String[] args) {
        try {
            PrintWriter p = new PrintWriter("/aa/b");
        } catch (FileNotFoundException e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            System.out.println(stringWriter.toString());
        }


    }
}
