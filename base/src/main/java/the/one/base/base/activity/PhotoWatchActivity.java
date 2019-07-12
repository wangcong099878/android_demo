package the.one.base.base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import the.one.base.R;
import the.one.base.adapter.ImageWatchAdapter;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.base.util.FileDirectoryUtil;
import the.one.base.widge.PhotoViewPager;
import the.one.net.FailUtil;

import static the.one.base.BaseApplication.context;

/**
 * @author The one
 * @date 2018/11/5 0005
 * @describe 图片查看
 * @email 625805189@qq.com
 * @remark
 */
public class PhotoWatchActivity extends BaseActivity implements ImageWatchAdapter.OnPhotoLongClickListener,QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener {

    PhotoViewPager photoViewPager;
    TextView textView;

    private int currentPosition;
    private ImageWatchAdapter adapter;
    private int SUM;
    private List<String> imageLists;

    private String mPath;

    public static void startThisActivity(Activity activity, View image, ArrayList<String> list, int position) {
        Intent in = new Intent(activity, PhotoWatchActivity.class);
        in.putStringArrayListExtra(DataConstant.DATA, list);
        in.putExtra(DataConstant.POSITION, position);
        if (null != image)
            activity.startActivity(in, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    image,
                    context.getString(R.string.image))
                    .toBundle());
        else
            activity.startActivity(in);
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
        adapter = new ImageWatchAdapter(imageLists, this, this);
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
    public void onLongClick(String path, int position) {
        mPath= path;
        showBottomSheetDialog();
    }

    protected void showBottomSheetDialog() {
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(this);
        addItem(builder);
        builder.setOnSheetItemClickListener(this).build().show();
    }

    protected void addItem(QMUIBottomSheet.BottomGridSheetBuilder builder){
        builder.addItem(R.drawable.icon_more_operation_save, "下载",0, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
    }

    @Override
    public void onClick(QMUIBottomSheet dialog, View itemView) {
        dialog.dismiss();
        downPhoto();
    }

    private int oldPercent ;

    private void downPhoto(){
        String name = "pic_"+System.currentTimeMillis()+".png";
        showProgressDialog(0,100,"下载中");
        OkHttpUtils
                .get()
                .url(mPath)
                .tag(TAG)
                .build()
                .execute(new FileCallBack(FileDirectoryUtil.getDownloadPath(),name) {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideProgressDialog();
                       showFailTips(FailUtil.getFailString(e));
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        // 如果进度与之前进度相等，则不更新，如果更新太频繁，否则会造成界面卡顿
                        int percent = (int) (progress * 100);
                        if (percent != oldPercent) {
                            oldPercent = percent;
                            progressDialog.setProgress(percent,100);
                        }
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        hideProgressDialog();
                        oldPercent = 0;
                        showSuccessTips("下载成功");
                    }
                });
    }

}
