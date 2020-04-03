package the.one.aqtour.ui.presenter;

import android.view.View;

import com.rxjava.rxlife.RxLife;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;
import the.one.aqtour.constant.QSPConstant;
import the.one.aqtour.ui.view.QSPCategoryView;
import the.one.aqtour.util.QSPSoupUtil;
import the.one.base.Interface.OnError;
import the.one.base.base.presenter.BasePresenter;

public class QSPCategoryPresenter extends BasePresenter<QSPCategoryView> {

    public void getCategoryList() {
        RxHttp.get(QSPConstant.BASE_URL)
            .asString()
            .observeOn(AndroidSchedulers.mainThread())
            .as(RxLife.as(this))
            .subscribe(s -> {
                //请求成功
                getView().onComplete(QSPSoupUtil.parseCategoryList(s));
            }, (OnError) error -> {
                //请求失败
               onFail(error.getErrorMsg(), new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       getCategoryList();
                   }
               });
            });

    }

}
