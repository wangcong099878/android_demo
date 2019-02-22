package the.one.base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;

import java.util.Calendar;
import java.util.Date;

import the.one.base.R;
import the.one.base.widge.DateTimePicker;


/**
 * @author The one
 * @date 2018/10/25 0025
 * @describe 日期选择
 * @email 625805189@qq.com
 * @remark
 */
public class DatePickerUtil {

    @SuppressLint("StaticFieldLeak")
    private static DatePickerUtil datePickerUtil;

    public static DatePickerUtil getInstance() {
        if (null == datePickerUtil)
            datePickerUtil = new DatePickerUtil();
        return datePickerUtil;
    }

    private DateTimePicker.Builder TBuilder;
    private Date start, end;
    private Context context;

    public DatePickerUtil initTimePicker(Context context) {
        initTimePicker(context, new Date());
        return this;
    }
    public DatePickerUtil initNextYearTimePicker(Context context) {
        initTimePicker(context, getNextYearDate());
        return this;
    }

    public DatePickerUtil initTimePicker(Context context, Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1960);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Date start = calendar.getTime();

        initTimePicker(context, start, end);
        return this;
    }

    /**
     *
     * @param context
     * @param start  开始日期
     * @param end  结束日期
     * @return
     */
    public DatePickerUtil initTimePicker(Context context, Date start, Date end) {
        this.context = context;
        this.start = start;
        this.end = end;
        //方式一：构建自己的builder
        TBuilder = new DateTimePicker.Builder(context)
                .setOk("确定")
                .setCancel("取消")
                .setCancelTextColor(Color.RED)
                .setOkTextColor(context.getResources().getColor(R.color.qmui_config_color_red))
                .setTitleTextColor(0xFF999999)
                .setSelectedTextColor(context.getResources().getColor(R.color.qmui_config_color_blue))
                .setShowType(DateTimePicker.ShowType.DAY);
        return this;
    }

    /**
     * 一个月之后的Date
     * @return
     */
    public Date getNextMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
    /**
     * 一个月之后的Date
     * @return
     */
    public Date getNextYearDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }
    public Date getMaxDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2100);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTime();
    }
    public Date getMinDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1960);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTime();
        }
    public void show(String title, DateTimePicker.ResultHandler resultHandler) {
        show(title,resultHandler,new Date());
    }

    public void show(String title, DateTimePicker.ResultHandler resultHandler,Date showDate) {
        TBuilder.setTitle(title);
        new DateTimePicker(context, resultHandler, start, end, TBuilder).show(showDate);
    }

    public void show( DateTimePicker.ResultHandler resultHandler) {
        show("选择日期",resultHandler);
    }
}
