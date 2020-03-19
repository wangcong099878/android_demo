package the.one.aqtour.ui.presenter;

import com.rxjava.rxlife.RxLife;
import com.zhy.http.okhttp.callback.StringCallback;

import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.Call;
import rxhttp.wrapper.param.RxHttp;
import the.one.aqtour.constant.QSPConstant;
import the.one.aqtour.util.QSPSoupUtil;

public class QSPVideoPresenter extends BaseVideoPresenter {

    private boolean isIndex = false;

    public void getVideoList(String url, final int page) {
        isIndex = url.equals("/");
        url = QSPConstant.BASE_URL + url;
        if (!isIndex) {
            if (url.endsWith(CATEGORY_FIX)) {
                url = url.replace(CATEGORY_FIX, page + CATEGORY_FIX);
            } else {
                url = url.replace(FIX, "-" + page + FIX);
            }
        }

        RxHttp.get(url)
            .asString()
            .observeOn(AndroidSchedulers.mainThread())
            .as(RxLife.as(this))
            .subscribe(s -> {
                //请求成功
                getView().onSuccess(QSPSoupUtil.parseVideoSections(s, isIndex, page));
                if (isIndex) {
                    getView().onNormal();
                }
            }, throwable -> {
                //请求失败
               onFail(throwable.getMessage());
            });
    }

}
