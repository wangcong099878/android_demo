package the.one.aqtour.ui.presenter;

import android.view.View;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import the.one.aqtour.constant.QSPConstant;
import the.one.aqtour.ui.view.QSPCategoryView;
import the.one.aqtour.util.QSPSoupUtil;
import the.one.net.util.FailUtil;

public class QSPCategoryPresenter extends BaseJsoupPresenter<QSPCategoryView> {

    public void getCategoryList() {
        request(QSPConstant.BASE_URL, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (isViewAttached())
                    getView().showErrorPage(FailUtil.getFailString(e), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getCategoryList();
                        }
                    });
            }

            @Override
            public void onResponse(String response, int id) {
                if (isViewAttached()) {
                    getView().onComplete(QSPSoupUtil.parseCategoryList(response));
                }
            }
        });
    }

}
