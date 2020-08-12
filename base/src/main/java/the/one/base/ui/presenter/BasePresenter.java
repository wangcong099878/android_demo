package the.one.base.ui.presenter;

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

import android.graphics.drawable.Drawable;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import the.one.base.ui.view.BaseView;
import the.one.base.util.ExceptionHelper;
import the.one.base.util.NetFailUtil;


/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BasePresenter<V extends BaseView> implements LifecycleEventObserver,LifecycleOwner {

    protected final String TAG = this.getClass().getSimpleName();

    private LifecycleOwner lifecycleOwner;
    private V baseView;

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    public void attachView(@NotNull V baseView, @NotNull LifecycleOwner lifecycleOwner) {
        this.baseView = baseView;
        this.lifecycleOwner = lifecycleOwner;
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    public void detachView() {
        this.baseView = null;
        this.lifecycleOwner = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached() {
        return baseView != null;
    }

    /**
     * 获取连接的view
     */
    public V getView() {
        return baseView;
    }

    protected void onFail(Exception e) {
        onFail(NetFailUtil.getFailString(e),null);
    }

    protected void onFail(Throwable t) {
        onFail(ExceptionHelper.handleException(t),null);
    }

    protected void onFail(Throwable t, View.OnClickListener listener) {
        onFail(ExceptionHelper.handleException(t),listener);
    }

    protected void onFail(String error, View.OnClickListener listener) {
        if (isViewAttached()) {
            getView().showEmptyPage(error,listener);
        }
    }

    protected void showFailTips(String msg) {
        if (isViewAttached())
            getView().showFailTips(msg);
    }

    protected void showSuccessTips(String msg) {
        if (isViewAttached())
            getView().showSuccessTips(msg);
    }

    protected void showErrorPage(String title) {
        if (isViewAttached()) {
            getView().showErrorPage(title);
        }
    }

    protected void showErrorPage(String title, View.OnClickListener listener) {
        if (isViewAttached()) {
            getView().showErrorPage(title, listener);
        }
    }

    protected void showErrorPage(String title, String btnString, View.OnClickListener listener) {
        if (isViewAttached()) {
            getView().showErrorPage(title, btnString, listener);
        }
    }

    protected void showErrorPage(String title, String content, String btnString, View.OnClickListener listener) {
        if (isViewAttached()) {
            getView().showErrorPage(title, content, btnString, listener);
        }
    }

    protected void showErrorPage(Drawable drawable, String title, String content, String btnString, View.OnClickListener listener) {
        if (isViewAttached()) {
            getView().showErrorPage(drawable, title, content, btnString, listener);
        }
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleOwner.getLifecycle();
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if(event == Lifecycle.Event.ON_DESTROY){
            source.getLifecycle().removeObserver(this);
            detachView();
        }
    }

}
