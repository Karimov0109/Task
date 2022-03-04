package com.example.task.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy KK:mm");

    public static String timeParse(Long time){
        return simpleDateFormat.format(new Date(time * 1000L));
    }

    public static Long dateParse(String time) throws ParseException {
        return simpleDateFormat.parse(time).getTime()/1000;
    }
}
