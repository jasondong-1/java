package com.jason.classpath;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ClasspathTest {

    public ClasspathTest() throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource("");
        System.out.println(url.getFile());
        File file = new File(url.getFile());
        for(File f:file.listFiles()){
            System.out.println(f.getAbsolutePath());
        }
    }

    public static void main(String[] args) throws URISyntaxException {
        new ClasspathTest();
    }
}
