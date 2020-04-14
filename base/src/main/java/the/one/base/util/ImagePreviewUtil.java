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
import android.content.Context;
import android.view.View;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.view.listener.OnBigImageLongClickListener;

/**
 * @author The one
 * @date 2020/4/13 0013
 * @describe 这里面无法做权限判断，所以权限必须在外面就申请好
 * @email 625805189@qq.com
 * @remark
 */
public class ImagePreviewUtil {

    private static final String TAG = "ImagePreviewUtil";

    private static ImagePreviewUtil imagePreviewUtil;

    private ImagePreviewUtil (){}

    public static ImagePreviewUtil newInstance(){
        if(null == imagePreviewUtil){
            imagePreviewUtil = new ImagePreviewUtil();
        }
        return imagePreviewUtil;
    }

    public  void show(Context context, List<String> images, int position) {
        ImagePreview.getInstance()
                .setContext(context)
                .setIndex(position)
                .setShowDownButton(false)
                .setBigImageLongClickListener(new OnBigImageLongClickListener() {
                    @Override
                    public boolean onLongClick(Activity activity, View view, int position) {
                        DownloadSheetDialogUtil.show((AppCompatActivity) activity, images.get(position));
                        return true;
                    }
                })
                .setEnableDragClose(true)
                .setImageList(images)
                .start();
    }


}
