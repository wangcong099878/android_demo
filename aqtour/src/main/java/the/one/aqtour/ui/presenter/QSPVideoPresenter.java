package the.one.aqtour.ui.presenter;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
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
        request(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (isViewAttached())
                    getView().onFail(e);
            }

            @Override
            public void onResponse(String response, int id) {
                if (isViewAttached()) {
                    getView().onSuccess(QSPSoupUtil.parseVideoSections(response, isIndex, page));
                    if (isIndex) {
                        getView().onNormal();
                    }
                }
            }
        });

    }

}
