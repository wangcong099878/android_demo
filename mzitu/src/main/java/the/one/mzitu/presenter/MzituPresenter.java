package the.one.mzitu.presenter;

import com.rxjava.rxlife.RxLife;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;
import the.one.base.ui.presenter.BaseDataPresenter;
import the.one.mzitu.bean.Mzitu;
import the.one.mzitu.constant.MzituConstant;
import the.one.mzitu.util.MzituParseUtil;


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

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";

    public void getCategoryData(String url, final int page) {
        final String fakeRefer = MzituConstant.WELFARE_BASE_URL + url + "/";
        if (MzituConstant.isNoDetail(url)) {
            url = fakeRefer + "/comment-page-" + page + "/#comments";
        } else {
            url = fakeRefer + "page/" + page + "/";
        }
        get(url, s -> {
            onComplete(MzituParseUtil.parseMzituCategory(s, fakeRefer));
        });
    }


    public void getDetailData(final String url) {
        get(url, s -> {
            onComplete(MzituParseUtil.parseMzituDetail(s, url));
            getView().onNormal();
        });
    }

    private void get(String url, Consumer<String> onNext) {
        RxHttp.get(url)
                .addHeader("Referer", url)
                .addHeader("User-Agent", USER_AGENT)
                .asString()
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLife.as(this))
                .subscribe(onNext, throwable -> {
                    //请求失败
                    onFail(throwable.getMessage());
                });
    }

}
