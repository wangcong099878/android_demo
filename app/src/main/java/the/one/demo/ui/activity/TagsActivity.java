package the.one.demo.ui.activity;

import android.view.View;

import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import the.one.base.base.activity.BaseActivity;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.R;
import the.one.demo.adapter.TagCloudAdapter;
import the.one.demo.bean.Tag;
import the.one.demo.ui.gank.GankActivity;
import the.one.demo.ui.sample.SampleIndexActivity;
import the.one.demo.ui.wallpaper.WallpaperActivity;


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
public class TagsActivity extends BaseActivity implements TagCloudAdapter.OnTagItemClickListener {

    @BindView(R.id.tag_cloud)
    TagCloudView mTagCloud;

    TagCloudAdapter adapter;

    private Tag TAG_GANK,TAG_SAMPLE,TAG_BINGO,TAG_WALLPAPER;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_thanks;
    }

    @Override
    protected boolean isExitActivity() {
        return true;
    }

    @Override
    protected void initView(View mRootView) {
        TAG_GANK = new Tag("Gank", GankActivity.class);
        TAG_SAMPLE = new Tag("Sample",SampleIndexActivity.class);
        TAG_BINGO = new Tag("Bingo",BingoActivity.class);
        TAG_WALLPAPER=  new Tag("Wallpaper",WallpaperActivity.class);

        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tags.add(TAG_GANK);
            tags.add(TAG_SAMPLE);
            tags.add(TAG_BINGO);
            tags.add(TAG_WALLPAPER);
        }
        adapter = new TagCloudAdapter(this, tags);
        adapter.setListener(this);
        mTagCloud.setAdapter(adapter);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onTagItemClick(Class tagClass) {
        startActivity(tagClass);
    }

}
