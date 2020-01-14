package com.jason.classpath;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

public class ClasspathTest {

    public ClasspathTest() throws URISyntaxException {

    }

    public void test1() throws IOException {
        URL url = getClass().getClassLoader().getResource("");
        System.out.println(url.getFile());
        File file = new File(url.getFile());
        for(File f:file.listFiles()){
            System.out.println(f.getAbsolutePath());
        }
    }

    public void test3(){
        System.out.println(getClass().getResource("/").getPath());
    }

    public void test2() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("application.yml")));
        String line = null;
        while ((line=reader.readLine())!=null) {
            System.out.println(line);
        }
        reader.close();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        ClasspathTest ct =  new ClasspathTest();
        ct.test3();
        ct.test1();
        ct.test2();
    }
}
