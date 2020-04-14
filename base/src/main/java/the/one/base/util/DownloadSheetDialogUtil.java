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

import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import the.one.base.R;
import the.one.base.model.Download;
import the.one.base.model.PopupItem;
import the.one.base.service.DownloadService;

/**
 * @author The one
 * @date 2020/4/14 0014
 * @describe 包含下载功能的弹窗
 * @email 625805189@qq.com
 * @remark 这里面无法做权限判断，所以权限必须在外面就申请好
 */
public class DownloadSheetDialogUtil {

    public static void show(AppCompatActivity activity, String imageUrl) {
        List<PopupItem> items = new ArrayList<>();
        items.add(new PopupItem("下载",R.drawable.icon_more_operation_save));
        QMUIBottomSheetUtil.showGridBottomSheet(activity, items, new QMUIBottomSheetUtil.OnSheetItemClickListener() {
            @Override
            public void onClick(int position, String title, QMUIBottomSheet dialog, View itemView) {
                Download download = new Download(imageUrl, "Pictures", getDownloadFileName(imageUrl));
                download.setImage(true);
                DownloadService.startDown(activity, download);
                ToastUtil.showToast("开始下载");
                dialog.dismiss();
            }
        }).show();
    }

    private static String getDownloadFileName(String mUrl) {
        String suffix = ".gif";
        if (mUrl.contains(".")) {
            int position = mUrl.lastIndexOf(".");
            int difference = mUrl.length() - position;
            if (difference > 2 && difference < 5) {
                suffix = mUrl.substring(position);
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append("download_")
                .append(System.currentTimeMillis())
                .append(suffix);
        return sb.toString();
    }

}
