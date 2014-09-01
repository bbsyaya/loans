
package com.will.loans.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormtter {

    public static String formatDate(int year, int month, int day) {
        StringBuilder sb = new StringBuilder(String.valueOf(year));
        sb.append("年");
        sb.append(formatLength(month, 2));
        sb.append("月");
        sb.append(formatLength(day, 2));
        sb.append("号");
        return sb.toString();
    }

    private static String formatLength(int data, int num) {
        String result = String.valueOf(data);
        if (result.length() < num) {
            result = addZeroPrefix(result, num - result.length());
        }
        return result;
    }

    private static String addZeroPrefix(String data, int num) {
        StringBuilder result = new StringBuilder(data);
        for (int i = 0; i < num; i++) {
            result.insert(0, "0");
        }
        return result.toString();
    }

    public static String convertUtilDateToStringWithTime(Date date) {
        return convertUtilDateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertUtilDateToString(Date date, String dateFormat) {
        String strDate = "";
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            strDate = format.format(new Date());
        }
        return strDate;
    }
}
