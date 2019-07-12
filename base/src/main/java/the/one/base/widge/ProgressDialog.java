package the.one.base.widge;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUIProgressBar;

import the.one.base.R;


/**
 * 进度条Dialog
 */
public class ProgressDialog extends AlertDialog implements QMUIProgressBar.QMUIProgressBarTextGenerator {

    private TextView tips_loading_msg;
    private String message;
    private QMUIProgressBar progress;
    private int oldPercent = 0;

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public ProgressDialog(Context context) {
        super(context, R.style.dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_progress);
        tips_loading_msg = findViewById(R.id.tips_loading_msg);
        progress = findViewById(R.id.my_progress);
        progress.setQMUIProgressBarTextGenerator(this);
        tips_loading_msg.setText(this.message);
    }

    public void setProgress(int percent,int total) {
        if (null != progress && percent != oldPercent) {
            oldPercent = percent;
            progress.setMaxValue(total);
            progress.setProgress(percent);
        }
    }

    /**
     * 设置提示语
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
        if (tips_loading_msg != null) {
            tips_loading_msg.setText(message);
        }
    }

    @Override
    public String generateText(QMUIProgressBar progressBar, int value, int maxValue) {
        return value + "%";
    }
}
