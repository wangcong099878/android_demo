package the.one.demo.ui.fragment;

import android.view.View;

import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.R;
import the.one.demo.ui.activity.BingoActivity;
import the.one.demo.ui.activity.SampleActivity;
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
 * @date 2019/8/23 0023
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class TagsFragment extends BaseFragment implements TagCloudAdapter.OnTagItemClickListener {


    @BindView(R.id.tag_cloud)
    TagCloudView mTagCloud;

    TagCloudAdapter adapter;

    private String TAG_SAMPLE = "Sample";
    private String TAG_BINGO = "Bingo";

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_tags;
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return true;
    }

    @Override
    protected boolean isExitFragment() {
        return true;
    }

    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected void initView(View mRootView) {
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tags.add(TAG_SAMPLE);
            tags.add(TAG_BINGO);
        }
        adapter = new TagCloudAdapter(_mActivity, tags);
        adapter.setListener(this);
        mTagCloud.setAdapter(adapter);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onTagItemClick(String tag) {
       if(tag.equals(TAG_SAMPLE)){
            startActivity(SampleActivity.class);
        }else if(tag.equals(TAG_BINGO)){
            startActivity(BingoActivity.class);
        }
    }

}
