package com.ruoyi.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String YYYY_QQ = "yyyy-QQ";  // 例如: 2024-Q1

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 计算时间差
     *
     * @param endDate   最后时间
     * @param startTime 开始时间
     * @return 时间差（天/小时/分钟）
     */
    public static String timeDistance(Date endDate, Date startTime) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startTime.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor) {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor) {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 计算两个时间相差多少个小时
     */
    public static long getHoursBetween(Date startDate, Date endDate) {
        return Math.abs(endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60);
    }


    /**
     * 获取指定时间的周几
     *
     * @param date 时间
     * @param day  周几
     */
    public static Date getWeekDay(Date date, int day) {
        day = Math.min(7, day);
        day = Math.max(1, day);
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateDay = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.of(day)));
        return toDate(dateDay);
    }

    public static Date getWeekDay(Date date, int week, int day) {
        day = Math.min(7, day);
        day = Math.max(1, day);
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate.plusWeeks(week);
        LocalDate dateDay = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.of(day)));
        return toDate(dateDay);
    }

    public static String getWeekDay(Date date, int week, int day, String format) {
        return parseDateToStr(format, getWeekDay(date, week, day));
    }

    public static String getWeekDay(Date date, int day, String format) {
        return parseDateToStr(format, getWeekDay(date, day));
    }

    /**
     * 根据指定时间返回所在年份的第几周
     */
    public static Long getWeekDayNumber(Date day, Date year) {
        LocalDate dayDate = day.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate yearDate = year.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // 获取year参数所在年份
        int targetYear = yearDate.getYear();

        // 创建一个带有周数计算规则的日期对象（周一作为一周的开始，第一周最少包含1天）
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);

        // 获取目标年份的第一天和最后一天
        LocalDate firstDayOfYear = LocalDate.of(targetYear, 1, 1);
        LocalDate lastDayOfYear = LocalDate.of(targetYear, 12, 31);

        // 如果day日期在目标年份之前或之后，也可以计算其在该年份中的相对周数
        if (dayDate.isBefore(firstDayOfYear)) {
            // 日期在目标年份之前，返回第0周或第1周
            return 1L;
        } else if (dayDate.isAfter(lastDayOfYear)) {
            // 日期在目标年份之后，返回最后一周
            return (long) lastDayOfYear.get(weekFields.weekOfYear());
        } else {
            // 日期在目标年份内，正常计算周数
            return (long) dayDate.get(weekFields.weekOfYear());
        }
    }

    /**
     * 获取指定时间所在月的指定日
     *
     * @param date 时间
     * @param day  天数
     */
    public static Date getMonthDay(Date date, int day) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        day = Math.min(day, localDate.lengthOfMonth());
        day = Math.max(day, 1);
        LocalDate dateDay = localDate.withDayOfMonth(day);
        return DateUtils.toDate(dateDay);
    }

    /**
     * 获取指定时间指定n月后
     */
    public static Date getMonth(Date date, int month) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate months = localDate.plusMonths(month);
        return DateUtils.toDate(months);
    }

    /**
     * 获取指定时间所在月有多少天
     */
    public static int getMonthDayNumber(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().lengthOfMonth();
    }

    /**
     * 获取指定月份的天数，本月返回是第几天
     */
    public static int getMonthDayNumber(String dateStr) {
        return getMonthDayNumber(dateStr, true);
    }

    /**
     * 获取指定时间所在月份的天数
     *
     * @param dateStr   时间
     * @param thisMonth 是否是当前月份,如果是，且为true，则返回今天是本月第几天，否则返回指定月份的天数
     */
    public static int getMonthDayNumber(String dateStr, boolean thisMonth) {
        if (dateStr == null || dateStr.isEmpty()) {
            return 0;
        }

        Date parsedDate = parseDate(dateStr);
        if (parsedDate == null) {
            return 0;
        }
        if (thisMonth) {
            LocalDate today = LocalDate.now();
            // 检查是否是当前月份
            LocalDate localDate = parsedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // 如果是指定日期的年月与当前年月相同
            if (localDate.getYear() == today.getYear() &&
                    localDate.getMonthValue() == today.getMonthValue()) {
                return today.getDayOfMonth(); // 返回今天是本月第几天
            }
        }

        LocalDate localDate = parsedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.lengthOfMonth();
    }

    /**
     * 获取指定时间所在月份的几小时
     *
     * @param dateStr 时间
     * @return 本月已过去的小时数或本月总小时数
     */
    public static int getMonthHourNumber(String dateStr) {
        return getMonthHourNumber(dateStr, true);
    }

    /**
     * 获取指定时间所在月份的小时数
     *
     * @param dateStr   时间
     * @param thisMonth 是否是当前月份,如果是，且为true，则返回今天是本月的地几个小时，否则返回指定月份的总小时数
     * @return 本月已过去的小时数或本月总小时数
     */
    public static int getMonthHourNumber(String dateStr, boolean thisMonth) {
        if (dateStr == null || dateStr.isEmpty()) {
            return 0;
        }

        Date parsedDate = parseDate(dateStr);
        if (parsedDate == null) {
            return 0;
        }

        if (thisMonth) {
            LocalDate today = LocalDate.now();
            // 检查是否是当前月份
            LocalDate localDate = parsedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // 如果是指定日期的年月与当前年月相同
            if (localDate.getYear() == today.getYear() &&
                    localDate.getMonthValue() == today.getMonthValue()) {
                // 返回本月已过去的小时数（前面完整天数*24 + 今天已过去的小时数）
                int daysPassed = today.getDayOfMonth() - 1;
                int hoursToday = LocalTime.now().getHour();
                return daysPassed * 24 + hoursToday;
            }
        }

        LocalDate localDate = parsedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // 返回指定月份的总小时数
        return localDate.lengthOfMonth() * 24;
    }

    public static String getMonthDay(Date date, int day, String format) {
        return parseDateToStr(format, getMonthDay(date, day));
    }

    /**
     * 获取指定月份指定天数的日期
     *
     * @param date  时间
     * @param month 月份
     * @param day   天数
     */
    public static Date getMonthDay(Date date, int month, int day) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //加减月份
        localDate = localDate.plusMonths(month);
        //根据月份获取指定日
        int maxDays = localDate.lengthOfMonth();
        day = Math.min(day, maxDays);
        day = Math.max(day, 1);
        LocalDate targetDate = localDate.withDayOfMonth(day);
        return DateUtils.toDate(targetDate);
    }

    public static String getMonthDay(Date date, int month, int day, String format) {
        return parseDateToStr(format, getMonthDay(date, month, day));
    }


    /**
     * 获取指定时间月份最后一天
     */
    public static Date getMonthLastDay(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate lastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return toDate(lastDay);
    }

    /**
     * 获取指定日期最后一天
     */
    public static String getMonthLastDay(String date) {
        Date parsedDate = parseDate(date);
        if (parsedDate == null) {
            return null;
        }
        Date monthLastDay = getMonthLastDay(parsedDate);
        return parseDateToStr(YYYY_MM_DD, monthLastDay);
    }

    /**
     * 获取指定年份、季度和天数的日期
     *
     * @param year         年份
     * @param quarter      季度 (1-4)
     * @param dayOfQuarter 季度中的第几天
     * @return Date 日期
     */
    public static Date getDateOfQuarter(int year, int quarter, int dayOfQuarter) {
        // 获取季度第一天
        Month firstMonth = Month.of((quarter - 1) * 3 + 1);
        LocalDate quarterFirstDay = LocalDate.of(year, firstMonth, 1);

        // 加上指定天数（需要减1，因为是从第一天开始计算）
        LocalDate targetDate = quarterFirstDay.plusDays(dayOfQuarter - 1);

        // 转换为Date类型
        return toDate(targetDate);
    }

    /**
     * 获取指定日期的季度
     */
    public static int getQuarter(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (localDate.getMonthValue() - 1) / 3 + 1;
    }


    /**
     * 获取指定日期所在季度的第一天
     */
    public static Date getQuarterFirstDay(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int quarter = (localDate.getMonthValue() - 1) / 3 + 1;
        Month firstMonth = Month.of((quarter - 1) * 3 + 1);
        LocalDate quarterFirstDay = LocalDate.of(year, firstMonth, 1);
        return toDate(quarterFirstDay);
    }

    /**
     * 获取下一个季度的第一天
     */
    public static Date getNextQuarter(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int quarter = (month - 1) / 3 + 1;

        if (quarter == 4) {
            // 第四季度的下一个季度是下一年的第一季度
            return toDate(LocalDate.of(year + 1, 1, 1));
        } else {
            // 其他季度直接加3个月
            LocalDate nextQuarter = localDate.plusMonths(3);
            // 调整到季度第一天
            return getQuarterFirstDay(toDate(nextQuarter));
        }
    }

    /**
     * 获取指定日期所在季度天数
     */
    public static int getQuarterDayNumber(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int quarter = (localDate.getMonthValue() - 1) / 3 + 1;

        // 计算季度开始和结束月份
        int startMonth = (quarter - 1) * 3 + 1;
        int endMonth = startMonth + 2;

        // 获取季度开始日期
        LocalDate quarterStart = LocalDate.of(year, startMonth, 1);
        // 获取季度结束日期（下个季度第一天减一天）
        LocalDate quarterEnd = LocalDate.of(year, endMonth, 1).plusMonths(1).minusDays(1);

        return (int) java.time.temporal.ChronoUnit.DAYS.between(quarterStart, quarterEnd) + 1;
    }

    /**
     * 获取指定季度的天数，格式为yyyy-QQ,本季度则返回是本季度第几天
     */
    public static int getQuarterDayNumber(String dateStr) {
        return getQuarterDayNumber(dateStr, true);
    }

    /**
     * 获取指定季度的天数，返回int，并且判断是否是本季度，如果是本季度，且thisQuarter = true，则返回这是本季度第几天
     *
     * @param dateStr     季度字符串 "yyyy-QQ"
     * @param thisQuarter 是否是本季度
     */
    public static int getQuarterDayNumber(String dateStr, boolean thisQuarter) {
        if (dateStr == null || dateStr.isEmpty()) {
            return 0;
        }

        // 手动解析 "yyyy-Qq" 格式的季度字符串
        String[] parts = dateStr.split("-Q");
        if (parts.length != 2) {
            return 0;
        }

        int year = Integer.parseInt(parts[0]);
        int quarter = Integer.parseInt(parts[1]);

        // 验证季度值是否有效
        if (quarter < 1 || quarter > 4) {
            return 0;
        }

        if (thisQuarter) {
            LocalDate today = LocalDate.now();
            int currentYear = today.getYear();
            int currentQuarter = (today.getMonthValue() - 1) / 3 + 1;

            // 如果指定的年份和季度是当前季度
            if (year == currentYear && quarter == currentQuarter) {
                // 计算今天是本季度的第几天
                LocalDate quarterFirstDay = getQuarterFirstDay(toDate(today)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                return (int) java.time.temporal.ChronoUnit.DAYS.between(quarterFirstDay, today) + 1;
            }
        }

        // 计算季度开始和结束月份
        int startMonth = (quarter - 1) * 3 + 1;
        int endMonth = startMonth + 2;

        // 获取季度开始日期
        LocalDate quarterStart = LocalDate.of(year, startMonth, 1);
        // 获取季度结束日期（下个季度第一天减一天）
        LocalDate quarterEnd = LocalDate.of(year, endMonth, 1).plusMonths(1).minusDays(1);

        return (int) java.time.temporal.ChronoUnit.DAYS.between(quarterStart, quarterEnd) + 1;
    }

    /**
     * 获取指定季度的小时数量，返回int小时数量
     *
     * @param dateStr 季度字符串 "yyyy-QQ"
     * @return 季度总小时数或本季度已过去的小时数
     */
    public static int getQuarterHourNumber(String dateStr) {
        return getQuarterHourNumber(dateStr, true);
    }

    /**
     * 获取指定季度的小时数量，返回int，并且判断是否是本季度，如果是本季度，且thisQuarter = true，则返回这是本季度的几个小时
     *
     * @param dateStr     季度字符串 "yyyy-QQ"
     * @param thisQuarter 是否是本季度
     * @return 季度总小时数或本季度已过去的小时数
     */
    public static int getQuarterHourNumber(String dateStr, boolean thisQuarter) {
        if (dateStr == null || dateStr.isEmpty()) {
            return 0;
        }

        // 复用现有的解析逻辑
        String[] parts = dateStr.split("-Q");
        if (parts.length != 2) {
            return 0;
        }

        int year = Integer.parseInt(parts[0]);
        int quarter = Integer.parseInt(parts[1]);

        // 验证季度值是否有效
        if (quarter < 1 || quarter > 4) {
            return 0;
        }

        if (thisQuarter) {
            LocalDate today = LocalDate.now();
            int currentYear = today.getYear();
            int currentQuarter = (today.getMonthValue() - 1) / 3 + 1;

            // 如果指定的年份和季度是当前季度
            if (year == currentYear && quarter == currentQuarter) {
                // 计算今天是本季度的第几天
                LocalDate quarterFirstDay = getQuarterFirstDay(toDate(today)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long daysPassed = java.time.temporal.ChronoUnit.DAYS.between(quarterFirstDay, today);

                // 获取今天的小时数（当前时间）
                LocalTime nowTime = LocalTime.now();
                int hoursToday = nowTime.getHour();

                // 返回已过去的总小时数（整天数*24 + 今天已过的小时数）
                return (int) (daysPassed * 24 + hoursToday);
            }
        }

        // 计算季度总天数并转换为小时数
        int startMonth = (quarter - 1) * 3 + 1;
        int endMonth = startMonth + 2;

        LocalDate quarterStart = LocalDate.of(year, startMonth, 1);
        LocalDate quarterEnd = LocalDate.of(year, endMonth, 1).plusMonths(1).minusDays(1);

        long totalDays = java.time.temporal.ChronoUnit.DAYS.between(quarterStart, quarterEnd) + 1;
        return (int) (totalDays * 24);
    }


    /**
     * 获取指定年份的指定日
     *
     * @param date
     * @param year
     * @param day
     * @return String
     **/
    public static Date getYearDay(Date date, int year, int day) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //加减年份
        localDate = localDate.plusYears(year);
        //根据年份获取指定日
        day = Math.min(day, localDate.lengthOfYear());
        day = Math.max(day, 1);
        LocalDate temporalAccessor = localDate.withDayOfYear(day);
        return toDate(temporalAccessor);
    }

    public static String getYearDay(Date date, int year, int day, String format) {
        return parseDateToStr(format, getYearDay(date, year, day));
    }

    /**
     * 获取指定时间所在年份的指定日
     *
     * @param date 时间
     * @param day  天数
     */
    public static Date getYearDay(Date date, int day) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        day = Math.min(day, localDate.lengthOfYear());
        day = Math.max(day, 1);
        LocalDate dateDay = localDate.withDayOfYear(day);
        return toDate(dateDay);
    }

    public static String getYearDay(Date date, int day, String format) {
        return parseDateToStr(format, getYearDay(date, day));
    }

    /**
     * 获取指定日期所在年天数
     */
    public static int getYearDayNumber(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.lengthOfYear();
    }

    /**
     * 获取指定日期所在年天数-格式为YYYY，本年则返回今天是第几天
     */
    public static int getYearDayNumber(String dateStr) {
        return getYearDayNumber(dateStr, true);
    }

    /**
     * 获取指定时间的年份天数，并且判断是否是今年，如果是今年，且thisYear为true，则返回今天是第几天
     *
     * @param dateStr  时间格式
     * @param thisYear 是否要计算当前时间，如果是，并且时间是今年的，则返回今天今年是第几天
     */
    public static int getYearDayNumber(String dateStr, boolean thisYear) {
        if (dateStr == null || dateStr.isEmpty()) {
            return 0;
        }

        int year = Integer.parseInt(dateStr);

        if (thisYear) {
            LocalDate today = LocalDate.now();
            // 如果指定年份是今年，则返回今年到今天的天数
            if (year == today.getYear()) {
                return today.getDayOfYear();
            }
        }
        // 返回指定年份的总天数
        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
        return firstDayOfYear.lengthOfYear();
    }

    /**
     * 获取指定时间的年份小时数，并且判断是否是今年，如果是今年，且thisYear为true，则返回今年第几个小时
     *
     * @param dateStr 时间格式
     * @return 年份总小时数或今年已过去的小时数
     */
    public static int getYearHourNumber(String dateStr) {
        return getYearHourNumber(dateStr, true);
    }

    /**
     * 获取指定时间的年份小时数，并且判断是否是今年，如果是今年，且thisYear为true，则返回今年第几个小时
     *
     * @param dateStr  时间格式
     * @param thisYear 是否要计算当前时间，如果是，并且时间是今年的，则返回今天今年是第几个小时
     * @return 年份总小时数或今年已过去的小时数
     */
    public static int getYearHourNumber(String dateStr, boolean thisYear) {
        if (dateStr == null || dateStr.isEmpty()) {
            return 0;
        }

        int year = Integer.parseInt(dateStr);

        if (thisYear) {
            LocalDate today = LocalDate.now();
            // 如果指定年份是今年，则返回今年到当前时间的小时数
            if (year == today.getYear()) {
                // 计算今年已过去的天数
                int daysPassed = today.getDayOfYear() - 1;
                // 获取今天的小时数
                int hoursToday = LocalTime.now().getHour();
                // 返回已过去的总小时数
                return daysPassed * 24 + hoursToday;
            }
        }

        // 返回指定年份的总小时数
        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
        return firstDayOfYear.lengthOfYear() * 24;
    }


    /**
     * 获取指定时间指定n天后
     */
    public static Date getDay(Date date, int day) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate days = localDate.plusDays(day);
        return DateUtils.toDate(days);
    }

    /**
     * 获取指定时间指定n年后
     */
    public static Date getYear(Date date, int year) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate years = localDate.plusYears(year);
        return DateUtils.toDate(years);
    }

    public static String getDay(Date date, int day, String format) {
        return parseDateToStr(format, getDay(date, day));
    }


    /**
     * 获取指定时间范围的所有日期-日
     */
    public static List<String> getDateRangesDays(String startDateStr, String endDateStr) {
        Date startDate = parseDate(startDateStr);
        Date endDate = parseDate(endDateStr);
        // 判断时间范围
        if (startDate != null && endDate != null) {
            List<String> dateList = new ArrayList<>();
            // 开始时间小于等于结束时间
            while (startDate.getTime() <= endDate.getTime()) {
                dateList.add(parseDateToStr(YYYY_MM_DD, startDate));
                startDate = DateUtils.getDay(startDate, 1);
            }
            return dateList;
        }
        return null;
    }

    /**
     * 获取指定时间范围所有日期-月
     */
    public static List<String> getDateRangesMonths(String startDateStr, String endDateStr) {
        Date startDate = parseDate(startDateStr);
        Date endDate = parseDate(endDateStr);
        if (startDate == null || endDate == null) {
            return null;
        }
        List<String> dateList = new ArrayList<>();
        // 获取指定时间范围所有日期
        while (startDate.getTime() <= endDate.getTime()) {
            dateList.add(parseDateToStr(YYYY_MM, startDate));
            startDate = DateUtils.getMonth(startDate, 1);
        }
        return dateList;
    }

    /**
     * 获取指定时间范围所有日期-季
     */
    public static List<String> getDateRangesQuarters(String startDateStr, String endDateStr) {
        Date startDate = parseDate(startDateStr);
        Date endDate = parseDate(endDateStr);
        if (startDate == null || endDate == null) {
            return null;
        }
        List<String> dateList = new ArrayList<>();
        while (startDate.getTime() <= endDate.getTime()) {
            // 手动格式化季度字符串，避免使用SimpleDateFormat处理QQ格式
            dateList.add(formatQuarterDate(startDate));
            startDate = DateUtils.getNextQuarter(startDate);
        }
        return dateList;
    }

    /**
     * 自定义季度日期格式化方法
     */
    public static String formatQuarterDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int quarter = (localDate.getMonthValue() - 1) / 3 + 1;
        return year + "-Q" + quarter;
    }

    /**
     * 获取指定时间范围所有日期-年
     */
    public static List<String> getDateRangesYears(String startDateStr, String endDateStr) {
        Date startDate = parseDate(startDateStr);
        Date endDate = parseDate(endDateStr);
        if (startDate == null || endDate == null) {
            return null;
        }
        List<String> dateList = new ArrayList<>();

        // 获取起始和结束年份
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int startYear = startLocalDate.getYear();
        int endYear = endLocalDate.getYear();

        // 遍历年份范围
        for (int year = startYear; year <= endYear; year++) {
            dateList.add(String.valueOf(year));
        }

        return dateList;
    }

    public static void main(String[] args) {
        String startDate = "2023-5-01";
        String endDate = "2023-05-01";
        List<String> dateRangesMonths = getDateRangesMonths(startDate, endDate);
        System.out.println(dateRangesMonths);
        List<String> dateRangesQuarters = getDateRangesQuarters(startDate, endDate);
        System.out.println(dateRangesQuarters);
        List<String> dateRangesYears = getDateRangesYears(startDate, endDate);
        System.out.println(dateRangesYears);
        List<String> dateRangesDays = getDateRangesDays(startDate, endDate);
        System.out.println(dateRangesDays);
    }
}
