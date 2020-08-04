package the.one.gank.ui.fragment;

import com.rxjava.rxlife.RxLife;

import rxhttp.wrapper.param.RxHttp;
import the.one.base.Interface.OnError;
import the.one.base.ui.presenter.BasePresenter;
import the.one.gank.bean.GankBean;
import the.one.gank.constant.NetUrlConstantV2;


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
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class GankV2Fragment extends BaseGankFragment {

    @Override
    protected boolean isWelfare() {
        return mType.equals(NetUrlConstantV2.CATEGORY_GIRL);
    }

    @Override
    protected void requestServer() {
        RxHttp.get(NetUrlConstantV2.getCategoryData(mCategory,mType,page))
                .setDomainTov2UrlIfAbsent()
                .asResponseList(GankBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(response -> {
                    //请求成功
                    onComplete(response.getData(),response.getPageInfo());
                }, (OnError) error -> {
                    //请求失败
                    onFail(error.getErrorMsg());
                });
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
