package com.sophia.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 工具类：日期、字符串标准工具
 * @author 
 *
 */
public class DateUtil {
    public static final String FORMAT_0 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_1 = "yyyy-MM-dd";
    public static final String FORMAT_2 = "HH:mm:ss";
    public static final String FORMAT_3 = "yyyy-MM-dd HH";
    public static final String FORMAT_4 = "yyyy-MM-dd HH:mm";    
    public static final String FORMAT_5 = "y-M-d H:m S";    
    /**
	 * 获取一天的最开始  0点0时0分0秒 0毫秒
	 * @param date
	 * @return
	 */
	public static Date getDateStart(Date date){
		Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(date.getTime());
    	calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
    	return calendar.getTime();
	}
	
	/**
	 * 获取一条最23点59分59秒 999毫秒
	 * @param date
	 * @return
	 */
	public static Date getDateEnd(Date date){
		Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(date.getTime());
    	calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),0, 0, 0);
    	
    	calendar.add(Calendar.DAY_OF_YEAR, 1);
    	calendar.add(Calendar.SECOND, -1);
		calendar.set(Calendar.MILLISECOND, 999);
    	return calendar.getTime();
	}
    /**
     * 检查时间  是否小于1900.1.1
     * @param date
     * @return
     */
    public static Date checkKebaoDate(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(1900, 0, 1, 0, 0,0);
    	if(date.getTime() < calendar.getTimeInMillis()){
    		return new Date();
    	}
    	return date;
    }
    
    public static Date reverse2Date(String date) {
        SimpleDateFormat simple = null;
        switch (date.trim().length()) {
        case 19:// 日期+时间
            simple = new SimpleDateFormat(FORMAT_0);
            break;
        case 10:// 仅日期
            simple = new SimpleDateFormat(FORMAT_1);
            break;
        case 8:// 仅时间
            simple = new SimpleDateFormat(FORMAT_2);
            break;
        case 13://到小时
        	simple = new SimpleDateFormat(FORMAT_3);
        default:
            break;
        }
        try {
            return simple.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Date reverse2Date(String date,String format) {
        SimpleDateFormat simple = new SimpleDateFormat(format);
        try {
            return simple.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 得到一年的周六周日集合  格式 yyyy-MM-dd
     * @param year
     * @author hyz.persist 2013-5-18
     * @return
     */
    public static List<String> getWeekendOfYear(int year){
    	Calendar calendar = Calendar.getInstance();
		calendar.set(year, 0, 1);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		
		List<String> list = new ArrayList<String>();
		if(day == 1){//周日  如果是周日 需要先加上
			list.add(getCalendarToString(calendar));
		}
		//算出 这一年的第一个周六
		calendar.add(Calendar.DAY_OF_YEAR, differencing(calendar.get(Calendar.DAY_OF_WEEK)));
		
		while (true) {
			if(calendar.get(Calendar.YEAR) != year){
				break;
			}
			list.add(getCalendarToString(calendar));
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			if(calendar.get(Calendar.YEAR) != year){
				break;
			}
			list.add(getCalendarToString(calendar));
			calendar.add(Calendar.DAY_OF_YEAR, 6);
		}
		return list;
    }
    /**
     * 获取最大的日期
     * @param dates
     * @return
     */
    public static Date getMaxDate(Date...dates){
    	if(dates.length == 0){
    		return null;
    	}
    	Arrays.sort(dates, new Comparator<Date>() {
			@Override
			public int compare(Date o1, Date o2) {
				return -o1.compareTo(o2);
			}
		});
    	return dates[0];
    }
    /**
     * 获取最小日期
     * @param dates
     * @return
     */
    public static Date getMinDate(Date...dates){
    	if(dates.length == 0){
    		return null;
    	}
    	Arrays.sort(dates, new Comparator<Date>() {
			@Override
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
    	return dates[0];
    }
    
    /**
     * 将 Calendar 转换成 yyyy-MM-dd格式 
     * @param calendar
     * @author hyz.persist 2013-5-18
     * @return
     */
    public static String getCalendarToString(Calendar calendar){
    	return calendar.get(Calendar.YEAR)+"-"+(1+calendar.get(Calendar.MONTH))+"-"+calendar.get(Calendar.DATE);
    }
    /**
     * 求差值  推算与周六相差的天数  
     * 0 1 2 3 4 5 6 分别表示 周六 周日 周一 周二 周三 周四 周五
     * 
     * 0   0   差0天
     * 0   1   差6天
     * 0   2   差5天
     * 综合 :0差0天 其他 差7-n天
     * @param day
     * @author hyz.persist 2013-5-18
     * @return
     */
    public static int differencing(int day){
    	if(day == 0){
    		return 0;
    	}
    	return 7 - day;
    }
    
    /**
     * 获取年月日
     * @param date
     * @author hyz.persist 2013-5-18	
     * @return
     */
    public static int[] getYearMonthDay(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	int[] yearMonthDay = new int[]{
    			calendar.get(Calendar.YEAR),
    			calendar.get(Calendar.MONTH),
    			calendar.get(Calendar.DATE)
		};
    	return yearMonthDay;
    }
    /**
     * 获取年
     * @param date
     * @author hyz.persist 2013-5-18
     * @return
     */
    public static int getYear(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	return calendar.get(Calendar.YEAR);
    }
    /**
     * 获取月
     * @author hyz.persist 2013-5-18
     * @param date
     * @return
     */
    public static int getMonth(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	return calendar.get(Calendar.MONTH);
    }
    /**
     * 获取日
     * @param dat
     * @author hyz.persist 2013-5-18
     * @return
     */
    public static int getDay(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	return calendar.get(Calendar.DATE);
    }
    
    /**
     * 根据日期 获取当天 的  0点到第二天0点
     * @param date
     * @return [0] 是当天 [1]是下一天
     * 
     */
    public static Date[] getFromToTime(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(date.getTime());
    	calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),0, 0, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	Date[] from_to = new Date[2];
    	from_to[0] = calendar.getTime();
    	calendar.add(Calendar.DAY_OF_YEAR, 1);
    	calendar.set(Calendar.MILLISECOND, 0);
    	from_to[1] = calendar.getTime();
    	return from_to;
    }
    /**
     * 按照格式比较日期 只比较年月日
     * @param date1
     * @param date2
     * @return
     */
    public static boolean equalDate(Date date1,Date date2){
    	int[] d1 = getYearMonthDay(date1);
    	int[] d2 = getYearMonthDay(date2);
    	return d1[0] == d2[0] && d1[1] == d2[1] && d1[2] == d2[2];
    }
    
    public static int compareDate(Date date1,Date date2){
    	Calendar calendar = Calendar.getInstance();
    	int[] yearMonthDay = getYearMonthDay(date1);
    	calendar.set(yearMonthDay[0], yearMonthDay[1], yearMonthDay[2], 0, 0, 0);
    	date1 = calendar.getTime();
    	yearMonthDay = getYearMonthDay(date2);
    	calendar.set(yearMonthDay[0], yearMonthDay[1], yearMonthDay[2], 0, 0, 0);
    	date2 = calendar.getTime();
    	return (int) ((date1.getTime() - date2.getTime())/(1000*60*60*24));
    }

    /**
     * 如果参数长度不为为0,则取第一个参数进行格式化，<br>
     * 否则取当前日期 如:2010-4-15
     *
     * @param Date
     *            ... 可变参数
     * @return java.lang.String
     * **/
    public static String toDate(Date... date) {
        SimpleDateFormat simple = new SimpleDateFormat(FORMAT_1);
        if (date.length > 0) {
            return simple.format(date[0]);
        }
        return simple.format(new Date());
    }
    /**
     * 如果参数长度不为为0,则取第一个参数进行格式化，<br>
     * 否则取当前日期时间，精确到秒<br>
     * 如:9:36:38
     *
     * @param Date
     *            ... 可变参数
     * @return java.lang.String
     * **/
    public static String toTime(Date... date) {
        SimpleDateFormat simple = new SimpleDateFormat(FORMAT_2);
        if (date.length > 0) {
            return simple.format(date[0]);
        }
        return simple.format(new Date());
    }
    /**

     * 如果参数长度不为为0,则取第一个参数进行格式化，<br>

     * 否则取当前日期时间，精确到秒 如:2010-4-15 9:36:38

     * @param Date

     *            ... 可变参数
     * **/

    public static String toFull(Date date) {

        SimpleDateFormat simple = new SimpleDateFormat(FORMAT_0);

 /*       if (date.length > 0) {

            return simple.format(date[0]);

        }*/

        return simple.format(date);

    }   
    //java.util.Date ==> java.sql.Date

    public static java.sql.Timestamp getSQLTimestamp(Date date){
    	return new java.sql.Timestamp(date.getTime()); 
    }
    public static java.sql.Date getSQLDate(Date date){
    	return new java.sql.Date(date.getTime()); 
    }
    
	public static String getDate(){
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  Date dd = new Date();
		  return ft.format(dd);
	}
	public static long getQuot(String time1, String time2){
		  long quot = 0;
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  try {
		   Date date1 = ft.parse( time1 );
		   Date date2 = ft.parse( time2 );
		   quot = date1.getTime() - date2.getTime();
		   quot = quot / 1000 / 60 / 60 / 24;
		  } catch (ParseException e) {
		   e.printStackTrace();
		  }
		  return quot;
     }
    
	public static String toDate(Date date,String format){
		SimpleDateFormat simple = new SimpleDateFormat(format,Locale.CHINESE);
		return simple.format(date);
	}
	/**
	 * 页面时间空间处理时间格式(将格式为:yyyy-M-dd/yyyy-MM-d转换成yyyy-MM-dd)
	 * @return
	 */
	public static  String  getSelectTime(String time){
	    String[] times = new  String[10];
	    times = time.split("-");
	    for(int i =1;i<times.length;i++){
	    	if(times[i].length()==1){
	    		times[i] = "0"+times[i];
	    	}
	    }
	    StringBuffer sb = new StringBuffer();
	    for(int i = 0; i < times.length; i++){
	    	if(i==times.length-1){
	    		sb.append(times[i]);
	    	}else{
	    		 sb. append(times[i]+"-");
	    	}
	    }
	    String s = sb.toString();
		return s;
	}
	/**
	 * 得到一个日期累加几天后的日期
	 * @param data
	 * @param day
	 * @return
	 */
	public static Date addDay(Date data,int day){
		return new Date(data.getTime()+(24*60*60*1000)*day);
	}
	/**
	 * 得到一个日期减少几天后的日期
	 * @param data
	 * @param day
	 * @return
	 */
	public static Date reduceDay(Date data,int day){
		return new Date(data.getTime()-(24*60*60*1000)*day);
	}
    public static void main(String[] args){
    	String demo = "2012-02-14 14:10:30";
//    	java.sql.Timestamp tm = null;
//    	tm = new java.sql.Timestamp(reverse2Date(demo).getTime());
    	Date date = null;
    	date = reverse2Date(demo);
    	System.out.println(date);
    	System.out.println(reduceDay(date, 15));
    	//System.out.println(getSelectTime("2012-7-26"));
    	
    	//System.out.println(toDate(new Date(), FORMAT_5));
    }
    
}
