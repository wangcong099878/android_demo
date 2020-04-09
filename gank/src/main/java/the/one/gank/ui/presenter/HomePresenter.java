package the.one.gank.ui.presenter;

import android.view.View;

import com.rxjava.rxlife.RxLife;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;
import the.one.base.Interface.OnError;
import the.one.base.base.presenter.BasePresenter;
import the.one.gank.bean.BannerBean;
import the.one.gank.bean.HomeBean;
import the.one.gank.constant.NetUrlConstant;
import the.one.gank.ui.view.HomeView;


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
 * @date 2019/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class HomePresenter extends BasePresenter<HomeView> {


    public void getTodayData() {
        RxHttp.get(NetUrlConstant.TODAY)
                .asResponseOld(HomeBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLife.as(this))
                .subscribe(s -> {
                    //请求成功
                    getView().onTodayComplete(s);
                }, (OnError) error -> {
                    //请求失败
                    showErrorPage(error.getErrorMsg(), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getView().showLoadingPage();
                            getTodayData();
                        }
                    });
                });
    }

    public void getBanner() {
        RxHttp.get("https://gank.io/api/v2/banners")
                .asResponseList(BannerBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLife.as(this))
                .subscribe(s -> {
                    //请求成功
                    getView().onWelfareComplete(s.getData());
                }, (OnError) error  -> {
                    //请求失败
                    showFailTips("获取Banner信息失败"+error.getErrorMsg());
                });

    }

}
