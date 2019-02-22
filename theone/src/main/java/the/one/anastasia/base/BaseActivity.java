package the.one.anastasia.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import the.one.anastasia.Interface.IViewBase;
import the.one.anastasia.R;
import the.one.anastasia.util.DeviceUtil;
import the.one.anastasia.util.QMUIDialogUtils;
import the.one.anastasia.view.CommonTitleBar;
import the.one.anastasia.view.ProgressLayout;

public abstract class BaseActivity extends AppCompatActivity implements IViewBase {

    private static List<BaseActivity> activities = new ArrayList<>();
    private ProgressLayout mProgressLayout;
    private QMUITipDialog mProgressDialog;
    public CommonTitleBar titleBar;
    private List<Dialog> dialogs = new ArrayList<>();
    private Unbinder unbinder;
    /***封装toast对象**/
    private static Toast toast;
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 是否滑动返回
     */
    protected boolean isSwipeBackEnable() {
        return true;
    }

    /**
     * 是否显示状态栏
     */
    protected boolean isShowStatusBar() {
        return true;
    }

    /**
     * 是否显示无网络提示
     */
    protected boolean isShowNoNet() {
        return false;
    }


    /**
     * TitleBarClickListener
     */
    protected void onTitleBarClickListener(View v, int action, String extra) {
        onTitleBarBack(action);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);
        // 是否显示状态栏
        if (!isShowStatusBar()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        SwipeBackHelper.onCreate(this);
        if (isSwipeBackEnable() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SwipeBackHelper.getCurrentPage(this)
                    .setSwipeBackEnable(true)
                    .setSwipeSensitivity(0.5f)
                    .setSwipeEdge(150)
                    .setSwipeRelateEnable(true)
                    .setClosePercent(0.7f);
        } else {
            SwipeBackHelper.getCurrentPage(this)
                    .setSwipeBackEnable(false);
        }
        setContentView(R.layout.activity_base);
        initBodyView();
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
        afterInitData();
    }

    protected abstract int getBodyLayout();

    protected abstract void initView();

    protected abstract void initData();

    public void startActivity(Class a) {
        Intent intent = new Intent(this, a);
        startActivity(intent);
    }

