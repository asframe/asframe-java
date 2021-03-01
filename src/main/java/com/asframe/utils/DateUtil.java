/**
 * @DateUtil.java
 * @author sodaChen  E-mail:sujun10@21cn.com
 * @version 1.0
 * <br>Copyright (C), 2010 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:TribeGameServer
 * <br>Date:2010-7-2
 */
package com.asframe.utils;


import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 对时间进行转换的处理工具
 *
 * @author sodaChen
 * @date 2010-7-2
 */
public final class DateUtil {

//	private static SimpleDateFormat	sdf			= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private static SimpleDateFormat	sdfSimple	= new SimpleDateFormat("yyyy-MM-dd");
//	private static SimpleDateFormat	minTimeSdf	= new SimpleDateFormat("HH:mm");
//	private static SimpleDateFormat	minSdf		= new SimpleDateFormat("HH:mm:ss");

    public static Date dateAdd(Date date, int type, int ymdhms) {
        if (date == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (type) {
            case 1: // year
                calendar.add(Calendar.YEAR, ymdhms);
                break;
            case 2: // month
                calendar.add(Calendar.MONTH, ymdhms);
                break;
            case 3: // day
                calendar.add(Calendar.DATE, ymdhms);
                break;
            case 4: // hour
                calendar.add(Calendar.HOUR, ymdhms);
                break;
            case 5: // minute
                calendar.add(Calendar.MINUTE, ymdhms);
                break;
            case 6: // second
                calendar.add(Calendar.SECOND, ymdhms);
                break;
        }
        // 6.second 5.minutes 4.hour 3.day 2.month 1.year
        return calendar.getTime();
    }

    /**
      * @param date
      * @param day 想要获取的日期与传入日期的差值 比如想要获取传入日期前四天的日期 day=-4即可
      * @return
    */
      public static Date getSomeDay(Date date, int day){
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          calendar.add(Calendar.DATE, day);
          return calendar.getTime();
      }

    /**
     * 获取精确到秒的时间戳
     * @return
     */
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }

    /**
     * 两个时间比较结果，true前者大于后者，反之
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean dateCompare(Date date1, Date date2) {
        long v1 = (date1 == null ? 0l : date1.getTime());
        long v2 = (date2 == null ? 0l : date2.getTime());
        return v1 > v2;
    }

    /**
     * 返回比较两个时间相差的时间(秒)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(Date date1, Date date2) {
        long v1 = (date1 == null ? 0l : date1.getTime());
        long v2 = (date2 == null ? 0l : date2.getTime());
        return (int) ((v1 - v2) / 1000);
    }

    public static String dateToStr(Date date, String style) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (date == null)
                return "";
            if (style == null)
                return sdf.format(date);
            return (new SimpleDateFormat(style).format(date));
        } catch (Exception e) {
            System.err.println("[dateToStr] error: " + e);
        }
        return "";
    }

    public static Date dateAddDay(Date date, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,days); //把日期往后增加一天,整数  往后推,负数往前移动
        return calendar.getTime();
    }

    public static long dateToLong(Date date) {
        return (date == null ? 0l : date.getTime() / 1000 * 1000);
    }

    public static Date dateSub(Date date, int type, int ymdhms) {
        return dateAdd(date, type, 0 - ymdhms);
    }

    public static String longToDateStr(long mills) {
        return dateToStr(mills < 100l ? null : new Date(mills));
    }

    public static Date longToDate(long mills) {
        return (mills < 100l ? null : new Date(mills));
    }

    public static Date dateAdd(Date date, long minus) {
        return (date == null ? null : longToDate(date.getTime() + minus));
    }

    public static String dateToStr() {
        return dateToStr(new Date());
    }

    public static String dateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (date == null ? "" : sdf.format(date));
    }

    public static String dateToDayStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return (date == null ? "" : sdf.format(date));
    }

    public static int deviationDay(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        if (c1.getTime().getTime() < c2.getTime().getTime())
            return 0;
        return (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 12
                + (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
    }

    public static Date getCurrDate(String style) {
        SimpleDateFormat sdf = null;
        Date date = new Date();
        try {
            sdf = new SimpleDateFormat(style);
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String currDate() {
        return currDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String currDate(String style) {
        return currDate(new Date(System.currentTimeMillis()), style);
    }

    public static String currDate(Date date, String style) {
        SimpleDateFormat df = null;
        try {
            df = new SimpleDateFormat(style);
        } catch (Exception ex) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return df.format(date);
    }

    public static String toDate(long time) {
        return currDate(new Date(time), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 比较当前凌晨时间，返回相差天数
     *
     * @param time 后者时间
     * @return
     */
    public static int compareToDay(long time) {
        return compareToDay(new Date(time));
    }

