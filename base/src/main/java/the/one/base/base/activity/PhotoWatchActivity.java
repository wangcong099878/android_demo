package the.one.base.base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.viewpager.widget.ViewPager;

import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
public class PhotoWatchActivity extends BaseActivity implements ImageWatchAdapter.OnPhotoClickListener, QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener {

    protected static final int DOWNLOAD = 1;
    protected static final int SHARE = 2;

    protected PhotoViewPager photoViewPager;
    protected TextView textView;

    protected ImageWatchAdapter adapter;
    protected List<String> imageLists;
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
        startThisActivity(activity,PhotoWatchActivity.class,image,list,position);
    }

    public static void startThisActivity(Activity activity,Class targetActivity, View image, ArrayList<String> list, int position) {
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
        photoViewPager = mRootView.findViewById(R.id.photo_view);
        textView = mRootView.findViewById(R.id.tv_number);
        Intent intent = getIntent();
        imageLists = getIntent().getStringArrayListExtra(DataConstant.DATA);
        SUM = imageLists.size();
        currentPosition = intent.getIntExtra(DataConstant.POSITION, -1);
        adapter = new ImageWatchAdapter(imageLists, this);
        photoViewPager.setAdapter(adapter);
        photoViewPager.setCurrentItem(currentPosition, false);
        textView.setText(currentPosition + 1 + "/" + SUM);
        photoViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                textView.setText(position + 1 + "/" + SUM);
            }
        });
    }

    @Override
    public void onClick(String path, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }else{
            finish();
        }
    }

    @Override
    public void onLongClick(String path, int position) {
        mPath = path;
        showBottomSheetDialog();
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
        switch (mTag) {
            case DOWNLOAD:
                downPhoto();
                break;
            case SHARE:
                download();
                break;
        }
        dialog.dismiss();
    }

    private String getDownloadFileName() {
        String suffix = "jpg";
        if (mPath.contains(".")) {
            int position = mPath.lastIndexOf(".");
            int length = mPath.length();
            if (position != length - 4 && position != length - 5) {
                suffix ="gif";
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append("pic")
                .append("_")
                .append(System.currentTimeMillis())
                .append(".")
                .append(suffix);
        return sb.toString();
    }

    private void downPhoto() {
        Download download = new Download(mPath,"Pictures", getDownloadFileName());
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
                        ShareUtil.shareImageFile(PhotoWatchActivity.this, response,"分享图片");
                    }
                });

    }

    @Override
    protected void onDestroy() {
        if(null != adapter){
            adapter.clear();
        }
        super.onDestroy();
    }
}
