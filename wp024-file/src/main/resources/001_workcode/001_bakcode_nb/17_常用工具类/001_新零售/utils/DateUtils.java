package com.zrytech.framework.newshop.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {

    public static Date getWorkDay(Date startDate, int workDay) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(startDate);
        for (int i = 0; i < workDay; i++) {
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            if (Calendar.SATURDAY == c1.get(Calendar.SATURDAY) || Calendar.SUNDAY == c1.get(Calendar.SUNDAY)) {
                workDay = workDay + 1;
                c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
                continue;
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = c1.getTime();
        return d;
    }


    /**
     * 如果啊 a>b 返回false
     * 如果 a<b 返回true
     *
     * @param a
     * @param b
     * @return
     * @throws ParseException
     */
    public static boolean compare(Date a, Date b) throws ParseException {
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //将字符串形式的时间转化为Date类型的时间
        //Date类的一个方法，如果a早于b返回true，否则返回false
        if (a.before(b))
            return true;
        else
            return false;
		/*
		 * 如果你不喜欢用上面这个太流氓的方法，也可以根据将Date转换成毫秒
		if(a.getTime()-b.getTime()<0)
			return true;
		else
			return false;
		*/
    }


    /**
     * date 转换 string
     *
     * @param date
     * @return
     */
    public static String getStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    //获取当天时间
    public static String getCurrent() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date());
        return date;
    }


    //本周的第一天
    public static String getWeekStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00:00";
    }

    //本周的最后一天
    public static String getWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, 1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
    }

    //本月第一天
    public static String getMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00:00";
    }

    //本月的最后一天
    public static String getMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
    }


    public static Date addDay1(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//设置起时间
        cal.add(Calendar.DATE, day);//增加一天
        return cal.getTime();
    }

    public static void main(String[] args) {
        Date date1 = new Date();
        //  System.out.println(date1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String time = "2019-08-21 00:00:00";
            System.out.println(compare(new Date(), sdf.parse(time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //  Date date = addDay1(date1, 1);
        //System.out.println(date);
    }
}
