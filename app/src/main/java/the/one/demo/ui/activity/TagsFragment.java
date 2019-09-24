package the.one.demo.ui.activity;

import android.view.View;

import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.R;
import the.one.demo.adapter.TagCloudAdapter;
import the.one.demo.ui.gank.IndexFragment;
import the.one.demo.ui.sample.fragment.SampleFragment;
import the.one.demo.ui.wallpaper.WallpaperFragment;


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

    private String TAG_GANK = "Gank";
    private String TAG_SAMPLE = "Sample";
    private String TAG_WALLPAPER = "Wallpaper";
    private String TAG_BINGO = "Bingo";

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_thanks;
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
            tags.add(TAG_GANK);
            tags.add(TAG_SAMPLE);
            tags.add(TAG_BINGO);
            tags.add(TAG_WALLPAPER);
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
        if(tag.equals(TAG_GANK)){
            startFragment(new IndexFragment());
        }else if(tag.equals(TAG_SAMPLE)){
            startFragment(new SampleFragment());
        }else if(tag.equals(TAG_BINGO)){
            startActivity(BingoActivity.class);
        }else if(tag.equals(TAG_WALLPAPER)){
            startFragment(new WallpaperFragment());
        }
    }

}