package com.fama.app.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ist on 14/10/16.
 */

public class DateUtils {


    @SuppressLint("SimpleDateFormat")
    public static String getDateTime(String value) {
        if (value != null && !"".equals(value)) {
            long millisecond = Long.parseLong(value);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(millisecond);
            String timeStamp = new SimpleDateFormat("dd-MMM-yy HH:mm:ss").format(cal.getTime());
            return timeStamp;
        }
        return "0";
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateTime(Long value) {
        if (value != null && !"".equals(value)) {
            long millisecond = value;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(millisecond);
            String timeStamp = new SimpleDateFormat("dd-MMM-yy HH:mm:ss").format(cal.getTime());
            return timeStamp;
        }
        return "0";
    }


    @SuppressLint("SimpleDateFormat")
    public static String getDate(String value) {
        if (value != null && !"".equals(value)) {
            long millisecond = Long.parseLong(value);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(millisecond);
            String timeStamp = new SimpleDateFormat("dd-MMM-yy").format(cal.getTime());
            return timeStamp;
        }
        return "0";
    }

    public static String getMonthDate(String value) {
        if (value != null && !"".equals(value)) {
            long millisecond = Long.parseLong(value);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(millisecond);
            String timeStamp = new SimpleDateFormat("dd MMM").format(cal.getTime());
            return timeStamp;
        }
        return "0";
    }

    public static String getMonthNameYear(){

        try {
            Date now = new Date();
            DateFormat sdf;
            String timeStamp;
            sdf = new SimpleDateFormat("dd MMMM yyyy");
            timeStamp = sdf.format(now);
            return timeStamp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    @SuppressLint("SimpleDateFormat")
    public static String getTime(String value) {
        if (value != null && !"".equals(value)) {
            long millisecond = Long.parseLong(value);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(millisecond);
            String timeStamp = new SimpleDateFormat("HH:mm:ss").format(cal.getTime());
            return timeStamp;
        }
        return "0";
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean isDateValid(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
        Date todatDate = new Date(getTimeInMili(getDate(System.currentTimeMillis())));
        Date strDate;
        try {
            strDate = sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        if (todatDate.after(strDate)) {
            return true;
        }
        return false;
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean isDateTimeValid(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy HH:mm");
        Date todatDate = new Date(System.currentTimeMillis());
        Date strDate;
        try {
            strDate = sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        if (todatDate.after(strDate)) {
            return true;
        }
        return false;
    }


    @SuppressLint("SimpleDateFormat")
    public static String getDate(Long value) {
        if (value != null && !"".equals(value)) {
            long millisecond = value;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(millisecond);
            String timeStamp = new SimpleDateFormat("dd-MMM-yy").format(cal.getTime());
            return timeStamp;
        }
        return "0";
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMonthDate(Long value) {
        if (value != null && !"".equals(value)) {
            long millisecond = value;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(millisecond);
            String timeStamp = new SimpleDateFormat("dd MMM").format(cal.getTime());
            return timeStamp;
        }
        return "0";
    }


    @SuppressLint("SimpleDateFormat")
    public static long getTimeInMili(String dateTime){
        SimpleDateFormat sdf;
        if (dateTime.length()<11) {
            sdf = new SimpleDateFormat("dd-MMM-yy");
        }else if(dateTime.length()>11 && dateTime.length()<17){
            sdf = new SimpleDateFormat("dd-MMM-yy HH:mm");
        }else {
            sdf = new SimpleDateFormat("dd-MMM-yy HH:mm:ss");
        }
        long timeInMilliseconds=0;
        try {
            Date date = sdf.parse(dateTime);
            timeInMilliseconds = date.getTime();
        } catch (java.text.ParseException e) {

            e.printStackTrace();
        }

        return timeInMilliseconds;
    }

    /**
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     * */
    public String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        // Add hours if there
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;}

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    public static String convertMilisecontToTime(String milisecond) {

        System.out.println("convertMilisecontToTime  "+milisecond);
        String x = "" + milisecond;
        if (milisecond != null && !milisecond.equalsIgnoreCase("")) {
            SimpleDateFormat formatter = null;
            formatter = new SimpleDateFormat("HH:mm:ss");
            long milliSeconds = Long.parseLong(x);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            return formatter.format(calendar.getTime());
        }
        return "0:0";
    }

    public static String millisToMinutesAndSeconds(String time) {
        try {
            if(time!=null && !time.equalsIgnoreCase("null")) {
                long millis = Long.parseLong(time);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
                String sec = String.valueOf(seconds);
                if (sec.length() > 1) {
                    return minutes + ":" + sec.substring(0, 2);
                } else {
                    return minutes + ":" + sec;
                }
            }else{
                return "0:0";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0:0";

        }
    }


}
