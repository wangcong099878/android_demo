package the.one.base.base.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import the.one.base.BaseApplication;
import the.one.base.R;
import the.one.base.adapter.ImageWatchAdapter;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.base.widge.PhotoViewPager;

import static the.one.base.BaseApplication.context;

/**
 * @author The one
 * @date 2018/11/5 0005
 * @describe 图片查看
 * @email 625805189@qq.com
 * @remark
 */
public class PhotoWatchActivity extends BaseActivity {

    PhotoViewPager photoViewPager;
    TextView textView;

    private int currentPosition;
    private ImageWatchAdapter adapter;
    private int SUM;
    private List<String> imageLists;

    public static void startThisActivity(Activity activity,View image, ArrayList<String> list, int position) {
        Intent in = new Intent(activity, PhotoWatchActivity.class);
        in.putStringArrayListExtra(DataConstant.DATA, list);
        in.putExtra(DataConstant.POSITION, position);
        activity.startActivity(in, ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                image,
                context.getString(R.string.image))
                .toBundle());
    }

    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_photo_watch;
    }

    @Override
    protected void initView(View mRootView) {
        photoViewPager = mRootView.findViewById(R.id.photo_view);
        textView = mRootView.findViewById(R.id.tv_number);
        Intent intent = getIntent();
        imageLists = getIntent().getStringArrayListExtra(DataConstant.DATA);
        SUM = imageLists.size();
        currentPosition = intent.getIntExtra(DataConstant.POSITION, -1);
        adapter = new ImageWatchAdapter(imageLists, this);
        photoViewPager.setAdapter(adapter);
        photoViewPager.setCurrentItem(currentPosition, false);
        textView.setText(currentPosition+1 + "/" + SUM);
        photoViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                textView.setText(position+1 + "/" + SUM);
            }
        });
    }


}
