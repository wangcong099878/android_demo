package the.one.base.base.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIColorHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityOptionsCompat;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import the.one.base.BaseApplication;
import the.one.base.R;
import the.one.base.adapter.ImageWatchAdapter;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.base.model.Download;
import the.one.base.service.DownloadService;
import the.one.base.util.FileDirectoryUtil;
import the.one.base.util.ShareUtil;
import the.one.base.widge.PhotoViewPager;

/**
 * @author The one
 * @date 2018/11/5 0005
 * @describe 图片查看
 * @email 625805189@qq.com
 * @remark
 */
public class ImagePreviewActivity extends BaseActivity implements ImageWatchAdapter.OnPhotoClickListener,
        QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener, Observer<Boolean> {

    protected static final int DOWNLOAD = 1;
    protected static final int SHARE = 2;

    private View rootView;
    protected PhotoViewPager photoViewPager;
    protected TextView textView;

    protected ImageWatchAdapter mImageAdapter;
    protected List<String> mImageList;
    protected String mPath;
    protected int currentPosition;
    protected int SUM;
    protected int mTag;


    public static void startThisActivity(Activity activity, View image, String iconPath) {
        ArrayList<String> paths = new ArrayList<>();
        paths.add(iconPath);
        startThisActivity(activity, image, paths, 0);
    }

    public static void startThisActivity(Activity activity, View image, ArrayList<String> list, int position) {
        startThisActivity(activity, ImagePreviewActivity.class, image, list, position);
    }

    public static void startThisActivity(Activity activity, Class targetActivity, View image, ArrayList<String> list, int position) {
        Intent in = new Intent(activity, targetActivity);
        in.putStringArrayListExtra(DataConstant.DATA, list);
        in.putExtra(DataConstant.POSITION, position);
        if (null != image)
            activity.startActivity(in, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    image,
                    BaseApplication.getInstance().getString(R.string.image))
                    .toBundle());
        else
            activity.startActivity(in);
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_photo_watch;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView(View mRootView) {
        rootView = mRootView;
        photoViewPager = mRootView.findViewById(R.id.photo_view);
        textView = mRootView.findViewById(R.id.tv_number);
        Intent intent = getIntent();
        mImageList = getIntent().getStringArrayListExtra(DataConstant.DATA);
        SUM = mImageList.size();
        currentPosition = intent.getIntExtra(DataConstant.POSITION, -1);
        mImageAdapter = new ImageWatchAdapter(this, mImageList, this);
        photoViewPager.setAdapter(mImageAdapter);
        photoViewPager.setCurrentItem(currentPosition, false);
        textView.setText(currentPosition + 1 + "/" + SUM);
        photoViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (null != textView)
                    textView.setText(position + 1 + "/" + SUM);
            }
        });
    }

    public void setAlpha(float alpha) {
        int colorId = QMUIColorHelper.setColorAlpha(getColorr(R.color.we_chat_black), alpha);
        rootView.setBackgroundColor(colorId);
        if (alpha >= 1) {
            showView(textView);
        } else {
            goneView(textView);
        }
    }

    @Override
    public void onClick(String path, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    public boolean onLongClick(String path, int position) {
        mPath = path;
        Log.e(TAG, "onClick: " + mPath);
        showBottomSheetDialog();
        return true;
    }

    protected void showBottomSheetDialog() {
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(this);
        addItem(builder);
        builder.setOnSheetItemClickListener(this).build().show();
    }

    protected void addItem(QMUIBottomSheet.BottomGridSheetBuilder builder) {
        builder.addItem(R.drawable.icon_more_operation_save, "下载", DOWNLOAD, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
        builder.addItem(R.drawable.ic_share, "分享", SHARE, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
    }

    @Override
    public void onClick(QMUIBottomSheet dialog, View itemView) {
        mTag = (int) itemView.getTag();
        requestPermission();
        dialog.dismiss();
    }


    private void requestPermission() {
        final RxPermissions permissions = new RxPermissions(this);
        permissions
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET)
                .subscribe(this);
    }

    private String getDownloadFileName() {
        String suffix = ".gif";
        if (mPath.contains(".")) {
            int position = mPath.lastIndexOf(".");
            int difference = mPath.length() - position;
            if (difference > 2 && difference < 5) {
                suffix = mPath.substring(position);
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append("download_")
                .append(System.currentTimeMillis())
                .append(suffix);
        return sb.toString();
    }

    private void downPhoto() {
        Download download = new Download(mPath, "Pictures", getDownloadFileName());
        download.setImage(true);
        DownloadService.startDown(this, download);
    }

    public void download() {
        showLoadingDialog("");
        OkHttpUtils
                .get()
                .url(mPath)
                .tag(mPath)
                .build()
                .execute(new FileCallBack(FileDirectoryUtil.getCachePath(), getDownloadFileName()) {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("分享失败");
                        hideLoadingDialog();
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {

                    }

                    @Override
                    public void onResponse(File response, int id) {
                        hideLoadingDialog();
                        ShareUtil.shareImageFile(ImagePreviewActivity.this, response, "分享图片");
                    }
                });

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Boolean aBoolean) {
        if (aBoolean) {
            switch (mTag) {
                case DOWNLOAD:
                    downPhoto();
                    break;
                case SHARE:
                    download();
                    break;
            }
        } else {
            showToast("没有权限很难办事啊");
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImageAdapter != null) {
            mImageAdapter.closePage();
        }
    }

}
