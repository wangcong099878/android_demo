package the.one.gank.ui.presenter;

import android.view.View;

import com.rxjava.rxlife.RxLife;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.cahce.CacheMode;
import rxhttp.wrapper.param.RxHttp;
import the.one.base.Interface.OnError;
import the.one.base.ui.presenter.BasePresenter;
import the.one.gank.bean.BannerBean;
import the.one.gank.bean.GankBean;
import the.one.gank.bean.HomeBean;
import the.one.gank.constant.NetUrlConstant;
import the.one.gank.constant.NetUrlConstantV2;
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
                .as(RxLife.asOnMain(this))
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

    /**
     * 获取首页轮播图  GANK V2 接口
     */
    public void getBanner() {
        RxHttp.get(NetUrlConstantV2.BANNER)
                .setDomainTov2UrlIfAbsent()
                .asResponseList(BannerBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(s -> {
                    //请求成功
                    getView().onBannerComplete(s.getData());
                }, (OnError) error  -> {
                    //请求失败
                    showFailTips("获取Banner信息失败"+error.getErrorMsg());
                });

    }

    /**
     * 获取本周热门 GANK V2 接口
     * @param hot_type 可接受参数 views（浏览数） | likes（点赞数） | comments（评论数）
     * @param category category 可接受参数 Article | GanHuo | Girl
     * @return
     */
    public void getWeekHot(boolean isFirst,String hot_type, String category ) {
        RxHttp.get(NetUrlConstantV2.getHotUrl(hot_type,category))
                .setCacheMode(isFirst? CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK:CacheMode.NETWORK_SUCCESS_WRITE_CACHE)
                .setDomainTov2UrlIfAbsent()
                .asResponseList(GankBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(s -> {
                    //请求成功
                    getView().onHotComplete(s.getData());
                }, (OnError) error  -> {
                    //请求失败
                    getView().onError(error.getErrorMsg());
                });

    }

}
