package com.jason.core;

import org.joda.time.DateTime;

import java.sql.Date;
import java.sql.Timestamp;

public class JodaTimeTest {


    public static void main(String[] args) {
        Date date = new Date(System.currentTimeMillis());
        System.out.println("java.sql.date " + date.toString());
        DateTime dateTime = new DateTime(date);
        System.out.println(dateTime.toString("yyyy-MM-dd"));

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        dateTime = new DateTime(ts.getTime());
        System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
