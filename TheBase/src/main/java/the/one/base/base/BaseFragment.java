package the.one.base.base;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

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
public abstract class BaseFragment extends QMUIFragment implements BaseView{

    protected final String TAG = this.getClass().getSimpleName();

    protected abstract int getContentViewId();
    /**
     * 获取Presenter实例，子类实现
     */
    public abstract BasePresenter getPresenter();
    protected abstract void initView( View view);

    protected boolean showTitleBar(){ return true;}
    protected StatusLayout mStatusLayout;
    protected QMUITopBarLayout mTopLayout;
    protected ProgressDialog progressDialog;

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }

    @Override
    protected View onCreateView() {
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
        initView(mRootView);
        if(getPresenter() != null){
            getPresenter().attachView(this);
        }
        return mRootView;
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

    public View getView(int layoutId){
        return getLayoutInflater().inflate(layoutId,null);
    }

    public Drawable getDrawablee(int id) {
        return ContextCompat.getDrawable(getActivity(), id);
    }

    public String getStringg(int id) {
        return getResources().getString(id);
    }

    public int getColorr(int id) {
        return ContextCompat.getColor(getActivity(), id);
    }

    public String getEditTextString(EditText editText) {
        String str = editText.getText().toString();
        return str;
    }
    public String getTextViewString(TextView textView) {
        String str = textView.getText().toString();
        return str;
    }
    public void goneView(View... views) {
        for (View view : views) {
            if (null !=view && view.getVisibility() != View.GONE)
                view.setVisibility(View.GONE);
        }
    }

    public void showView(View... views) {
        for (View view : views) {
            if (view.getVisibility() != View.VISIBLE)
                view.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(getPresenter()!=null){
            getPresenter().detachView();
        }
    }
}
