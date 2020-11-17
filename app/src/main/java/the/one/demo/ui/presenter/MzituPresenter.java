package the.one.demo.ui.presenter;

import com.rxjava.rxlife.RxLife;

import rxhttp.wrapper.cahce.CacheMode;
import rxhttp.wrapper.param.RxHttp;
import the.one.base.Interface.OnError;
import the.one.base.ui.presenter.BaseDataPresenter;
import the.one.demo.bean.Mzitu;
import the.one.demo.util.MzituParseUtil;


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
public class MzituPresenter extends BaseDataPresenter<Mzitu> {

    public static final String WELFARE_BASE_URL = "https://www.mzitu.com/zipai/";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";

    public void getCategoryData(final int page) {
        String url = WELFARE_BASE_URL + "/comment-page-" + page + "/#comments";
        RxHttp.get(url)
                .addHeader("Referer", url)
                .addHeader("User-Agent", USER_AGENT)
                .setCacheMode(CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK)
                .asString()
                .as(RxLife.asOnMain(this))
                .subscribe( s -> {
                    onComplete(MzituParseUtil.parseMzituCategory(s, WELFARE_BASE_URL));
                }, (OnError) error -> {
                    //请求失败
                    getView().onFail(error.getErrorMsg());
                });
    }

}
