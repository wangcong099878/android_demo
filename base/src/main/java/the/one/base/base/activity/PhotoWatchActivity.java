package the.one.base.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import the.one.base.R;
import the.one.base.adapter.ImageWatchAdapter;
import the.one.base.widge.PhotoViewPager;

/**
 * @author The one
 * @date 2018/11/5 0005
 * @describe 图片查看
 * @email 625805189@qq.com
 * @remark
 */
public class PhotoWatchActivity extends BaseActivity {

    private static final String DATA = "data";
    private static final String TITLE = "title";
    private static final String POSITION = "position";

    PhotoViewPager photoViewPager;
    TextView textView;

    private int currentPosition;
    private ImageWatchAdapter adapter;
    private int SUM;
    private List<String> imageLists;

    public static void startThisActivity(Activity activity, ArrayList<String> list, int position) {
        Intent in = new Intent(activity, PhotoWatchActivity.class);
        in.putStringArrayListExtra(DATA, list);
        in.putExtra(POSITION, position);
        activity.startActivity(in);
    }

    @Override
    protected boolean showTitleBar() {
        return false;
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
        imageLists = getIntent().getStringArrayListExtra(DATA);
        SUM = imageLists.size();
        currentPosition = intent.getIntExtra("position", -1);
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
