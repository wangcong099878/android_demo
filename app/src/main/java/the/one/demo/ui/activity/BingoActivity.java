package the.one.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.Random;

import butterknife.BindView;
import the.one.base.base.activity.BaseActivity;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.R;
import the.one.demo.widget.fireflower.FireworkView;

public class BingoActivity extends BaseActivity {

    @BindView(R.id.fire_work)
    FireworkView fireWork;

    private Thread mThread;
    private int fireWidth, fireHeight;
    private int time = 1000;

    private void initData() {
        fireWidth = QMUIDisplayHelper.getScreenWidth(this) / 2;
        fireHeight = QMUIDisplayHelper.getScreenHeight(this) / 2;
    }

    private void startFier() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //执行操作
                    if (mThread.isInterrupted()) break;
                    try {
                        Thread.sleep(time);
                        Random random = new Random();
                        final float x = fireWidth / 2 + random.nextInt(fireWidth);
                        final float y = fireHeight / 3 + random.nextInt(fireHeight);
                        time = 800 + random.nextInt(500);
                        final int type = random.nextInt(5);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                FireSize(x, y, type);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });
        mThread.start();
    }

    private int i = 0;

    private void FireSize(float x, float y, int type) {
        switch (type) {
            case 0:
                i = 8;
                break;
            case 1:
                i = 9;
                break;
            case 2:
                i = 10;
                break;
            case 3:
                i = 11;
                break;
            case 4:
                i = 12;
                break;

        }
        for (int num = 0; num < i; num++) {
            fireWork.launch(x - num, y - num, 10 + num, i * 100);
            fireWork.launch(x + num, y + num, 9 - num, i * 100);
        }
    }


    public static void startThisActivity(Activity activity) {
        Intent intent = new Intent(activity, BingoActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bingo;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void initView(View mRootView) {
        initData();
        startFier();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThread.interrupt();
    }
}
