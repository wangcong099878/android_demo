package the.one.base.base.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import the.one.base.base.view.BaseView;


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
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract   class BaseActivity extends QMUIActivity implements BaseView {

    protected abstract int getLayoutId();
    protected abstract void initView();

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(this, 150);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != unbinder) unbinder.unbind();
    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void hideLoadingDialog() {

    }

    @Override
    public void showProgressDialog(int percent) {

    }

    @Override
    public void showProgressDialog(int percent, int total) {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showContentPage() {

    }

    @Override
    public void showLoadingPage() {

    }

    @Override
    public void showEmptyPage(String title) {

    }

    @Override
    public void showEmptyPage(String title, String btnString, View.OnClickListener listener) {

    }

    @Override
    public void showEmptyPage(String title, String content, String btnString, View.OnClickListener listener) {

    }

    @Override
    public void showEmptyPage(Drawable drawable, String title, String content, String btnString, View.OnClickListener listener) {

    }

    @Override
    public void showErrorPage(String title) {

    }

    @Override
    public void showErrorPage(String title, String btnString, View.OnClickListener listener) {

    }

    @Override
    public void showErrorPage(String title, String content, String btnString, View.OnClickListener listener) {

    }

    @Override
    public void showErrorPage(Drawable drawable, String title, String content, String btnString, View.OnClickListener listener) {

    }
}
