package the.one.base.base;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
import the.one.base.R;
import the.one.base.widge.StatusLayout;


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
public abstract   class BaseDelegate extends SwipeBackFragment implements BaseView{

    protected final String TAG = this.getClass().getSimpleName();

    protected abstract int getContentViewId();
    /**
     * 获取Presenter实例，子类实现
     */
    public abstract BasePresenter getPresenter();
    protected abstract void initView(Bundle savedInstanceState,View view);

    protected boolean showTitleBar(){ return true;}
    protected StatusLayout mStatusLayout;
    protected QMUITopBarLayout mTopLayout;
    protected ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mBody = getView(getContentViewId());
        View mRootView;
        if(showTitleBar()){
            mRootView = getView(R.layout.delegate_base);
            mStatusLayout = mRootView.findViewById(R.id.status_layout);
            mTopLayout = mRootView.findViewById(R.id.top_layout);
            mStatusLayout.addView(mBody,-1,-1);
        }else{
            mRootView = mBody;
        }
        initView(savedInstanceState,mRootView);
        if(getPresenter() != null){
            getPresenter().attachView(this);
        }
        return mRootView;
    }


    @Override
    public void showLoadingDialog(String msg) {
        if(null == progressDialog){
            progressDialog = new ProgressDialog(_mActivity);
        }
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if(null != progressDialog)
            progressDialog.dismiss();
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

    public View getView(int layoutId){
        return getLayoutInflater().inflate(layoutId,null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(getPresenter()!=null){
            getPresenter().detachView();
        }
    }
}
