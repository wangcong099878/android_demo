package the.one.base.widge.pullrefresh;

import android.content.Context;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.qmuiteam.qmui.util.QMUIColorHelper;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import the.one.base.R;
import the.one.base.constant.PhoneConstant;
import the.one.base.widge.ProgressWheel;


//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

/**
 * @author The one
 * @date 2019/8/20 0020
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class FlymeStyleLoadingView extends RelativeLayout implements QMUIPullRefreshLayout.IRefreshView {

    private static final String TAG = "PullRefreshLoadingView";

    private ProgressWheel mProgressWheel;
    private AppCompatTextView mTips;
    private int mTextColor;

    private Vibrator vibrator;

    private boolean isPrepare = false;

    public FlymeStyleLoadingView(Context context) {
        super(context);
        init(context);
    }

    public FlymeStyleLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pull_refresh_layout, null);
        mProgressWheel = view.findViewById(R.id.progressWheel);
        mTips = view.findViewById(R.id.tv_tips);
        mTextColor = ContextCompat.getColor(context,R.color.qmui_config_color_gray_1);
        addView(view);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void stop() {
        mTips.setText("下拉刷新");
    }

    @Override
    public void doRefresh() {
        mTips.setText("更新中");
        mProgressWheel.spin();
    }

    @Override
    public void onPull(int i, int i1, int i2) {
        float percent = i * 1.0f / i1;
        if (percent >= 1) {
            if (!isPrepare && PhoneConstant.isHaveVibrator && vibrator.hasVibrator()) {
                vibrator.vibrate(10);
            }
            isPrepare = true;
            mTips.setText("释放刷新");
        } else {
            isPrepare = false;
            mTips.setText("下拉刷新");
            mProgressWheel.setProgress(percent);
            //文字透明度
            mTips.setTextColor(QMUIColorHelper.setColorAlpha(mTextColor,percent));
        }
    }

}
