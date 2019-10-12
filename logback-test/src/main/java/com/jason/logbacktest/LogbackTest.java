package com.jason.logbacktest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class LogbackTest {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    public void test1() {
        logger.debug("logback:{}", "debug");
        logger.info("logback:{}", "info");
        logger.warn("logback:{}", "warn");
        logger.error("logback:{}", "error");
    }

    public static void main(String[] args) {
        LogbackTest test = new LogbackTest();
        test.test1();
    }
}
