package com.team404.bookstore.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Implementation of Facade Pattern
 * */
public class TimeGenerator implements TimeGeneratorInterface{

    public String GetOrderGenerationTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (String)df.format(new Date());
    }

    public Timestamp GetTimestampValue() {
        String s = GetOrderGenerationTime();
        return Timestamp.valueOf(s);
    }

}
