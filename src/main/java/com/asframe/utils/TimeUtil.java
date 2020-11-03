/**
 * @TimeUtil.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFrame
 * <br>Date:2018-10-1
 */
package com.asframe.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TimeUtil.java 类描述：时间处理工具
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public class TimeUtil {

    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm";
    public static final String FORMAT_DATE_TIME2 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_MONTH_DAY_TIME = "MM月dd日 HH:mm";
    private static final int YEAR = 365 * 24 * 60 * 60;// 年
    private static final int MONTH = 30 * 24 * 60 * 60;// 月
    public static final int DAY = 24 * 60 * 60;// 天
    public static final int DAY_MILL_SEC = DAY * 1000;
    public static final int HOUR = 60 * 60;// 小时
    public static final int MINUTE = 60;// 分钟

//    private static final int TIMEZONE_DELTA = 8 * HOUR;    //东八区时差

    public static final int RESET_ZERO = 0;
    public static final int RESET_FIVE_HOUR = 5;
    public static final int RESET_TWELVE_HOUR = 12;
    public static final int RESET_FIVE = RESET_FIVE_HOUR * HOUR;
    public static final int RESET_TWELVE = RESET_TWELVE_HOUR * HOUR;


    public static SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME2);

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static int currentTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 获取当前时间距离今天0点的秒数
     *
     * @return 秒
     */
//    public static int getNowSecond() {
//        return currentTime() - (((currentTime() + TIMEZONE_DELTA) / DAY) * DAY - TIMEZONE_DELTA);
//    }
    public static int getNowSecond() {
        Calendar calendar = Calendar.getInstance();
        long curMillTime = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long zeroMillTime = calendar.getTimeInMillis();
        return (int) ((curMillTime - zeroMillTime) / 1000);
    }

    /**
     * 获取今天某个小时的时间戳
     * @param hour
     * @return
     */
    public static long getTodayHourMillis(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取今天的刷新时间
     * @return
     */
    public static long getTodayRefreshTime(int refreshHour){
        return TimeUtil.getTodayHourMillis(refreshHour);
    }

    /**
     * 获取零点时刻
     * @param day
     * @return
     */
    public static long getZeroTime(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE,day);
        return calendar.getTimeInMillis();
    }

    /**
     * 判断是否同一天
     *
     * @param timestamp
     * @return
     */
//    public static boolean inSameDay(int timestamp) {
//        int now = currentTime();
//        return (timestamp + TIMEZONE_DELTA) / DAY == (now + TIMEZONE_DELTA) / DAY;
//    }

    public static boolean inSameDay(int timestamp) {
        Calendar calendar = Calendar.getInstance();
        int curDay = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTimeInMillis(1000L * timestamp);
        int timestampDay = calendar.get(Calendar.DAY_OF_YEAR);

        return curDay == timestampDay;
    }

//    /**
//     * 判断是否在同一天
//     *
//     * @param date
//     * @return
//     */
//    public static boolean isSameDate(Date date) {
//        Date now = new Date();
//        return now.getDay() == date.getDay();
//    }

    /**
     * 结算时间内是否在同一天
     *
     * @param timestamp
     * @param clearingTime
     * @return
     */
