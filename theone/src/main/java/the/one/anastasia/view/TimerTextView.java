package the.one.anastasia.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import the.one.anastasia.R;


/**
 * 计时器 （ 正计时  倒计时   超时时间）
 * the one
 * 2018-1-4
 */

public class TimerTextView extends android.support.v7.widget.AppCompatTextView implements Runnable {
    //时间
    private long BetweenTimes;
    // 时间变量
    private int day, hour, minute, second;
    // 当前计时器是否运行
    private boolean isRun = false;
    //模式
    private int MODE = ADD_TIME;
    //超时时间
    private int OUT_TIME;
    //正计时
    public static int ADD_TIME = 100;
    //倒计时
    public static int DOWN_TIME = 200;

    public TimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerTextView(Context context) {
        super(context);
    }


    public void setMODE(int MODE) {
        this.MODE = MODE;
    }

    public void setOUT_TIME(int OUT_TIME) {
        this.OUT_TIME = OUT_TIME;
    }

    /**
     * 将时间毫秒数转换为自身变量
     *
     * @param time 时间间隔毫秒数
     */
    public void setTimes(long time) {
        //当前时间减去基础时间 = 经过的时间
        //把经过的时间转换成显示格式
        long current = System.currentTimeMillis();
        if (time > current)
            BetweenTimes = 1000;
        else
            BetweenTimes = current - time;
        this.second = (int) (BetweenTimes / 1000) % 60;
        this.minute = (int) (BetweenTimes / (60 * 1000) % 60);
        this.hour = (int) (BetweenTimes / (60 * 60 * 1000) % 24);
        this.day = (int) (BetweenTimes / (24 * 60 * 60 * 1000));
    }

    /**
     * 显示当前时间
     *
     * @return
     */
    public String showTime() {
        StringBuilder time = new StringBuilder();
        if (day != 0) {
            time.append(day);
            time.append("天");
            if (hour < 10) {
                time.append("0");
            }
            time.append(hour);
            time.append(":");
            if (minute < 10) {
                time.append("0");
            }
            time.append(minute);
            time.append(":");
            if (second < 10) {
                time.append("0");
            }
            time.append(second);
        } else if (hour != 0) {
            time.append(hour);
            time.append(":");
            if (minute < 10) {
                time.append("0");
            }
            time.append(minute);
            time.append(":");
            if (second < 10) {
                time.append("0");
            }
            time.append(second);
        } else if (minute != 0) {
            if (minute < 10) {
                time.append("0");
            }
            time.append(minute);
            time.append(":");
            if (second < 10) {
                time.append("0");
            }
            time.append(second);
        } else if (second != 0) {
            time.append("00:");
            if (second < 10) {
                time.append("0");
            }
            time.append(second);
        }
        return time.toString();
    }


    /**
     * 实现正计时
     */
    private void TimeAdd() {
        if (second == 59) {
            second = -1;
            minute++;
            if (minute == 60) {
                minute = 0;
                hour++;
                if (hour == 24) {
                    hour = 0;
                    day++;
                }
            }
        }
        second++;
    }

    /**
     * 实现倒计时
     */
    private void TimeDown() {
        if (second == 0) {
            if (minute == 0) {
                if (hour == 0) {
                    if (day == 0) {
                        //当时间归零时停止倒计时
                        isRun = false;
                        return;
                    } else {
                        day--;
                    }
                    hour = 23;
                } else {
                    hour--;
                }
                minute = 59;
            } else {
                minute--;
            }
            second = 60;
        }
        second--;
    }

    public boolean isRun() {
        return isRun;
    }

    /**
     * 开始计时
     */
    public void start() {
        isRun = true;
        run();
    }

    /**
     * 结束计时
     */
    public void stop() {
        isRun = false;
    }

    /**
     * 实现计时循环
     */
    @Override
    public void run() {
        if (isRun) {
            // 选择模式
            if (MODE == ADD_TIME) {
                TimeAdd();
            } else if (MODE == DOWN_TIME) {
                TimeDown();
            }
            this.setText(showTime());
            check();
            postDelayed(this, 1000);
        } else {
            removeCallbacks(this);
        }
    }

    private void check() {
        // 默认以分钟为基准
        // 分钟不为0则进行判断
        if (minute != 0) {
            // 超时则更换时间颜色
            if (minute >= OUT_TIME)
                this.setTextColor(ContextCompat.getColor(getContext(), R.color.qmui_config_color_red));
        }
    }

}
