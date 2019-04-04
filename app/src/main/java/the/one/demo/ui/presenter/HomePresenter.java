package the.one.demo.ui.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.util.JsonUtil;
import the.one.demo.Constant;
import the.one.demo.ui.bean.GankBean;
import the.one.demo.ui.bean.HomeBean;
import the.one.demo.ui.view.HomeView;
import the.one.net.FailUtil;


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
    private static final String TAG = "HomePresenter";

    public static final int TYPE_TODAY = 0;
    public static final int TYPE_WELFARE = 1;

    public void getData( final int type) {
        String url = type == TYPE_TODAY?Constant.TODAY:Constant.GANK_CATEGORY + Constant.WELFARE + "/" + Constant.COUNT + "/1";
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (isViewAttached())
                    getView().showErrorPage(FailUtil.getFailString(e));
            }

            @Override
            public void onResponse(String response, int id) {
                if (isViewAttached()) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");
                        if (error) {
                            getView().showErrorPage("请求失败");
                        } else {
                            String result = jsonObject.getString("results");
                            if (type == TYPE_TODAY) {
                                HomeBean homeBean = JsonUtil.fromJson(result,HomeBean.class);
                                getView().onTodayComplete(homeBean);
                            } else{
                                List<GankBean> gankBeans = new Gson().fromJson(result,
                                        new TypeToken<List<GankBean>>() {
                                        }.getType());
                                getView().onWelfareComplete(gankBeans);
                            }
                        }
                    } catch (JSONException e) {
                        getView().showErrorPage("解析错误");
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
