package the.one.base.util;

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
import the.one.base.ui.activity.ImagePreviewActivity;

/**
 * @author The one
 * @date 2020/4/16 0016
 * @describe 图片预览跳转工具
 * @email 625805189@qq.com
 * @remark
 */
public class ImagePreviewUtil {

    private static long mLastClickTime;
    private static final int MIN_DOUBLE_CLICK_TIME = 1500;

    public static void start(Activity activity, String path) {
        List<ImagePreviewBean> previewBeans = new ArrayList<>();
        previewBeans.add(new ImagePreviewBean(path));
        start(activity, ImagePreviewActivity.class, new ImagePreviewEvent(previewBeans));
    }

    public static void start(Activity activity, List<String> paths, int position) {
        List<ImagePreviewBean> previewBeans = new ArrayList<>();
        for (String path : paths) {
            previewBeans.add(new ImagePreviewBean(path));
        }
        start(activity, ImagePreviewActivity.class, new ImagePreviewEvent(previewBeans, position));
    }

    public static void start(Activity activity, ImagePreviewEvent previewEvent) {
        start(activity, ImagePreviewActivity.class, previewEvent);
    }

    public static void start(Activity activity, Class targetActivity, ImagePreviewEvent previewEvent) {
        if (null == activity || activity.isFinishing() || activity.isDestroyed() || System.currentTimeMillis() - mLastClickTime <= MIN_DOUBLE_CLICK_TIME)
            return;
        mLastClickTime = System.currentTimeMillis();
        Intent in = new Intent(activity, targetActivity);
        in.putExtra(DataConstant.DATA, previewEvent);
        activity.startActivity(in);
        activity.overridePendingTransition(R.anim.scale_enter, R.anim.slide_still);
    }

}
