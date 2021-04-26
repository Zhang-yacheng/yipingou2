package cn.yanfa.common;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * 日期处理
 */
public class DateUtil {

    /**
     * 年份格式(yyyy)
     */
    public final static String YEAR_PATTERN = "yyyy";

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式(yyyy/MM/dd)
     */
    public final static String DATE_PATTERN_SLASH = "yyyy/MM/dd";

    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式(yyyy年M月dd日 ah:mm:ss) 代码生成器使用
     */
    public final static String DATE_TIME_CHN_PATTERN = "yyyy年M月dd日 ah:mm:ss";

    /**
     * Date转为时间格式(yyyy-MM-dd)的字符串
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * Date根据格式字符串转为字符串
     *
     * @param date
     * @param pattern
     * @return 时间字符串
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 判断是不是8(参数传入)点
     */
    public static boolean isHourByPar(Date date, Integer hour) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        if (cal1.get(Calendar.HOUR_OF_DAY) == hour) {
            return true;
        }
        return false;
    }


    /**
     * 判断两个时间是否是同一小时
     */
    public static boolean isSameHour(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        //同一年
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        //同一个月
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        //同一天
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
        //同一小时
        boolean isSameHour = isSameDate && cal1.get(Calendar.HOUR_OF_DAY) == cal2
                .get(Calendar.HOUR_OF_DAY);
        return isSameHour;
    }


    /**
     * 给时间增加x小时，并将分钟数设置为0
     *
     * @param date
     * @param x
     * @return
     */
    public static Date addDateHour(Date date, int x) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, x);// 24小时制
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date format2(Date date) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
            String format = df.format(date);
            Date parse = null;
            try {
                parse = df.parse(format);
                return parse;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 当前日期加天数
     *
     * @param startDate
     * @param days
     * @return
     */
    public static Date addDays(Date startDate, int days) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(startDate);
        cl.add(Calendar.DATE, days);
        return cl.getTime();
    }

    /**
     * 当前时间加分钟
     *
     * @param startDate
     * @param days
     * @return
     */
    public static Date addMinutes(Date startDate, int days) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(startDate);
        cl.add(Calendar.MINUTE, days);
        return cl.getTime();
    }

    /**
     * 当前当前系统年份
     *
     * @return
     */
    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_PATTERN);
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 当前当前系统月份
     *
     * @return
     */
    public static String getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return StringUtils.leftPad(String.valueOf(cal.get(Calendar.MONTH) + 1), 2, '0');
    }

    /**
     * 当前当前系统日期天
     *
     * @return
     */
    public static String getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        return StringUtils.leftPad(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2, '0');
    }

    /**
     * 当前当前系统时
     *
     * @return
     */
    public static String getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        return StringUtils.leftPad(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)), 2, '0');
    }

    /**
     * 当前当前系统分钟
     *
     * @return
     */
    public static String getCurrentMinute() {
        Calendar cal = Calendar.getInstance();
        return StringUtils.leftPad(String.valueOf(cal.get(Calendar.MINUTE)), 2, '0');
    }

    /**
     * 当前当前系统秒数
     *
     * @return
     */
    public static String getCurrentMillisecond() {
        Calendar cal = Calendar.getInstance();
        return StringUtils.leftPad(String.valueOf(cal.get(Calendar.SECOND)), 2, '0');
    }

    /**
     * 获取前N天到今天的localdate集合
     *
     * @param days
     * @return
     */
    public static List<LocalDate> getForeDateList(int days) {
        List<LocalDate> dateList = new ArrayList<>();
        for (int i = days - 1; i >= 0; i--) {
            LocalDate localDate = LocalDate.now().plusDays(0 - i);
            dateList.add(localDate);
        }
        return dateList;
    }

    /**
     * 时间格式(yyyy/MM/dd)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getdate(String date) throws ParseException {
        if (date == null || "".equals(date))
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_SLASH);
        return sdf.parse(date);
    }

    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getDate(String date) throws ParseException {
        if (date == null || "".equals(date))
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        return sdf.parse(date);
    }

    public static Date dealDateFormat(String oldDate) {
        Date date1 = null;
        DateFormat df2 = null;
        try {
            oldDate = oldDate.replace("Z", " UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return getDate(df2.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Date getDate(String date, int type) {
        try {
            if (date == null || "".equals(date))
                return null;
            StringBuffer stringBuffer = new StringBuffer(date);
            date = stringBuffer.insert(4, '-').insert(7, '-').insert(10, ' ').insert(13, ":").insert(16, ":").toString();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
            return sdf.parse(date);
        } catch (Exception e) {
            return new Date();
        }
    }

    /**
     * 将时间戳转成date
     *
     * @param date
     * @return
     */
    public static Date getDate(Long date) {
        if (date == null) {
            return null;
        }
        Date d = new Date(date);
        return d;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getCurrentDate() {

        Date d = new Date();

        return d;
    }

    /**
     * 跟当前日期之间差几天
     *
     * @return
     */
    public static int subductionDays(Date dt) {

        int days = (int) ((format2(getCurrentDate()).getTime() - format2(dt).getTime()) / (1000 * 3600 * 24));

        return days;
    }

    public static void main(String args[]) throws ParseException {

        // double d = -9.033123456;
        double d = 5.10529173902359E9;
//		System.out.println("ok---" + addMinutes(new Date(), 3));
//
//		System.out.println(DateUtil.getCurrentDate());
//		if (DateUtil.getDate("2020-08-07 12:30:02").compareTo(DateUtil.getCurrentDate()) > 0) {
//
//			System.out.println("ok");
//		}

    }

    public static BigDecimal formatNum(String StrBd) {

        BigDecimal bd = new BigDecimal(StrBd);
        bd = bd.setScale(4, BigDecimal.ROUND_HALF_UP);

        return bd;
    }

    /**
     * 获取传入时间的零点
     *
     * @return
     */
    public static Date getCurentStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    /**
     * 获取传入时间的23:59:59
     *
     * @return
     */
    public static Date getCurentEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date zero = calendar.getTime();
        return zero;
    }

    /**
     * 获取现在的时间 格式没有—和冒号 yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentDateNoSymbol() {
        String gettime = DateUtil.getCurrentYear() + DateUtil.getCurrentMonth() + DateUtil.getCurrentDay()
                + DateUtil.getCurrentHour() + DateUtil.getCurrentMinute() + DateUtil.getCurrentMillisecond();
        return gettime;
    }

    /**
     * 规范时间  2018-08-02T08:52:32.449Z或1533199952449
     */
    public static Date formatData(String date) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if (date.indexOf("T") > 0) {
            //return dealDateFormat1(date);
            return format.parse(date);
        } else {
            return getDate(Long.parseLong(date));
        }
    }

    public static Date dealDateFormat1(String dateStr) {
        Date date = null;
        try {
            dateStr = dateStr.replace("Z", " UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            date = df.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