//    public static boolean isSameDay(int timestamp, int clearingTime) {
//        int now = currentTime();
//        return (timestamp + TIMEZONE_DELTA - clearingTime) / DAY == (now + TIMEZONE_DELTA - clearingTime) / DAY;
//    }

    public static boolean isSameDay(int timestamp, int clearingTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        int curDay = (int) ((calendar.getTimeInMillis() - 1000L * clearingTime) / DAY_MILL_SEC);

        calendar.setTimeInMillis(1000L * timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        int timestampDay = (int) ((calendar.getTimeInMillis() - 1000L * clearingTime)/ DAY_MILL_SEC);

        return curDay == timestampDay;
    }

    /**
     * 距离现在多少天
     *
     * @param timestamp
     * @return
     */
//    public static int getDayDiff(int timestamp) {
//        int now = currentTime();
//        return (now + TIMEZONE_DELTA) / DAY - (timestamp + TIMEZONE_DELTA) / DAY;
//    }
    public static int getDayDiff(int timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int nowDay = (int) (calendar.getTimeInMillis() / (1000 * DAY));

        long time = 1000L * timestamp;
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int timestampDay = (int) (calendar.getTimeInMillis() / (1000 * DAY));

        return nowDay - timestampDay;
    }


    public static int getDayDiffToOpen(String openTime) {
        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.FORMAT_DATE_TIME2);
        try {
            Date date = sdf.parse(openTime);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int day1 = (int) (calendar.getTimeInMillis() / (1000 * DAY));
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int day2 = (int) (calendar.getTimeInMillis() / (1000 * DAY));
        return day2 - day1 + 1;
    }

    /**
     * 获取多少天后的时间戳（秒）
     * @param day 天数
     * @return 时间戳
     */
    public static int getTimeDiffToNow(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, day);
        return (int)(calendar.getTimeInMillis() / 1000);
    }

    /**
     * 某日期距离开服有多少天
     * @param openTime 开服
     * @param timeStamp 某日期
     * @return
     */
    public static int getDayDiffToOpen(int openTime, int timeStamp) {
        long time = 1000L * openTime;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int openDay = (int) (calendar.getTimeInMillis() / (1000 * DAY));

        time = 1000L * timeStamp;
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int timestampDay = (int) (calendar.getTimeInMillis() / (1000 * DAY));

        return timestampDay - openDay + 1;
    }

    public static long getSecondDiffToOpen(String openTime) {
        try {
            Date date = dateFormat.parse(openTime);
            return (System.currentTimeMillis() - date.getTime())/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 当前天定时时间戳
     *
     * @param hour minutes
     * @return
     */
//    public static int timingTime(int hour, int minutes) {
//
//        return ((currentTime() + TIMEZONE_DELTA) / DAY) * DAY - TIMEZONE_DELTA + hour * HOUR + minutes * MINUTE;
//    }
    public static int timingTime(int hour, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);

        long time = calendar.getTimeInMillis() / 1000;
        return (int) (time + hour * HOUR + minutes * MINUTE);
    }

    /**
     * 当前天定时时间戳
     *
     * @param hour 当天的时间，24小时进制
     * @return
     */
//    public static int timingTime(int hourseconds) {
//        return ((currentTime() + TIMEZONE_DELTA) / DAY) * DAY - TIMEZONE_DELTA + hourseconds;
//    }
    public static int timingTime(int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);

        long time = calendar.getTimeInMillis() / 1000;
        return (int) (time + hour);
    }

    /**
     * 当前时间距离下一天零点钟多少秒
     *
     * @return
     */
    public static int leftNextZeroTime() {
        int now = currentTime();
        int zero = timingTime(RESET_ZERO);
        return zero + DAY - now;
    }

    /**
     * 距离未来指定时间多少天
     * @param deadLineTime
     * @return
     */
    public static int getCardLeftDay(int deadLineTime){
        int nowTime = TimeUtil.currentTime();
        int day = 0;
        while (deadLineTime > nowTime) {
            day++;
            deadLineTime -= TimeUtil.DAY;
        }
        return day;
    }


    /**
     * 当前时间距离五点钟多少秒，如5点前则加上DAY
     *
     * @return
     */
    public static int leftTimeFromFive() {
        int now = currentTime();
        int five = timingTime(RESET_FIVE);
        if (now > five) return five + DAY - now;
        else return now - five;
    }

    /**
     * 当前星期几
     * 1970-1-1: 即time = 0为星期四
     * week值分别为0，1，2，3，4，5，6
     * 即表示星期天，星期一，星期二，星期三，星期四，星期五，星期六
     *
     * @return
     */
    public static int nowWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 十三位的时间戳转换成星期几
     * 1970-1-1: 即time = 0为星期四
     * week值分别为0，1，2，3，4，5，6
     * 即表示星期天，星期一，星期二，星期三，星期四，星期五，星期六
     *
     * @param curTime
     * @return
     */
    public static int time2Week(int curTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1000L * curTime);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 当前时间距离整点多少秒
     *
     * @return
     */
    public static int leftTimeHour() {
        return timingTime((hourSeconds() + 1) * HOUR) - currentTime();
    }

    /**
     * 整点时间戳
     *
     * @return
     */
    public static int hourSeconds() {
        return (currentTime() - timingTime(0)) / HOUR;
    }

    /**
     * 获取当前年月日
     *
     * @return 返回8位数字 20170420
     */
    public static int getCurrentYearMonthDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        return year * 10000 + month * 100 + day;
    }

    /**
     * 获取昨天的年月日
     * @return 返回8位数字 20170419
     */
    public static int getYesterdayYearMonthDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis() - DAY_MILL_SEC);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        return year * 10000 + month * 100 + day;
    }

    /**
     * 根据时间戳获取年月日
     * @return 返回8位数字 20170419
     */
    public static int getYearMonthDay(int curTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((long)curTime * 1000L);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        return year * 10000 + month * 100 + day;
    }

    /**
     * 获取当前的年月日
     * @return 返回6位数字 180201
     */
    public static int getSimpleYearMonthDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        return (year % 2000) * 10000 + month * 100 + day;
    }

    /**
     * 获取当前年月
     *
     * @return 返回6位数字 201704
     */
    public static int getCurrentYearMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return year * 100 + month;
    }

    /**
     *获取某天的0点时间戳
     * @param year 年份
     * @param month 月份 1-12
     * @param day 天数 1-30
     * @return
     */
    public static int getTimeStampZero(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);

        int time = (int)(calendar.getTimeInMillis() / 1000);
        return time;
    }

    /**
     * 获取某天的0点时间戳
     * @param yearMonthDay 20180201(8位数)
     * @return
     */
    public static int getTimeStampZero(int yearMonthDay){
        String string = String.valueOf(yearMonthDay);
        if (string.length() != 8){
            return 0;
        }
        int year = Integer.valueOf(string.substring(0, 4));
        int month = Integer.valueOf(string.substring(4, 6));
        int day = Integer.valueOf(string.substring(6, 8));
        return TimeUtil.getTimeStampZero(year, month, day);
    }

    /**
     * 当月最大天数
     *
     * @return 28~31
     */
    public static int getMaxDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取今天的日数
     *
     * @return 1~31
     */
    public static int getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前时间的日期格式
     *
     * @return
     */
    public static String getFormatDate() {
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * 获得指定时间的日期格式
     * @return
     */
    public static String getFormatDate(int time){
        Date date = new Date((long)time*1000);
        return dateFormat.format(date);
    }

    public static String getFormatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String getFormatDate2() {
        Date date = new Date();
        return (new SimpleDateFormat(FORMAT_DATE)).format(date);
    }

    /**
     * 现在距离过去指定时间第几天
     *
     * @param timestamp 过去的一个时间，比当前时间小
     * @return 天数
     */
    public static int getDayDisTime(int timestamp) {
        int curTime = timingTime(RESET_ZERO);
        int day = 1;
        while (timestamp < curTime) {
            curTime -= DAY;
            day += 1;
        }
        return day;
    }

    /**
     * 使用参数Format将字符串转为Date
     */
    public static int getTimeFromDate(String strDate){
        try {
            Date date = new SimpleDateFormat(FORMAT_DATE_TIME2).parse(strDate);
            return (int) (date.getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取某天的0点时间
     * @param timeStamp 时间戳 秒
     * @return 时间戳 秒
     */
    public static int getZeroTimeStamp(int timeStamp){
        long time = 1000L * timeStamp;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return (int)(calendar.getTimeInMillis() / 1000);
    }

    /**
     * 获取当前时间戳（秒）
     */
    public static int timeStampSecond(){
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static void main(String[] args) {

        System.out.println(0x7FFFFFFE ^(1<<28));

        System.out.println(nowWeek());

        System.out.println(timingTime(16, 46));
        System.out.println(timingTime(RESET_FIVE));
        System.out.println(leftTimeFromFive() / 7200);
        System.out.println(inSameDay(timingTime(16, 46)));
        System.out.println(currentTime());
        System.out.println(nowWeek());
        System.out.println(time2Week(1482163100));//1
        System.out.println(time2Week(1482163200));//2
        System.out.println(time2Week(1482249599));//2
        System.out.println(time2Week(1482249600));//3
        System.out.println(time2Week(1482249601));//3

        System.out.println(leftTimeHour());
        System.out.println(hourSeconds());

        System.out.println(getNowSecond());
        System.out.println(getCurrentYearMonthDay());
        System.out.println(getFormatDate());

        System.out.println(Integer.parseInt("12999"));
        System.out.println(getSimpleYearMonthDay());
    }
}
