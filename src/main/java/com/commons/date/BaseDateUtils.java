package com.commons.date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 基本的日期类型
 * 基于java8
 * Created by Administrator on 2016/4/9.
 */
public class BaseDateUtils extends DateUtils {

    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public final static String DATE_FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public final static String DATE_FORMAT_yyyyMMdd = "yyyyMMdd";
    public final static String DATE_FORMAT_yyMMddHHmmss = "yyMMddHHmmss";
    public final static String DATE_FORMAT_yyyy_MM_dd_HH = "yyyy-MM-dd HH";

    /**
     * 默认时间格式化
     *
     * @param date Date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String defaultFormat(Date date) {
        if (date != null) {
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            return localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
        }
        return null;
    }

    /**
     * 自定义时间格式化
     *
     * @param date Date
     * @param str  自定义格式
     * @return str格式的字符串
     */
    public static String selfFormat(Date date, String str) {
        if (date != null) {
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            return localDateTime.format(DateTimeFormatter.ofPattern(str));
        }
        return null;
    }

    /**
     * 获取给定日期前一天的时间格式化
     *
     * @param date Date
     * @param str  自定义格式
     * @return str格式的字符串
     */
    public static String yesterdayFormat(Date date, String str) {
        if (date != null) {
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).minusDays(1);
            return localDateTime.format(DateTimeFormatter.ofPattern(str));
        }
        return null;
    }

    /**
     * 字符串格式转换为date
     *
     * @param dateStr 日期字符串，如2011-01-01
     * @param format  日期字符串形式,如yyyy-MM-dd
     * @return Date类型
     */
    public static Date strToDate(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(format)) {
            return null;
        }
        switch (format) {
            case DEFAULT_DATE_FORMAT:
            case DATE_FORMAT_yyyyMMddHHmmss:
            case DATE_FORMAT_yyMMddHHmmss:
            case DATE_FORMAT_yyyy_MM_dd_HH:
                LocalDateTime localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(format));

                return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            case DATE_FORMAT_yyyy_MM_dd:
            case DATE_FORMAT_yyyyMMdd:
                LocalDate localDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(format));
                return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }
}
