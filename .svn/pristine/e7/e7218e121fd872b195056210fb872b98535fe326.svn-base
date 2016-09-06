package com.minsheng.app.xunchedai.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期管理类
 * **/
public class DateUtils {
    public static String timeFormat(long timeMillis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }


    public static String formatPhotoDate(long time) {
        return timeFormat(time, "yyyy-MM-dd");
    }

    public static boolean DateCompare(String s1,String s2){
        boolean isFlag=false;
        //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //得到指定模范的时间
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdf.parse(s1);
            d2 = sdf.parse(s2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //比较
        if(Math.abs(((d1.getTime() - d2.getTime())/(24*3600*1000)))>7) {
            isFlag=true;
        }else{
            isFlag=false;
        }
        return isFlag;
    }

}
