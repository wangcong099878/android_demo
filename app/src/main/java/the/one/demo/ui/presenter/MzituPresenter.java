package the.one.demo.ui.presenter;

import android.util.Log;

import com.rxjava.rxlife.RxLife;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.Call;
import rxhttp.wrapper.param.RxHttp;
import the.one.base.base.presenter.BaseDataPresenter;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.base.view.BaseDataView;
import the.one.demo.bean.Mzitu;
import the.one.demo.constant.MzituConstant;


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
    public void getData(String url, final int page) {
        final String fakeRefer = MzituConstant.WELFARE_BASE_URL + url + "/";
        if(MzituConstant.isNoDetail(url)){
            url = fakeRefer + "/comment-page-"+page +"/#comments";
        }else{
            url = fakeRefer + "page/" + page;
        }

        RxHttp.get(url)
            .asString()
            .observeOn(AndroidSchedulers.mainThread())
            .as(RxLife.as(this))
            .subscribe(s -> {
                //请求成功
                onResponse(s,fakeRefer);
            }, throwable -> {
                //请求失败
               onFail(throwable.getMessage());
            });
    }

    public void onResponse(String response, String fakeRefer) {
        if (isViewAttached()) {
            List<Mzitu> mzitus = new ArrayList<>();
            Document doc = Jsoup.parse(response);
            Elements items = doc.select("div.postlist").select("li");
            for (Element element : items) {
                Mzitu mzitu = new Mzitu();
                mzitu.setRefer(fakeRefer);
                Element imageInfo = element.select("img").first();
                mzitu.setUrl(imageInfo.attr("data-original"));
                mzitu.setLink(element.select("a[href]").attr("href"));
                try {
                    mzitu.setWidth(Integer.parseInt(imageInfo.attr("width")));
                    mzitu.setHeight(Integer.parseInt(imageInfo.attr("height")));
                    mzitu.setTitle(imageInfo.attr("alt"));
                    mzitu.setDate(element.select("span").last().html());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mzitus.add(mzitu);
            }
            onComplete(mzitus);
        }
    }


    public void getDetailData(final String url) {
        RxHttp.get(url)
            .asString()
            .observeOn(AndroidSchedulers.mainThread())
            .as(RxLife.as(this))
            .subscribe(s -> {
                //请求成功
                onDetailResponse(s,url);
            }, throwable -> {
                //请求失败
               onFail(throwable.getMessage());
            });
    }

    private void onDetailResponse(String response,String url) {
        if (isViewAttached()) {
            List<Mzitu> gankBeans = new ArrayList<>();
            Document doc = Jsoup.parse(response);
            String firstUrl = doc.select("div.main-image").select("img").attr("src");
            String baseUrl = firstUrl.substring(0, firstUrl.length() - 6);
            try {
                Elements pageElement = doc.select("div.pagenavi").select("span");
                Element maxPageElement = pageElement.get(pageElement.size() - 2);
                int maxPage = Integer.parseInt(maxPageElement.html());
                for (int i = 1; i < maxPage; i++) {
                    String index = String.valueOf(i);
                    if (i < 10)
                        index = "0" + index;
                    String imageUrl = baseUrl + index + ".jpg";
                    gankBeans.add(getGankBean(url,imageUrl,i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            onComplete(gankBeans);
            getView().onNormal();
        }
    }

    private Mzitu getGankBean(String url,String imageUrl, int page){
        String refer = url +"/"+page;
        return new Mzitu(imageUrl,refer);
    }
}