    private void initBodyView() {
        mProgressLayout = (ProgressLayout) findViewById(R.id.progressLayout);
        mProgressLayout.addView(getView(getBodyLayout()), -1, -1);
        titleBar = (CommonTitleBar) findViewById(R.id.titlebar);
        titleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                onTitleBarClickListener(v, action, extra);
            }
        });
    }

    public void setTitleString(String title) {
        titleBar.getCenterTextView().setText(title);
    }

    public void onTitleBarBack(int action) {
        if (action == CommonTitleBar.ACTION_LEFT_BUTTON
                || action == CommonTitleBar.ACTION_LEFT_TEXT) {
            onBackPressed();
        }
    }

    public View getView(int layoutId) {
        return View.inflate(this, layoutId, null);
    }

    public Drawable getDrawablee(int id) {
        return ContextCompat.getDrawable(this, id);
    }

    public String getStringg(int id) {
        return getResources().getString(id);
    }

    public int getColorr(int id) {
        return ContextCompat.getColor(this, id);
    }

    public String getEditTextString(EditText editText) {
        String str = editText.getText().toString();
        return str;
    }

    @Override
    public void showProgressDialog(String text) {
        mProgressDialog = QMUIDialogUtils.LoadingTipsDialog(this, text);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        if (!isFinishing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog == null)
            return;
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showContentPage() {
        if (mProgressLayout != null) {
            mProgressLayout.showContent();
        }
    }

    @Override
    public void showLoadingPage() {
        if (mProgressLayout != null) {
            mProgressLayout.showLoading();
        }
    }

    @Override
    public void showEmptyPage() {
        //在这里可以添加默认的图片
        showEmptyPage(getResources().getDrawable(R.drawable.icon_search_result_null));
    }

    @Override
    public void showEmptyPage(Drawable emptyImageDrawable) {
        showEmptyPage(emptyImageDrawable, "", " ");
    }

    public void showEmptyPage(View.OnClickListener listener) {
        if (mProgressLayout != null) {
            mProgressLayout.showError(getResources().getDrawable(R.drawable.icon_search_result_null), "", "", " 刷新试试 ", listener);
        }
    }

    public void showEmptyPage(String title, View.OnClickListener onClickListener) {
        showErrorPage(ContextCompat.getDrawable(this, R.drawable.icon_search_result_null), title, ""," 刷新试试 ", onClickListener);
    }

    public void showEmptyPage(String msg) {
        if (mProgressLayout != null) {
            mProgressLayout.showError(getResources().getDrawable(R.drawable.icon_search_result_null), "", "", " 刷新试试 ", null);
        }
    }

    @Override
    public void showEmptyPage(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent) {
        if (mProgressLayout != null) {
            mProgressLayout.showEmpty(emptyImageDrawable, emptyTextTitle, emptyTextContent);
        }
    }

    @Override
    public void showErrorPage(View.OnClickListener listener) {
        //在这里可以添加默认的图片
        showErrorPage(getResources().getDrawable(R.drawable.loading_view_loading_fail), listener);
    }

    @Override
    public void showErrorPage(Drawable errorDrawable, View.OnClickListener listener) {
        showErrorPage(errorDrawable, "", ""," 刷新试试 ", listener);
    }

    @Override
    public void showErrorPage(Drawable errorImageDrawable, String errorTextTitle, String errorTextContent,String btnString, View.OnClickListener listener) {
        if (mProgressLayout != null) {
            mProgressLayout.showError(errorImageDrawable, errorTextTitle, errorTextContent, btnString, listener);
        }
    }

    @Override
    public void showMustLogin(Drawable imageDrawable, String textTitle, String textContent, View.OnClickListener listener) {
        if (mProgressLayout != null) {
            mProgressLayout.showError(imageDrawable, textTitle, textContent, "请先登录", listener);
        }
    }

    // 触摸其他区域，输入法界面消失
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    public static void finishAllActivies() {
        for (int i = 0; i < activities.size(); i++) {
            activities.get(i).finish();
        }
        activities.clear();
    }


    protected void afterInitData() {

    }

    @Override
    public void finish() {
        hideProgressDialog();
        for (int i = 0; i < dialogs.size(); i++) {
            Dialog dialog = dialogs.get(i);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        dialogs.clear();
        super.finish();
        overridePendingTransition(R.anim.activity_back, R.anim.activity_finish);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_new, R.anim.activity_out);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.activity_new, R.anim.activity_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_new, R.anim.activity_out);
    }

    /**
     * 获取顶部的activity
     *
     * @return
     */
    public static Activity getTopActivity() {
        if (activities.size() > 0) {
            return activities.get(activities.size() - 1);
        }
        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (activities.contains(this)) {
            activities.remove(this);
        }
        activities.add(this);
    }

    public void addDialog(Dialog dialog) {
        dialogs.add(dialog);
    }

    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }


    /***
     * =======================================================
     * 以下为处理 Fragment 中onActivity 无效的方法
     *
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int indext = 0; indext < fragmentManager.getFragments().size(); indext++) {
            Fragment fragment = fragmentManager.getFragments().get(indext); //找到第一层Fragment
            if (fragment == null)
                Log.w(TAG, "Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            else
                handleResult(fragment, requestCode, resultCode, data);
        }
    }

    /**
     * 递归调用，对所有的子Fragment生效
     *
     * @param fragment
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);//调用每个Fragment的onActivityResult
        Log.e(TAG, "MyBaseFragmentActivity  handleResult");
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments(); //找到第二层Fragment
        if (childFragment != null)
            for (Fragment f : childFragment)
                if (f != null) {
                    handleResult(f, requestCode, resultCode, data);
                }
        if (childFragment == null)
            Log.e(TAG, "MyBaseFragmentActivity1111");
    }


    /**
     * 判断是否联网
     *
     * @return
     */
    public boolean isNet() {
        if (!DeviceUtil.isNetworkConnected(this) && isShowNoNet()) {
            if (mProgressLayout != null) {
                mProgressLayout.showError(getDrawablee(R.drawable.loading_view_no_network), "无网络", "", "检查网络", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS)); //直接进入手机中设置界面
                    }
                });
            }
            return false;
        }
        return true;
    }


    /**
     * 显示长toast
     *
     * @param msg
     */
    public void showLongToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 显示短toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public void showToast(boolean msg) {
        showToast(msg + "");
    }

    public void showToast(int msg) {
        showToast(msg + "");
    }

    public void showToast(double msg) {
        showToast(msg + "");
    }

    public void ViewGone(View... views) {
        for (View view : views) {
            if (view.getVisibility() != View.GONE)
                view.setVisibility(View.GONE);
        }
    }

    public void showView(View... views) {
        for (View view : views) {
            if (view.getVisibility() != View.VISIBLE)
                view.setVisibility(View.VISIBLE);
        }
    }

    public void showLog(String str) {
        Log.e("LOG", str);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isNet();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (!isShowStatusBar()) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
        unbinder.unbind();
    }
}
