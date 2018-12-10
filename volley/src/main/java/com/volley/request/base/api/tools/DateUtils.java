package com.volley.request.base.api.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.TextUtils;


public class DateUtils {

    public static String stringToTime(String timeStr) {
        if (timeStr == null) {
            return null;
        }
        String ret = "";
        int time = Integer.valueOf(timeStr);
        int m = time / 60;
        int s = time % 60;
        ret = (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
        return ret;
    }

    public static String secondToTimeString(long time) {
        if (time <= 0) {
            return "00:00";
        }
        String ret = "";
        long m = time / 60;
        long s = time % 60;
        ret = (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
        return ret;
    }

    /**
     * 
     * TODO:获取当前时间 年月日时分秒格式
     * 
     * @param @return
     * @param @throws ParseException
     */
    public static String string2Timestamp() {
        try {
        	SimpleDateFormat sDateFormat =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        	Date curDate =new Date(System.currentTimeMillis());//获取当前时间
        	String str =sDateFormat.format(curDate);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期
     * 
     * @param data
     * @return
     */
    public static String getMMDDData(String data) {
        String time = null;
        if (TextUtils.isEmpty(data)) {
            return time;
        }
        String[] str = data.split(" ");
        if (str.length > 0) {
            String[] hsm = str[0].split("-");
            time = hsm[1] + "-" + hsm[2];
        }
        return time;
    }

    /**
     * 把long时间转换成 yyyy-mm-dd
     * 
     * @param time
     *            long时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String getLongDate(String time) {
        String retime = null;
        try {
            if (time.length() != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                long longTime = Long.valueOf(time);
                retime = sdf.format(new Date(longTime * 1000L));
            } else {
                retime = "未知";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retime;
    }

    /**
     * 
     * TODO:输入一个时间，获取该时间的时间戳
     * 
     * @param @param dateString
     * @param @return
     * @param @throws ParseException
     */
    public static long string2Timestamp1(String dateString) {
        if (dateString == null) {
            return 0;
        }
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            long temp = date1.getTime();// JAVA的时间戳长度是13位
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 
     * TODO:输入一个时间，获取该时间的时间戳
     * 
     * @param @param dateString
     * @param @return
     * @param @throws ParseException
     */
    public static long string2Timestamp2(String dateString) {
        if (dateString == null) {
            return 0;
        }
        try {
            Date date1 = new SimpleDateFormat("HH:mm:ss").parse(dateString);
            long temp = date1.getTime();// JAVA的时间戳长度是13位
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 
     * TODO:输入一个时间，获取该时间的时间戳 年月日时分秒
     * 
     * @param @param dateString
     * @param @return
     * @param @throws ParseException
     */
    public static long string2Timestamp3(String dateString) {
        if (dateString == null) {
            return 0;
        }
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
            long temp = date1.getTime();// JAVA的时间戳长度是13位
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
