package com.jason.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Log4jTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 测试log4j.properties文件设置（格式，文件存储）
     */
    public void test() {
        logger.info("pumas are large cat like animals {}", "info");
        logger.debug("pumas are large cat like animals {} ", "debug");
    }

    /**
     * 测试获取properties文件中的变量，properties默认是不支持的，
     * 这里用到的是log4j中的类
     * @throws IOException
     */
    public void test1() throws IOException {
        Properties properties = new Properties();
        properties.load(Log4jTest.class.getClassLoader().getResourceAsStream("log4j.properties"));
        String value = properties.getProperty("log4j.appender.thisProject.file.out.File");
        System.out.println(org.apache.log4j.helpers.OptionConverter.substVars(value, properties));
    }

    /**
     * 查看默认的环境变量
     */

    public void test2() {
        Properties properties = System.getProperties();
        for (String s : properties.stringPropertyNames()) {
            System.out.println(s + " : " + properties.getProperty(s));
        }
    }

    public static void main(String[] args) throws IOException {
        new Log4jTest().test2();
    }
}
