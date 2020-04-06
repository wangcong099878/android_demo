package the.one.aqtour.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

import butterknife.BindView;
import the.one.aqtour.R;
import the.one.aqtour.m3u8downloader.bean.M3U8Task;
import the.one.aqtour.m3u8downloader.server.EncryptM3U8Server;
import the.one.aqtour.m3u8downloader.utils.M3U8PathUtil;
import the.one.aqtour.widge.StandardTheVideoPlayer;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.base.util.glide.GlideUtil;

public class DownloadVideoPlayActivity extends GSYBaseDetailActivity<StandardTheVideoPlayer> {

    public static void startThisActivity(Activity activity,M3U8Task task){
        Intent intent = new Intent(activity,DownloadVideoPlayActivity.class);
        intent.putExtra(DataConstant.DATA,task);
        activity.startActivity(intent);
    }

    @BindView(R.id.video_player)
    StandardTheVideoPlayer mVideoPlayer;

    protected ImageView ivCover;
    private M3U8Task mTask;

    private EncryptM3U8Server m3u8Server = new EncryptM3U8Server();

    private String mPlayPath;

    @Override
    protected boolean isStatusBarLightMode() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_download_video_play;
    }

    @Override
    protected void initView(View mRootView) {
        mRootView.setBackgroundColor(getColorr(R.color.black));
        mTask = getIntent().getParcelableExtra(DataConstant.DATA);
        m3u8Server.execute();
        mPlayPath = M3U8PathUtil.getLocalPath(m3u8Server,mTask.getUrl());
        initVideo();
        initVideoBuilderMode();
        mVideoPlayer.startPlayLogic();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public StandardTheVideoPlayer getGSYVideoPlayer() {
        return mVideoPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        if (null == ivCover)
            ivCover = new ImageView(this);
        GlideUtil.load(this, mTask.getCover(), ivCover);
        return new GSYVideoOptionBuilder()
                .setThumbImageView(ivCover)
                .setUrl(mPlayPath)
                .setCacheWithPlay(false)
                .setVideoTitle(mTask.getFullName())
                .setIsTouchWiget(true)
                .setPlayTag(mPlayPath)
                .setRotateViewAuto(true)
                .setAutoFullWithSize(true)
                .setSeekRatio(3)
                .setLockLand(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        m3u8Server.decrypt();
    }

    @Override
    protected void onPause() {
        super.onPause();
        m3u8Server.encrypt();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        m3u8Server.finish();
    }

    @Override
    protected void doOnBackPressed() {
        if ( GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.doOnBackPressed();
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return false;
    }
}
