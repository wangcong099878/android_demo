package the.one.base.ui.activity;

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

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import the.one.base.R;
import the.one.base.constant.DataConstant;
import the.one.base.event.ImagePreviewEvent;
import the.one.base.model.ImagePreviewBean;
import the.one.base.ui.fragment.BaseFragment;
import the.one.base.ui.fragment.ImagePreviewFragment;

/**
 * @author The one
 * @date 2020/4/15 0015
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ImagePreviewActivity2 extends BaseFragmentActivity {

    private static long mLastClickTime;
    private static final int MIN_DOUBLE_CLICK_TIME = 1500;

    public static void startThisActivity(Activity activity, ImagePreviewEvent previewEvent) {
        startThisActivity(activity, ImagePreviewActivity2.class, previewEvent);
    }

    public static void startThisActivity(Activity activity, Class targetActivity, ImagePreviewEvent previewEvent) {
        if (null == activity || activity.isFinishing() || activity.isDestroyed() || System.currentTimeMillis() - mLastClickTime <= MIN_DOUBLE_CLICK_TIME)
            return;
        mLastClickTime = System.currentTimeMillis();
        Intent in = new Intent(activity, targetActivity);
        in.putExtra(DataConstant.DATA, previewEvent);
        activity.startActivity(in);
        activity.overridePendingTransition(R.anim.scale_enter, R.anim.slide_still);
    }

    @Override
    protected BaseFragment getFirstFragment() {
        Intent intent = getIntent();
        if(null == intent) finish();
        return ImagePreviewFragment.newInstance(intent.getParcelableExtra(DataConstant.DATA));
    }


    public static List<ImagePreviewBean> parse(List<String> paths){
        List<ImagePreviewBean> images = new ArrayList<>();
        for (String path : paths) {
            images.add(new ImagePreviewBean(path));
        }
        return images;
    }
}