    /**
     * 比较当前凌晨时间，返回相差天数
     *
     * @param date 后者时间
     * @return
     */
    public static int compareToDay(Date date) {
        long day = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(sdf.format(new Date()));
            Date date2 = sdf.parse(sdf.format(date));
            day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) day;
    }

    public static int getDateHour(Date date) {
        return (int) (date.getTime() / (60 * 60 * 1000));
    }

    public static long compareToDay(String date) {
        long day = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(sdf.format(new Date()));
            Date date2 = sdf.parse(date);
            day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 是否已经超时了（传入时间是否小于当前的时间）
     *
     * @param sceTime
     * @return
     */
    public static boolean isTimeOut(long sceTime) {
        return getCurrentSceTime() >= sceTime;
    }

    /**
     * 是否已经超时了（传入时间是否小于当前的时间）
     *
     * @param sceTime
     * @return
     */
    public static boolean isTimeOutByMillis(long sceTime) {
        return System.currentTimeMillis() >= sceTime;
    }

    /**
     * 是否已经超时了（传入时间是否小于当前的时间）
     *
     * @param startTime:开始的时间
     * @param useTime:需要使用的时间
     * @return
     */
    public static boolean isTimeOut(long startTime, long useTime) {
        return System.currentTimeMillis() >= (startTime + useTime);
    }

    /**
     * 获取1900到当前的时间(单位秒)
     *
     * @return
     */
    public static long getCurrentSceTime() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 是否在两个时间段之间
     *
     * @param startTime:开始时间
     * @param endTime:结束时间
     * @return
     */
    public static boolean isMidTime(String startTime, String endTime, long nowTime) {
        try {
            SimpleDateFormat minTimeSdf = new SimpleDateFormat("HH:mm");
            Date now = new Date(nowTime);
            now = minTimeSdf.parse(minTimeSdf.format(now));
            Date begin = minTimeSdf.parse(startTime);
            Date end = minTimeSdf.parse(endTime);
            //判断是否在范围内
            if (now.after(begin)) {
                return !now.after(end);
            }
        } catch (ParseException e) {
//			LogUtils.error(e);
        }
        return false;
    }

    /**
     * 今天是否星期X
     *
     * @param weekX:星期X
     * @return
     */
    public static boolean nowIsWeeX(int weekX) {
        return weekX == getDayOfWeek(System.currentTimeMillis());
    }

    /**
     * 获取时间为星期几
     *
     * @param nowTime
     * @return(星期一1，星期二2……星期日7)
     */
    public static int getDayOfWeek(long nowTime) {
        Date now = new Date(nowTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek <= 0)
            dayOfWeek = 7;
        return dayOfWeek;
    }

    /**
     * 看传入的日期是否当天
     *
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(now).equals(sdf.format(date));
    }

    /**
     * 获取HH:mm的时间与当前时间的时间差
     * 如果小于0，则代表当前时间大
     *
     * @param date
     * @return
     */
    public static Long getGapTime(String date) {
        try {
            SimpleDateFormat minSdf = new SimpleDateFormat("HH:mm:ss");
            date += ":00";
            Date time = minSdf.parse(date);
            String nowStr = minSdf.format(new Date(System.currentTimeMillis()));
            Date now = minSdf.parse(nowStr);
            Long gap = (time.getTime() - now.getTime());
            return gap;
        } catch (ParseException e) {
//			LogUtils.error(e + date);
        }
        return 0L;
    }

    /**
     * 获取HH:mm的时间的当天日期
     *
     * @param time
     * @return
     */
    public static Long getTodayTime(String time) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdfDate.format(new Date(System.currentTimeMillis()));
        today += " " + time + ":00";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dayTime = sdf.parse(today);
            return dayTime.getTime();
        } catch (ParseException e) {
//			LogUtils.error(e);
        }
        return 0L;
    }

    /**
     * 获取HH:mm比赛结束前三分钟时间
     *
     * @param time
     * @return
     */
    public static Long getRaceBeforeThree(String time) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdfDate.format(new Date(System.currentTimeMillis()));
        today += " " + time + ":00";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dayTime = sdf.parse(today);
            return dayTime.getTime() - 3 * 60 * 1000;
        } catch (ParseException e) {
//			LogUtils.error(e);
        }
        return 0L;
    }


    /**
     * 获取HH:mm的时间的当天日期
     *
     * @param time
     * @return
     */
    public static Long getLastTodayTime(String time) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdfDate.format(new Date(System.currentTimeMillis()));
        today += " " + time + ":59";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dayTime = sdf.parse(today);
            return dayTime.getTime();
        } catch (ParseException e) {
//			LogUtils.error(e);
        }
        return 0L;
    }

    /**
     * 返回上周星期几，星期天为最后一天
     * 1，取出当前时间，减去7天
     * 2，查看当天是星期几，
     * 3，如果小于要求的天数，则加上相应天数
     * 4，如果大于要求天数，则减去相应天数，周日除外
     *
     * @param day
     * @return
     */
    public static String getLastWeekDate(int day) {
        SimpleDateFormat sdfSimple = new SimpleDateFormat("yyyy-MM-dd");
        //1
        long dateTime = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7;
        //2
        int dayNew = getDayOfWeek(dateTime);
        //3
        if (dayNew < day)
            dateTime += (day - dayNew) * 1000 * 60 * 60 * 24;
        if (dayNew > day)
            dateTime -= (dayNew - day) * 1000 * 60 * 60 * 24;
        return sdfSimple.format(new Date(dateTime));
    }


    //获取当前年月日(yyyy-MM-dd)
    public static String getCurrentDay() {
        Date date = new Date();
        SimpleDateFormat sdfSimple = new SimpleDateFormat("yyyy-MM-dd");
        return sdfSimple.format(date);
    }

    //获取当前年月日(yyyy-MM-dd)
    public static Date getCurrentDate() {
        try {
            Date date = new Date();
            SimpleDateFormat sdfSimple = new SimpleDateFormat("yyyy-MM-dd");
            return sdfSimple.parse(sdfSimple.format(date));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取当前年月日
    public static String formatDate(Date date) {
        try {
            SimpleDateFormat sdfSimple = new SimpleDateFormat("yyyy-MM-dd");
            return sdfSimple.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * String 日期转成Date日期 yyyy-MM-dd
     *
     * @param dateStr
     * @return
     */
    public static Date paseDate1(String dateStr) {
        try {
            SimpleDateFormat sdfSimple = new SimpleDateFormat("yyyy-MM-dd");
            return sdfSimple.parse(dateStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * String 日期转成Date日期 yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     */
    public static Date paseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(dateStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * String 日期转成Date日期 yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     */
    public static Date paseDateDay(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @param startTime 开始日期
     * @param endTime   结束日期
     * @return
     */
    public static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

    /**
     * 当前时间是否在开始时间和结束时间之间
     *
     * @param startTimeStr
     * @param endTimeStr
     * @return
     */
    public static boolean betweenDate(String startTimeStr, String endTimeStr) {
        try {
            Date nowDate = new Date();
            Date startDate = paseDate(startTimeStr);
            Date endDate = paseDate(endTimeStr);
            long currentTime = nowDate.getTime();
            if (currentTime > startDate.getTime() && currentTime < endDate.getTime()) {
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 当前时间是否在开始时间和结束时间之间
     *
     * @param startTimeStr
     * @param endTimeStr
     * @return
     */
    public static boolean betweenDateSpring(String startTimeStr, String endTimeStr) {
        try {
            Date nowDate = new Date();
            Date startDate = paseDate(startTimeStr);
            Date endDate = paseDate(endTimeStr);
            long currentTime = nowDate.getTime() + 1000 * 3;
            if (currentTime > startDate.getTime() && currentTime < endDate.getTime()) {
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static long beforeCurrentDay() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date time = cal.getTime();
            String timeStr = sdf.format(time);
            return sdf.parse(timeStr).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取前一天日期
     */
    public static String getBeforeDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 获取前一天日期
     */
    public static String getBeforeDate(int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 获取前一天日期
     */
    public static Date beforeDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 两个时间相差多少秒
     *
     */
    public static int getDistanceSecondTime(long startTime, long endTime) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            long diff;
            if (startTime < endTime) {
                diff = endTime - startTime;
            } else {
                diff = startTime - endTime;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) sec;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }


    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static long getDistanceTime(Date date1, Date date2) {
        long day = 0;
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 两个时间相差距离多少天多少小时
     *
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceDayHour(long timeOne, long timeTwo) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        try {
            long time1 = timeOne;
            long time2 = timeTwo;
            long diff;
//			if(time1<time2) {
            diff = time2 - time1;
            diff = diff < 0 ? 0 : diff;
//			} else {
//				diff = time1 - time2;
//			}
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时";
    }

    /**
     * 两个时间是不是同一天
     * return 0-是同一天，1-不是同一天
     */
    public static int isSameTime(long creatTime, long loginTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String oneStr = sdf.format(new Date(creatTime));
        String twoStr = sdf.format(new Date(loginTime));
//		System.out.println("creatTime:"+ creatTime);
//		System.out.println("loginTime:"+loginTime);
        if (oneStr.equals(twoStr) || loginTime == 0) {
//			System.out.println(true);
            return 0;
        } else {
            System.out.println(false);
            return 1;
        }
    }

    /**
     * 传入时间戳
     * 两个日期间隔天数
     */
    public static int differentDays(long one, long two) {

        //时间戳转化为Sting或Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Long time = new Long(445555555);
        String oneStr = format.format(one);
        String twoStr = format.format(two);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(oneStr);
            date2 = format.parse(twoStr);
//            System.out.println("Format To String(Date):"+d);
//            System.out.println("Format To Date:"+date);
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("出错啦");
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }


    //将日期转换成数字190506->160506
    public static long transDateToNumber() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
            String currentDate = sdf.format(new Date());
            return Long.valueOf(currentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //	//将时间(时分秒毫秒)转换为数字
    public static long transDateTimesToNumber(long date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
            String dateTime = sdf.format(new Date(date));
            long dateTimeNumber = Long.valueOf(dateTime);
            return dateTimeNumber;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * String 字符串时间转成时间戳
     */
    public static long transStrToTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(time);
//			System.out.println("Format To times:"+date.getTime());
            return date.getTime();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return 0;
    }

    /**
     * 格式化时间戳
     * @param time
     * @return
     */
    public static String formatTime(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(time);
        return sdf.format(date);
    }
    /**
     * 格式化时间戳
     * @param time
     * @return
     */
    public static String formatTimeSS(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return sdf.format(date);
    }



    public static void main(String[] args) {
        System.out.println(beforeCurrentDay());
    }

	/**
	 * description:
	 *
	 * @Author: liuping
	 * @Date: 2019-10-22
	 */
	public static class FileUtils {
		static Gson gson = new Gson();
		/**
		 * 全部文件覆盖
		 *
		 * @param file
		 * @return
		 */
		public static void createWriteNoAppent(String file, Map<String,String> strMap) {
			String str = gson.toJson(strMap);
			//创建BufferedWriter
			BufferedWriter out = null;
			try {
				File fileName = new File(file);

				if (fileName.exists()) {
					fileName.delete();
				}
				fileName.createNewFile();
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file)));
				out.write(str);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}






	}


	public static long transDate(String timeStr) {
	    try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            long time = simpleDateFormat.parse(timeStr).getTime();
            return time;
        }catch (Exception e){
	        e.printStackTrace();
        }
        return 0;
    }

    public static long transDate2(String timeStr){
        try {
            String[] timeDay = timeStr.split("\\.");
            timeStr = timeDay[0]+"."+(timeDay[1].length()==1?0+timeDay[1]:timeDay[1])+"."+(timeDay[2].length()==1?0+timeDay[2]:timeDay[2])+".00.00.00";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            long time = simpleDateFormat.parse(timeStr).getTime();
            return time;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static int transSex(String sex){
	    if("男".equals(sex)){
	        return 1;
        }
        return 2;
    }



}
