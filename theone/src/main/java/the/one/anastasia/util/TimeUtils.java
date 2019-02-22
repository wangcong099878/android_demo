package the.one.anastasia.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tifezh on 2016/5/7.
 */
public class TimeUtils {

    public static final long oneSecond=1000;

    public static final long oneMinute = 60*oneSecond;

    public static final long oneHour = 60 * oneMinute;

    public static final long oneDay = 24 * oneHour;

    public static Date parseTime(String date) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(date);
    }

    public static String getTimeString(String time)
    {
        try {
            return TimeUtils.getTimeString(TimeUtils.parseTime(time).getTime(),0);
        } catch (ParseException e) {
            return time;
        }
    }

    /**
     * 转换时间戳
     *
     * @param oldtime
     */
    public static String getTimeString(long oldtime,long now) {
        long today=0;
        if(now>0){
            today = now;
        }else{
            today = System.currentTimeMillis();
        }
        long time = today - oldtime;
        long days = time / oneDay;
        long hours = time / oneHour;
        long minutes = time / oneMinute;
        if (days > 10) {
            return new SimpleDateFormat("yyyy-MM-dd").format(oldtime);
        }
        else if(days>0)
        {
            return days+"天前";
        }
        else if(hours>0)
        {
            return hours+"小时前";
        }
        else if(minutes>0)
        {
            return minutes+"分钟前";
        }
        else {
            return "刚刚";
        }
    }
}
