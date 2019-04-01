package the.one.demo.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import the.one.base.base.activity.BaseActivity;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.Constant;
import the.one.demo.R;
import the.one.demo.ui.adapter.GankAdapter;
import the.one.demo.ui.bean.GankBean;


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
 * @date 2019/3/22 0022
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class StickyTestActivity extends BaseActivity {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private GankAdapter adapter;
    private int page = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sticky;
    }

    @Override
    protected void initView(View mRootView) {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setNestedScrollingEnabled(false);
        adapter = new GankAdapter();
        recycleView.setAdapter(adapter);
        showLoadingPage();
        getData();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    public void getData() {
        Log.e(TAG, "getData: " );
        OkHttpUtils.get().url(Constant.GANK_CATEGORY + Constant.ANDROID + "/" + 10 + "/" + page).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (error) {
                        showEmptyPage("错误");
                    } else {
                        String personObject = jsonObject.getString("results");
                        List<GankBean> itemData = new Gson().fromJson(personObject,
                                new TypeToken<List<GankBean>>() {
                                }.getType());
                            adapter.setNewData(itemData);
                            showContentPage();
                    }

                } catch (JSONException e) {
                    showEmptyPage("解析错误");
                    e.printStackTrace();
                }
            }
        });
    }

}
