package the.one.demo.ui.activity;

import android.view.View;

import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import the.one.base.base.activity.BaseActivity;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.R;
import the.one.demo.ui.adapter.TagCloudAdapter;


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
public class TagActivity extends BaseActivity {

    @BindView(R.id.tag_cloud)
    TagCloudView mTagCloud;

    TagCloudAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tag;
    }

    @Override
    protected void initView(View mRootView) {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            datas.add(i%2==0?"魅族":"MEIZU");
        }
        adapter = new TagCloudAdapter(datas);
        mTagCloud.setAdapter(adapter);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
