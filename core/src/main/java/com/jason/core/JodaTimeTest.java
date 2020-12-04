package com.jason.core;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * spark利用SimpleDateFormat处理时间时存在线程安全问题，
 * 因此采用jodatime
 */
public class JodaTimeTest {
    public static void main(String[] args) {
        Date date = new Date();
        DateTime dt = new DateTime(date);
    }
}
