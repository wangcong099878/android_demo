package the.one.base.util;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.TextView;


public class TimeDownUtil extends CountDownTimer {

    private TextView textView;//按钮

    /**
     *
     * @param millisInFuture  总时长
     * @param countDownInterval  时间间隔
     * @param button
     */
    public TimeDownUtil(long millisInFuture, long countDownInterval, TextView button) {
        super(millisInFuture, countDownInterval);
        this.textView =button;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {

        textView.setText(millisUntilFinished / 1000 + "s");//设置倒计时时间
       //textView，这时是不能点击的
        textView.setEnabled(false);
    }

    @Override
    public void onFinish() {
        textView.setText("重获验证码");
        textView.setEnabled(true);//为可以点击
    }
}
