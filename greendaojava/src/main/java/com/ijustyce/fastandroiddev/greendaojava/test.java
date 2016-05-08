package com.ijustyce.fastandroiddev.greendaojava;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yangchun on 16/4/19.  测试
 */
public class test {

    public static void main(String[] args){

        System.out.println("date is " + timesTampToDate("1461903360", "yyyy/MM/dd HH:mm:ss"));
    }

    /**
     * convert Unix timestamp to a certain date
     *
     * @param timesTamp Unix timestamp
     * @return date , like 2014-04-27 11:42:00
     */
    public static String timesTampToDate(String timesTamp, String format) {

        if (format == null || timesTamp ==null
                || (timesTamp.length() != 10 && timesTamp.length() != 13)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        long lcc_time = Long.parseLong(timesTamp);
        if (lcc_time == 0) return null;
        if (timesTamp.length() == 10) lcc_time *= 1000;
        return sdf.format(new Date(lcc_time));
    }
}
