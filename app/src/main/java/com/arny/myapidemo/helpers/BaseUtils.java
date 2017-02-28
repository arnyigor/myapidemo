package com.arny.myapidemo.helpers;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseUtils {

    private static final String APP_PREFERENCES = "app_preferences";
    public static final String TIME_SEPARATOR_TWICE_DOT = ":";
    public static final String TIME_SEPARATOR_DOT = ".";

    public static boolean matcher(String preg, String string) {
        return Pattern.matches(preg, string);
    }


    public static String match(String where, String pattern, int groupnum) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(where);
        while (m.find()) {
            if (!m.group(groupnum).equals("")) {
                return m.group(groupnum);
            }
        }
        return null;
    }

    public static String dateFormatChooser(String myTimestamp) {
        HashMap<String, String> pregs = new HashMap<>();
        pregs.put("^[0-9]{1,2}\\.[0-9]{2}\\.[0-9]{4}$", "dd.MM.yyyy");
        pregs.put("^[0-9]{1,2}\\.[0-9]{2}\\.[0-9]{2}$", "dd.MM.yy");
        pregs.put("^[0-9]{1,2}\\-\\.*\\-[0-9]{2}$", "dd-MMM-yy");
        pregs.put("^[0-9]{1,2}\\-.*\\-[0-9]{4}$", "dd-MMM-yyyy");
        pregs.put("^[0-9]{1,2}\\s\\.*\\s[0-9]{2}$", "dd MMM yy");
        pregs.put("^[0-9]{1,2}\\s\\.*\\s[0-9]{4}$", "dd MMM yyyy");
        pregs.put("^[0-9]{1,2}\\s[0-9]{2}\\s[0-9]{2}$", "dd MM yy");
        pregs.put("^[0-9]{1,2}\\s[0-9]{2}\\s[0-9]{4}$", "dd MM yyyy");
        String format = "dd MMM yyyy";
        for (HashMap.Entry<String, String> entry : pregs.entrySet()) {
            if (Pattern.matches(entry.getKey(), myTimestamp)) {
                format = entry.getValue();
                break;
            }
        }
        return format;
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * if milliseconds==0 returned current datetime,
     * if format==null default "dd MMM yyyy HH:mm:ss.sss"
     *
     * @param milliseconds
     * @param format
     * @return String datetime
     */
    public static String getDateTime(long milliseconds, String format) {
        if (milliseconds == -1) {
            return "";
        }
        try {
            milliseconds = (milliseconds == 0) ? Calendar.getInstance().getTimeInMillis() : milliseconds;
            format = (format == null) ? "dd MMM yyyy HH:mm:ss.sss" : format;
            return (new SimpleDateFormat(format, Locale.getDefault())).format(new Date(milliseconds));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param date
     * @param format
     * @return String datetime
     */
    public static String getDateTime(Date date, String format) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            long milliseconds = calendar.getTimeInMillis();
            format = (format == null || format.trim().equals("")) ? "dd MMM yyyy HH:mm:ss.sss" : format;
            return (new SimpleDateFormat(format, Locale.getDefault())).format(new Date(milliseconds));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }

    public static long convertTimeStringToLong(String myTimestamp, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        Date date;
        try {
            date = formatter.parse(myTimestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return date.getTime();
    }

    public static int validateInt(String val) {
        try {
            if (val != null) {
                return Integer.parseInt(val);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long validateLong(String val) {
        try {
            if (val != null) {
                return Long.parseLong(val);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }


    public static String strLogTime(int logtime) {
        int h = logtime / 60;
        int m = logtime % 60;
        return pad(h) + TIME_SEPARATOR_TWICE_DOT + pad(m);
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
        n = n == Long.MIN_VALUE ? 0 : n < 0 ? -n : n;
        n = n % (max - min);
        return min + n;
    }

    public static int randInt(int min, int max) {
        Random rnd = new Random();
        int range = max - min + 1;
        return rnd.nextInt(range) + min;
    }


    public static int convertStringToTime(String time) {
        int hours = 0;
        int mins = 0;
        String delimeter = (time.contains(TIME_SEPARATOR_TWICE_DOT)) ? TIME_SEPARATOR_TWICE_DOT : TIME_SEPARATOR_DOT;
        int posDelim = time.indexOf(delimeter);
        try {
            hours = Integer.parseInt(time.substring(0, posDelim));
            mins = Integer.parseInt(time.substring(posDelim + 1, time.length()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mins + (hours * 60);
    }

    public static boolean empty(final String s) {
        return s == null || s.trim().isEmpty();
    }
}
