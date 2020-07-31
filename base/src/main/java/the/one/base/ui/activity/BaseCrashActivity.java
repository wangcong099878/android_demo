package the.one.base.ui.activity;

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

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.io.File;

import the.one.base.R;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.FileUtils;
import the.one.base.util.ShareUtil;
import the.one.base.util.crash.CrashConfig;
import the.one.base.util.crash.CrashUtil;
import the.one.base.widge.MyTopBarLayout;
import the.one.base.widge.ThePopupWindow;

/**
 * @author The one
 * @date 2020/1/3 0003
 * @describe 程序崩溃后进入此界面
 * @email 625805189@qq.com
 * @remark
 */
public class BaseCrashActivity extends BaseActivity {

    protected View mRoot;
    protected MyTopBarLayout mTopBarLayout;
    protected View mErrorLayout;
    protected ScrollView mErrorInfoLayout;
    protected ImageView ivClose,ivCrash;
    protected TextView tvShowErrorInfo;
    protected QMUIRoundButton mReportError,mReStart;
    protected CrashConfig mConfig;
    protected String mDetailErrorStr;

    @Override
    protected boolean isStatusBarLightMode() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_crash;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView(View mRootView) {
        mConfig = CrashUtil.getConfigFromIntent(getIntent());
        if (mConfig == null) return;
        mDetailErrorStr = CrashUtil.getAllErrorDetailsFromIntent(BaseCrashActivity.this, getIntent());
        mRoot = findViewById(R.id.root);
        mTopBarLayout = findViewById(R.id.top_layout);
        tvShowErrorInfo = findViewById(R.id.tv_show_info_layout);
        ivCrash = findViewById(R.id.iv_crash);
        mReportError = findViewById(R.id.report_error);
        mReStart = findViewById(R.id.restart);

        TypedArray array = obtainStyledAttributes(null, R.styleable.CrashLayout, R.attr.CrashLayoutStyle, 0);
        Drawable crash_drawable = array.getDrawable(R.styleable.CrashLayout_crash_drawable);
        String crash_report_tip = array.getString(R.styleable.CrashLayout_crash_report_tip);
        String crash_title = array.getString(R.styleable.CrashLayout_crash_title);
        String crash_restart = array.getString(R.styleable.CrashLayout_crash_restart);
        String crash_show_info = array.getString(R.styleable.CrashLayout_crash_show_info);
        array.recycle();

        ivCrash.setImageDrawable(crash_drawable);
        mReStart.setText(crash_restart);
        tvShowErrorInfo.setText(crash_show_info);
        mReportError.setText(crash_report_tip);
        mTopBarLayout.setTitle(crash_title).setTextColor(getColorr(R.color.qmui_config_color_gray_1));
        mTopBarLayout.setBackgroundColor(getColorr(R.color.qmui_config_color_white));

        initErrorInfoLayout();

        tvShowErrorInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showErrorInfoPop();
            }
        });
        mReportError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportError();
            }
        });
        mReStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartAPP();
            }
        });
    }

    /**
     * 获取错误信息
     * @return
     */
    protected String getDetailErrorStr(){
        return mDetailErrorStr;
    }

    /**
     * 反馈Error
     * 默认分享错误文本信息
     * */
    protected void reportError(){
        shareErrorContent();
    }

    /**
     * 重启APP
     */
    protected void restartAPP(){
        CrashUtil.restartApplication(BaseCrashActivity.this, mConfig);
    }

    /**
     * 复制到剪切板
     */
    protected void copyErrorInfo(){
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setPrimaryClip(ClipData.newPlainText("崩溃日志", mDetailErrorStr));
    }

    /**
     * 分享文本内容
     */
    protected void shareErrorContent(){
        ShareUtil.shareText(BaseCrashActivity.this,"发送错误报告",mDetailErrorStr);
    }

    /**
     * 分享错误信息图片
     */
    protected void saveErrorImage() {
        showLoadingDialog("打印病例中...");
        Bitmap bt = getBitmapByView();
        if (null == bt) showFailTips("图片获取失败");
        String savePath = FileUtils.saveBitmap("crash", bt);
        if (TextUtils.isEmpty(savePath)) showFailTips("图片保存失败");
        File saveFile = new File(savePath);
        if (!saveFile.exists()) showFailTips("图片失效了");
        hideLoadingDialog();
        ShareUtil.shareImageFile(this, saveFile, "快点抢救吧~");
    }

    protected Bitmap getBitmapByView() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int height = 0;
        for (int i = 0; i < mErrorInfoLayout.getChildCount(); i++) {
            height += mErrorInfoLayout.getChildAt(i).getHeight();
        }
        Bitmap svBitmap = Bitmap.createBitmap(mErrorInfoLayout.getWidth(), height,
                Bitmap.Config.ARGB_8888);
        Canvas svCanvas = new Canvas(svBitmap);
        svCanvas.drawRGB(255, 255, 255);
        mErrorInfoLayout.draw(svCanvas);
        svCanvas.drawBitmap(svBitmap, 0, 0, paint);
        return svBitmap;
    }

    protected void initErrorInfoLayout(){
        mErrorLayout = getView(R.layout.custom_crash_info);
        mErrorInfoLayout = mErrorLayout.findViewById(R.id.error_layout);
        TextView textMessage = mErrorLayout.findViewById(R.id.textMessage);
        mErrorLayout.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mErrorInfoPop.hide();
            }
        });
        textMessage.setText(mDetailErrorStr);
    }

    protected ThePopupWindow mErrorInfoPop;

    protected void showErrorInfoPop(){
        if(null == mErrorInfoPop){
            mErrorInfoPop = new ThePopupWindow(this,mRoot,mErrorLayout,mTopBarLayout);
        }
        mErrorInfoPop.show();
    }

    @Override
    protected void doOnBackPressed() {
        if(null != mErrorInfoPop && mErrorInfoPop.isShowing()){
            mErrorInfoPop.hide();
            return;
        }
        super.doOnBackPressed();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
