package the.one.aqtour.ui.presenter;

import android.view.View;

import com.rxjava.rxlife.RxLife;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;
import the.one.aqtour.constant.QSPConstant;
import the.one.aqtour.util.QSPSoupUtil;
import the.one.base.Interface.OnError;

public class QSPVideoPresenter extends BaseVideoPresenter {

    private boolean isIndex = false;

    public void getVideoList(final String url, final int page) {
        isIndex = url.equals("/");
        String URL = QSPConstant.BASE_URL + url;
        if (!isIndex) {
            if (URL.endsWith(CATEGORY_FIX)) {
                URL = URL.replace(CATEGORY_FIX, page + CATEGORY_FIX);
            } else {
                URL = URL.replace(FIX, "-" + page + FIX);
            }
        }

        RxHttp.get(URL)
            .asString()
            .observeOn(AndroidSchedulers.mainThread())
            .as(RxLife.as(this))
            .subscribe(s -> {
                //请求成功
                getView().onSuccess(QSPSoupUtil.parseVideoSections(s, isIndex, page));
                if (isIndex) {
                    getView().onNormal();
                }
            }, (OnError) error  -> {
                //请求失败
               onFail(error.getErrorMsg(), new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       getVideoList(url,page);
                   }
               });
            });
    }

}
