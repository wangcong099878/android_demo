package the.one.base.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateUtil {


    private SimpleDateFormat sdf;

    @SuppressLint("SimpleDateFormat")
    public DateUtil() {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    //获取当前日期
    public String getToday() {
        Date d = new Date();
        String date = sdf.format(d);
        return date;
    }

    //截取本月
    public int getCurrentMonth() {
        Date d = new Date();
        String t = sdf.format(d);
        String[] strs = t.split("-");
        int m = Integer.valueOf(strs[1]);
        return m;
    }

    //截取本年
    public static int getCurrentYear() {
        Date d = new Date();
        int y = d.getYear();
        return y;
    }

    public int getCurrentDay() {
        Date d = new Date();
        String t = sdf.format(d);
        String[] strs = t.split("-");
        int m = Integer.valueOf(strs[2]);
        int day = Integer.valueOf(strs[2]);
        return day;
    }

    //获取昨天的日期
    public String getDateOfYesterday() {
        Calendar c = Calendar.getInstance();
        long t = c.getTimeInMillis();
        long l = t - 24 * 3600 * 1000;
        Date d = new Date(l);
        String s = sdf.format(d);

        return s;
    }

    //获取上个月的第一天
    public String getFirstDayOfLastMonth() {
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1); //set the date to be 1
        lastDate.add(Calendar.MONTH, -1);//reduce a month to be last month
//		lastDate.add(Calendar.DATE,-1);//reduce one day to be the first day of last month

        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获取上个月的最后一天
    public String getLastDayOfLastMonth() {
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//
        lastDate.add(Calendar.MONTH, -1);//
        lastDate.roll(Calendar.DATE, -1);//
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获取本月第一天
    public String getFirstDayOfThisMonth() {
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//
//		lastDate.add(Calendar.MONTH,-1);//
//		lastDate.add(Calendar.DATE,-1);//

        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获取本月最后一天
    public String getLastDayOfThisMonth() {
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//
        lastDate.add(Calendar.MONTH, 1);//
        lastDate.add(Calendar.DATE, -1);//

        str = sdf.format(lastDate.getTime());
        return str;
    }

    //判断闰年
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        }
        return false;
    }

    //获取传入的时间字符串的年
    public int getYear(String s) {
        try {
            Date date = sdf.parse(s);
            return date.getYear();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public int getMonth(String s) {
        String[] strs = s.split("-");
        return Integer.valueOf(strs[1]);
    }

    public int getDay(String s) {

        String[] strs = s.split("-");
        Log.e("LOG", strs[2]);

        return Integer.valueOf(strs[2]);
    }

    /**
     * 获取字符串中的小时
     *
     * @param s 字符串
     * @return 小时（24小时制）
     */
    public int getHours(String s) {
        Log.e("LOG", s);
        String[] strs = s.split(":");
        return Integer.valueOf(strs[0]);
    }


    /**
     * 获得当前的小时+分钟
     *
     * @return
     */
    public String getStartTIme() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        //当前的时间：小时+分钟的格式
        String nowTime = sdf.format(d);
        return nowTime;
    }

    /**
     * 获取下一个小时的时间和分钟
     *
     * @return
     */
    public String getEndTime() {

        String endTime;
        String endMinute;

        Calendar now = Calendar.getInstance();
        Integer hour = now.get(Calendar.HOUR_OF_DAY);
        Integer minute = now.get(Calendar.MINUTE);
        Log.e("nextHour", "+++=============================+++  + hour : " + hour);
        Integer nextHour = hour + 1;

        //对时间做的处理
        if (hour != null && hour.intValue() == 23) {
            endTime = "00";
        } else if (hour != null && hour.intValue() == 0) {
            endTime = "01";
        } else if (hour != null && hour.intValue() < 9) {
            endTime = "0" + nextHour;
        } else {
            endTime = nextHour.toString();
        }
        Log.e("nextHour", "+++=============================+++  + endTime : " + endTime);

        //对分钟做一下处理
        if (minute != null && minute.intValue() < 10) {
            endMinute = "0" + minute;
        } else {
            endMinute = minute.toString();
        }
        endTime = endTime + ":" + endMinute;
        return endTime;
    }

    /**
     * 取出：年月日中的日
     * 格式：yyyy-MM-dd
     *
     * @return
     */
    public Integer getDayFromString(String dateString) {
        String[] strs = dateString.split("-");
        return Integer.valueOf(strs[2]);
    }

    /**
     * 获取现在时间
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateYMD(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    @SuppressLint("SimpleDateFormat")
    public static String Date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
//            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    }



    public static Date String2Date(String dates) {
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date =  sdf.parse(dates);
        } catch (ParseException e) {

        }
        return date;
    }

    public static long String2Long(String dates) {
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date =  sdf.parse(dates);

        } catch (ParseException e) {

        }
        return date.getTime();
    }

    private  String mYear;
    private  String mMonth;
    private  String mDay;
    private  String mWay;

    public  void initDate(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="周日";
        }else if("2".equals(mWay)){
            mWay ="周一";
        }else if("3".equals(mWay)){
            mWay ="周二";
        }else if("4".equals(mWay)){
            mWay ="周三";
        }else if("5".equals(mWay)){
            mWay ="周四";
        }else if("6".equals(mWay)){
            mWay ="周五";
        }else if("7".equals(mWay)){
            mWay ="周六";
        }
    }

    public String getYear() {
        return mYear;
    }

    public String getMonth() {
        return mMonth;
    }

    public String getDay() {
        return mDay;
    }

    public String getWay() {
        return mWay;
    }
}
