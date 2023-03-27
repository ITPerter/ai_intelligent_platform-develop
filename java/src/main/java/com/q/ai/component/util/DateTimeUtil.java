package com.q.ai.component.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Tong on 3/20/2019.
 * Wednesday
 */
public class DateTimeUtil {

//yyyy-MM-dd HH:mm:ss

    /**
     * 获取昨天的date String  such as "2019-08-01"
     *
     * @return
     */
    public static String getYesterdayDateString() {
        return LocalDateTime.now().minusDays(1).toLocalDate().toString();
    }

    public static String getLastMonthString() {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    /**
     * 纪元毫秒时间戳转小时分钟  such as "1552989879430" ->  "20:03"
     *
     * @return
     */
    public static String timeStamp2FormatterString(long milliEpoch, String formatterString) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(milliEpoch / 1000, 0, OffsetDateTime.now().getOffset());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterString);
        return localDateTime.format(formatter);
    }

    /**
     * @param localDateTime
     * @param formatterString
     * @return
     */
    public static String localDateTime2FormatterString(LocalDateTime localDateTime, String formatterString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterString);
        return localDateTime.format(formatter);
    }

    public static String localDateTime2FormatterString(LocalDate localDate, String formatterString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterString);
        return localDate.format(formatter);
    }


    /**
     * 返回今天0点的纪元毫秒
     *
     * @return Long 今天0点的纪元毫秒
     */
    public static LocalDate getTodayLocalDate() {
        return LocalDate.now();
    }

    public static String getNowString(String formatterString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterString);
        return LocalDateTime.now().format(formatter);
    }

    /**
     * yyyy-MM-dd HH:mm
     *
     * @param time
     * @param
     * @return
     */
    public static LocalDateTime string2LocalDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(time, formatter);
    }

    public static LocalDateTime string2LocalDateTime(String time,String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(time, formatter);
    }

    public static LocalDate string2LocalDate(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(time, formatter);
    }


}
