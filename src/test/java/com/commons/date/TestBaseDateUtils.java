package com.commons.date;

import junit.framework.TestCase;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/9.
 */
public class TestBaseDateUtils {

    @Test
    public void defaultFormat() {
        Instant instant = Instant.now();
        Date d = Date.from(instant);
        String result = BaseDateUtils.defaultFormat(d);
        TestCase.assertEquals(result, instant.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(BaseDateUtils.DEFAULT_DATE_FORMAT)));
    }

    @Test
    public void selfFormat() {
        Instant instant = Instant.now();
        Date d = Date.from(instant);
        String str = instant.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(BaseDateUtils.DATE_FORMAT_yyMMddHHmmss));
        Assert.assertEquals(str, BaseDateUtils.selfFormat(d, BaseDateUtils.DATE_FORMAT_yyMMddHHmmss));
    }

    @Test
    public void yesterdayFormat() {
        Instant instant = Instant.now().minus(1, ChronoUnit.DAYS);
        String yesterday = instant.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(BaseDateUtils.DATE_FORMAT_yyyy_MM_dd_HH));
        Assert.assertEquals(yesterday, BaseDateUtils.yesterdayFormat(new Date(), BaseDateUtils.DATE_FORMAT_yyyy_MM_dd_HH));
    }

    @Test
    public void strToDate() {
        String dateStr = "2016-01-01 12";
        Date resultDate = BaseDateUtils.strToDate(dateStr, BaseDateUtils.DATE_FORMAT_yyyy_MM_dd_HH);
        LocalDate localDate = LocalDate.of(2016, 1, 1);
        LocalTime localTime = LocalTime.of(12, 0);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        Assert.assertEquals(resultDate, Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }
}
