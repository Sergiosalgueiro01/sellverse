package com.main.es.sellverse.util.date;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateConvertionUtil {

    public static Date addTime(int days,int hours,int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,1900);
        calendar.add(Calendar.DAY_OF_MONTH,days);
        calendar.add(Calendar.HOUR,hours);
        calendar.add(Calendar.MINUTE,minutes);


        return calendar.getTime();
    }

    public static String convert(Date localDateTime) {
        localDateTime.setMonth(localDateTime.getMonth()+1);
        return localDateTime.getYear()+
                "-"+parseNumber(localDateTime.getMonth())+
                "-"+parseNumber(localDateTime.getDate())+
                " "+parseNumber(localDateTime.getHours())+
                ":"+parseNumber(localDateTime.getMinutes())+
                ":"+parseNumber(localDateTime.getSeconds());
    }


    private static String parseNumber(Integer num){
        if(num.toString().length() > 1)
            return num+"";
        else
            return "0"+num;
    }
    public static Date getDate(int year,int month, int day,int hour,int minutes,int seconds){
        Date date = new Date();
        date.setYear(year);
        date.setMonth(month);
        date.setDate(day);
        date.setHours(hour);
        date.setMinutes(minutes);
        date.setSeconds(seconds);
        return date;
    }
    public static Date unconvert(String s) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormater.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

}
