package the.one.anastasia.base;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import the.one.anastasia.Interface.IViewBase;
import the.one.anastasia.R;
import the.one.anastasia.util.DeviceUtil;
import the.one.anastasia.util.QMUIDialogUtils;
import the.one.anastasia.view.ProgressLayout;


public abstract class BaseFragment extends Fragment implements IViewBase {

    private QMUITipDialog mProgressDialog;
    private ProgressLayout mProgressLayout;
    /***封装toast对象**/
    private static Toast toast;
    private View view2;

    /**
     * 是否显示无网络提示
     */
    protected boolean isShowNoNet() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate", getClass().getSimpleName());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, null);
        view2 = inflater.inflate(getLayout(), null);
        mProgressLayout = view.findViewById(R.id.progressLayout);
        mProgressLayout.addView(view2,-1,-1);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(view2);
        initData();
    }

    protected abstract int getLayout();
    protected abstract void initView(View rootView);
    protected abstract void initData();


    public void showProgressDialog()
    {
        showProgressDialog("加载中...");
    }

    @Override
    public void showProgressDialog(String text) {
            mProgressDialog = QMUIDialogUtils.LoadingTipsDialog(getActivity(), text);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        if(!getActivity().isFinishing())
        {
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
        if(mProgressLayout!=null)
        {
            mProgressLayout.showContent();
        }
    }

    @Override
    public void showLoadingPage() {
        if(mProgressLayout!=null)
        {
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
        //listView 没有数据的时候显示：默认图积即可，文字暂时去掉
        //showEmptyPage(emptyImageDrawable,"","暂无数据");
        showEmptyPage(emptyImageDrawable,"","");
    }

    @Override
    public void showEmptyPage(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent) {
        if(mProgressLayout!=null)
        {
            mProgressLayout.showEmpty(emptyImageDrawable,emptyTextTitle,emptyTextContent);
        }
    }

    @Override
    public void showErrorPage(View.OnClickListener listener) {
        //在这里可以添加默认的图片
        showErrorPage(getResources().getDrawable(R.drawable.loading_view_loading_fail), listener);
    }

    @Override
    public void showErrorPage(Drawable errorDrawable, View.OnClickListener listener) {
        showErrorPage(errorDrawable,"","出错啦!","刷新试试",listener);
    }

    @Override
    public void showErrorPage(Drawable errorImageDrawable, String errorTextTitle, String errorTextContent,String btnString, View.OnClickListener listener) {
        if(mProgressLayout!=null) {
            mProgressLayout.showError(errorImageDrawable,errorTextTitle,errorTextContent,btnString,listener);
        }
    }

    @Override
    public void showMustLogin(Drawable imageDrawable, String textTitle, String textContent, View.OnClickListener listener) {
        if(mProgressLayout!=null) {
            mProgressLayout.showError(imageDrawable,textTitle,textContent,"请先登录",listener);
        }
    }

    public void showEmptyPage(String message) {
        showEmptyPage(getResources().getDrawable(R.drawable.icon_search_result_null),"",message);
    }

    public void showEmptyPage(View.OnClickListener onClickListener){
        showErrorPage(ContextCompat.getDrawable(getContext(),R.drawable.icon_search_result_null),"","","刷新试试",onClickListener);
    }
    public void showEmptyPage(String title,View.OnClickListener onClickListener){
        showErrorPage(ContextCompat.getDrawable(getContext(),R.drawable.icon_search_result_null),title,"","刷新试试",onClickListener);
    }
    public void showEmptyPage(String title,String btnString,View.OnClickListener onClickListener){
        showErrorPage(ContextCompat.getDrawable(getContext(),R.drawable.icon_search_result_null),title,"",btnString,onClickListener);
    }

    public BaseActivity getBaseActivity()
    {
        if(getActivity() instanceof BaseActivity)
        {
            return (BaseActivity) getActivity();
        }
        return null;
    }

    /**
     * 取消网络请求
     */
    public void onCancelRequest(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onCancelRequest();
    }

    /**
     *
     * @return
     */
    public boolean isNet(){
        if( !DeviceUtil.isNetworkConnected(getActivity())&& isShowNoNet()){
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


    @Override
    public void onResume() {
        super.onResume();
        isNet();
    }

    /**
     * 显示长toast
     *
     * @param msg
     */
    public void showLongToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
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
            toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public void ViewGone(View view){
        if(view.getVisibility()!=View.GONE)
            view.setVisibility(View.GONE);
    }

    public void ViewShow(View view){
        if(view.getVisibility()!=View.VISIBLE)
            view.setVisibility(View.VISIBLE);
    }

    public View getView(int layoutId) {
        return View.inflate(getActivity(), layoutId, null);
    }

    public Drawable getDrawablee(int id){
        return ContextCompat.getDrawable(getActivity(),id);
    }

    public String getStringg(int id){
        return getResources().getString(id);
    }

    public int getColorr(int id){
        return ContextCompat.getColor(getActivity(),id);
    }

    public String getEditTextString(EditText editText){
        String str = editText.getText().toString();
        return str;
    }
}
