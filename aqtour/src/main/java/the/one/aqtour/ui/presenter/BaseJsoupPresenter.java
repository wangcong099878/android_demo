package the.one.aqtour.ui.presenter;


import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import the.one.base.ui.presenter.BasePresenter;
import the.one.base.ui.view.BaseView;

public class BaseJsoupPresenter<V extends BaseView> extends BasePresenter<V> {


    protected void request(String url,StringCallback callback) {
        Log.e(TAG, "getVideoList: " + url);
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(callback);
    }

}
