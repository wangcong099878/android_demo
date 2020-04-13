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

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.view.listener.OnBigImageLongClickListener;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import the.one.base.R;
import the.one.base.model.Download;
import the.one.base.service.DownloadService;

/**
 * @author The one
 * @date 2020/4/13 0013
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ImagePreviewUtil {

    private static final String TAG = "ImagePreviewUtil";

    public  void show(Context context, List<String> images, int position) {
        ImagePreview.getInstance()
                .setContext(context)
                .setIndex(position)
                .setShowDownButton(false)
                .setBigImageLongClickListener(new OnBigImageLongClickListener() {
                    @Override
                    public boolean onLongClick(Activity activity, View view, int position) {
                        showBottomSheetDialog((AppCompatActivity) activity, images.get(position));
                        return true;
                    }
                })
                .setEnableDragClose(true)
                .setImageList(images)
                .start();
    }

    private  void showBottomSheetDialog(AppCompatActivity activity, String imageUrl) {
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(activity);
        builder.addItem(R.drawable.icon_more_operation_save, "下载", 1, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
//        builder.addItem(R.drawable.ic_share, "分享", 2, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
        builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
            @Override
            public void onClick(QMUIBottomSheet dialog, View itemView) {
                requestPermission(activity, imageUrl);
                dialog.dismiss();
            }
        }).build().show();
    }

    private  void requestPermission(AppCompatActivity activity, String imageUrl) {
        final RxPermissions permissions = new RxPermissions(activity);
        permissions
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.e(TAG, "onNext: "+aBoolean );
                        if (aBoolean) {
                            Download download = new Download(imageUrl, "Pictures", getDownloadFileName(imageUrl));
                            download.setImage(true);
                            DownloadService.startDown(activity, download);
                            ToastUtil.showToast("开始下载");
                        } else {
                            ToastUtil.showToast(activity.getString(R.string.no_permissioin_tips));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " );
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: " );
                    }
                });
    }

    private  String getDownloadFileName(String mUrl) {
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
