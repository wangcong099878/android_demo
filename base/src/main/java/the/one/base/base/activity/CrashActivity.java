package the.one.base.base.activity;

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
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.io.File;
import java.text.SimpleDateFormat;

import the.one.base.R;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.base.model.CrashModel;
import the.one.base.util.AppInfoManager;
import the.one.base.util.FileUtils;
import the.one.base.util.ShareUtil;
import the.one.base.widge.MyTopBarLayout;
import the.one.base.widge.ThePopupWindow;

/**
 * @author The one
 * @date 2020/1/3 0003
 * @describe 程序崩溃后进入此界面
 * @email 625805189@qq.com
 * @remark
 */
public class CrashActivity extends BaseActivity {

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private CrashModel model;

    protected View mRoot;
    private MyTopBarLayout mTopBarLayout;
    protected View mErrorLayout;
    private ScrollView mErrorInfoLayout;
    private AppCompatImageView ivClose,ivCrash;
    private TextView tvShowErrorInfo;
    private QMUIRoundButton mReportError,mReStart;

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
        model = getIntent().getParcelableExtra(DataConstant.DATA);
        if (model == null) return;
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
        mTopBarLayout.setBackgroundColor(getColorr(R.color.white));

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
                AppInfoManager.restartApp(CrashActivity.this);
            }
        });
    }

    /**
     * 反馈Error
     * 默认发送错误图片
     */
    protected void reportError(){
        saveErrorImage();
    }

    private void saveErrorImage() {
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

    public Bitmap getBitmapByView() {
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
        TextView tv_className = mErrorLayout.findViewById(R.id.tv_className);
        TextView tv_methodName = mErrorLayout.findViewById(R.id.tv_methodName);
        TextView tv_lineNumber = mErrorLayout.findViewById(R.id.tv_lineNumber);
        TextView tv_exceptionType = mErrorLayout.findViewById(R.id.tv_exceptionType);
        TextView tv_fullException = mErrorLayout.findViewById(R.id.tv_fullException);
        TextView tv_time = mErrorLayout.findViewById(R.id.tv_time);
        TextView tv_model = mErrorLayout.findViewById(R.id.tv_model);
        TextView tv_brand = mErrorLayout.findViewById(R.id.tv_brand);
        TextView tv_version = mErrorLayout.findViewById(R.id.tv_version);
        mErrorLayout.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mErrorInfoPop.hide();
            }
        });

        textMessage.setText(model.getExceptionMsg());
        tv_className.setText(model.getFileName());
        tv_methodName.setText(model.getMethodName());
        tv_lineNumber.setText(String.valueOf(model.getLineNumber()));
        tv_exceptionType.setText(model.getExceptionType());
        tv_fullException.setText(model.getFullException());
        tv_time.setText(df.format(model.getTime()));
        CrashModel.Device device = model.getDevice();
        tv_model.setText(device.getModel());
        tv_brand.setText(device.getBrand());
        tv_version.setText(device.getVersion());
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

    public static void handleException(Context context, CrashModel model) {
        Intent intent = new Intent(context, CrashActivity.class);
        intent.putExtra(DataConstant.DATA, model);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
