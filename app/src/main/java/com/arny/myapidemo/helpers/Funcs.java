package com.arny.myapidemo.helpers;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Funcs {

    public static String getTimeMIN_SEC_MS(long ms) {
        return (new SimpleDateFormat("mm:ss:SSS", Locale.getDefault())).format(new Date(ms));
    }

    public static String getTimeMIN_SEC(long ms) {
        return (new SimpleDateFormat("mm:ss", Locale.getDefault())).format(new Date(ms));
    }

    public static String getTimeMIN_SEC(int sec) {
        return (new SimpleDateFormat("mm:ss", Locale.getDefault())).format(new Date(sec * 1000));
    }

    public static String getStringDateTime(int year, int monthOfYear, int dayOfMonth) {
        String strDateFormat = "MMM";
        String strMonth = new DateFormatSymbols().getMonths()[monthOfYear];
        Date date = null;
        try {
            date = new SimpleDateFormat(strDateFormat, Locale.getDefault()).parse(strMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formDate = new SimpleDateFormat("MMM", Locale.getDefault()).format(date);
        return dayOfMonth + " " + formDate + " " + year;
    }

    public static String strLogTime(int logtime) {
        int h = logtime / 60;
        int m = logtime % 60;
        return pad(h) + ":" + pad(m);
    }

    public static String pad(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return "0" + String.valueOf(c);
        }
    }

    public static long randLong(long min, long max) {
        Random rnd = new Random();
        if (min > max) {
            throw new IllegalArgumentException("min>max");
        }
        if (min == max) {
            return min;
        }
        long n = rnd.nextLong();
        //abs (use instead of Math.abs, which might return min value) :
        n = n == Long.MIN_VALUE ? 0 : n < 0 ? -n : n;
        //limit to range:
        n = n % (max - min);
        return min + n;
    }

    public static int randInt(int min, int max) {
        Random rnd = new Random();
        int range = max - min + 1;
        return rnd.nextInt(range) + min;
    }

    public static int getMonth(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static String getStrMonth(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);
        String strDateFormat = "MMM";
        String strM = new DateFormatSymbols().getMonths()[m];
        Date dat = null;
        try {
            dat = new SimpleDateFormat(strDateFormat, Locale.getDefault()).parse(strM);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String mMonth = new SimpleDateFormat("MMM", Locale.getDefault()).format(dat);
        return mMonth + " " + y;
    }

    public static String getStrDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);
        String strDateFormat = "MMM";
        String strM = new DateFormatSymbols().getMonths()[m];
        Date dat = null;
        try {
            dat = new SimpleDateFormat(strDateFormat, Locale.getDefault()).parse(strM);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String fDate = new SimpleDateFormat("MMM", Locale.getDefault()).format(dat);
        return d + " " + fDate + " " + y;
    }

    public static String getStrTime(long timestamp) {
        Date d = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("kk:mm", Locale.getDefault());
        return format.format(d);
    }

    public static String getCurrentDateTime() {
        Date d = new Date(Calendar.getInstance().getTimeInMillis());
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy kk:mm", Locale.getDefault());
        return format.format(d);
    }

    public static String getCurrentDateTimeSec() {
        Date d = new Date(Calendar.getInstance().getTimeInMillis());
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy kk:mm:ss", Locale.getDefault());
        return format.format(d);
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);
        String strDateFormat = "MMM";
        String strM = new DateFormatSymbols().getMonths()[m];
        Date dat = null;
        try {
            dat = new SimpleDateFormat(strDateFormat, Locale.getDefault()).parse(strM);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String fDate = new SimpleDateFormat("MMM", Locale.getDefault()).format(dat);
        return d + " " + fDate + " " + y;
    }

    public static long convertTimeStringToLong(String myTimestamp) {
        Calendar mCalendar = Calendar.getInstance();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(myTimestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mCalendar.setTime(dateObj);
        return mCalendar.getTimeInMillis();
    }
}
