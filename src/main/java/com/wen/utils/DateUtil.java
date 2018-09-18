package com.wen.utils;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;





/**
 * @author wencun
 * @date 2018年5月5日 上午11:00:23
 * @version 1.0
 * @since JDK 1.8
 */
public class DateUtil {
    
    /** 存放不同的日期模板格式的sdf的Map */
    private static Map<String, ThreadLocal<SimpleDateFormat>> simpleDateFormats = 
            new ConcurrentHashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static  DateFormat sdf;
    private static  final String default_patter = "yyyy-MM-dd";

    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT2 = "yyyyMMdd";
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_LONG2 = "yyyyMMddHHmmss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     *  精确到秒的完整时间 如：yyyy-MM-dd E HH:mm:ss
     */
    public static String FORMAT_LONG_WEEK_CN = "yyyy-MM-dd E HH:mm:ss";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";
    /** 某天开始时分秒字符串常量 00:00:00 */
    public static final String DAY_BEGIN_STRING_HHMMSS = " 00:00:00";
    /** 某天结束时分秒字符串常量 23:59:59 */
    public static final String DAY_END_STRING_HHMMSS = " 23:59:59";
       
  
    /**
     * 
     * 获取一天的起始时间
     */
    public static Date getDayOfStartTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 
     * 获取一天的结束时间
     * 
     
     */
    public static Date getDayOfEndTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 
     * 获取当前日期的前一天
     * 
     * 
     */
    public static Date getPreDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 
     * 得到某年某月的第一天
     * 
     * @author bo.mo
     * @date: 2017年7月4日 下午5:15:50
     * @version 1.0
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MINUTE,0);
        return cal.getTime();
    }
    
    /**
     * 得到某年某月的最后一天
     * 
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);

        return cal.getTime();

    }

    /************* 获取某年第一天日期 ********************/
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /************* 获取某年最后一天日期 ********************/
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
     * 
     * 获取当前年份. <br/> 
     * 
     * @return
     */
    public static int getNowYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }
    
    /**
     * 
     * 获取当前月份. <br/> 
     * 
     * @return
     */
    public static int getNowMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }
    
    /**
     * 获取日期的Integer类型 
     * @author wencun
     * @date: 2018年3月22日 下午2:54:14
     * @return
     */
    public static Integer getIntegerDay(String strDate) {
        if(strDate.length()!=8||!NumberUtils.isNumber(strDate)) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
        Date date;
        String yearStr="";
        String monthStr="";
        String dayStr="";
        try {
            date = sdf.parse(strDate);       
            Calendar calendar = Calendar.getInstance();//日历对象
            calendar.setTime(date);//设置当前日期
            yearStr = calendar.get(Calendar.YEAR)+"";//获取年份
            int month = calendar.get(Calendar.MONTH) + 1;//获取月份
            monthStr = month < 10 ? "0" + month : month + "";
            int day = calendar.get(Calendar.DATE);//获取日
            dayStr = day < 10 ? "0" + day : day + "";                    
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getYeayMonthDayValue(yearStr, monthStr, dayStr);
    }

    private static Integer getYeayMonthDayValue(String year, String month, String day) {
        StringBuffer sb = new StringBuffer(6);
        sb.append(year).append(month).append(day);
        return Integer.valueOf(sb.toString());
    } 
    
    
    public static Integer getIntegerDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
        String format = sdf.format(date);
        Integer integerDay = getIntegerDay(format);
        return integerDay;        
    }
    
    public static String getFormatTime(Date date,String pattern) {
        if(null==date) {
            return "";
        }
        sdf=new SimpleDateFormat(pattern);
        String format = sdf.format(date);
        return format;
        
    }
    
    
    @Test
    public void testFormat() throws Exception {
        String formatLongWeekTime = getFormatTime(new Date(),FORMAT_LONG_WEEK_CN);
        System.out.println(formatLongWeekTime);
    }
}
