package the.one.anastasia.util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;


public class TimeCountUtil extends CountDownTimer {
    private Button btn;//按钮
    public TimeCountUtil(long millisInFuture, long countDownInterval, Button button) {
        super(millisInFuture, countDownInterval);
        this.btn =button;
    }

    @Override
    public void onTick(long millisUntilFinished) {

        btn.setText(millisUntilFinished / 1000 + "秒后重新发送");//设置倒计时时间
       //设置按钮为灰色，这时是不能点击的
        btn.setEnabled(false);
        Spannable span = new SpannableString(btn.getText().toString());//获取按钮的文字
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#03a969")), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//倒计时时间的颜色是设置
        btn.setText(span);
    }

    @Override
    public void onFinish() {
        btn.setText("重获验证码");
        btn.setEnabled(true);//为可以点击
    }
}
