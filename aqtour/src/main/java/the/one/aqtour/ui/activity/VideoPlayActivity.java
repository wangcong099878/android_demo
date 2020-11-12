package the.one.aqtour.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cb.ratingbar.CBRatingBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import the.one.aqtour.R;
import the.one.aqtour.bean.QSPSeries;
import the.one.aqtour.bean.QSPVideo;
import the.one.aqtour.bean.QSPVideoSection;
import the.one.aqtour.constant.QSPConstant;
import the.one.aqtour.m3u8downloader.M3U8Downloader;
import the.one.aqtour.m3u8downloader.server.EncryptM3U8Server;
import the.one.aqtour.m3u8downloader.utils.M3U8PathUtil;
import the.one.aqtour.ui.adapter.QSPSeriesAdapter;
import the.one.aqtour.ui.adapter.QSPVideoAdapter;
import the.one.aqtour.ui.presenter.QSPVideoDetailPresenter;
import the.one.aqtour.ui.view.QSPVideoDetailView;
import the.one.aqtour.util.LitePalUtil;
import the.one.aqtour.widge.StandardTheVideoPlayer;
import the.one.base.constant.DataConstant;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.glide.GlideUtil;
import the.one.base.widge.MyTopBarLayout;
import the.one.base.widge.StatusLayout;
import the.one.base.widge.TheCheckBox;
import the.one.base.widge.ThePopupWindow;
import the.one.base.widge.decoration.SpacesItemDecoration;

public class VideoPlayActivity extends GSYBaseDetailActivity<StandardTheVideoPlayer> implements QSPVideoDetailView {

    public static void startThisActivity(Activity activity, QSPVideo video) {
        Intent intent = new Intent(activity, VideoPlayActivity.class);
        intent.putExtra(DataConstant.DATA, video);
        activity.startActivity(intent);
    }

    @BindView(R.id.rootView)
    View mRootView;
    @BindView(R.id.detail_player)
    StandardTheVideoPlayer mVideoPlayer;
    @BindView(R.id.status_layout)
    StatusLayout mStatusLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.top_layout)
    MyTopBarLayout mTopLayout;

    protected ImageView ivCover;

    protected TextView mTvTitle, mTvType, mTvIntroduce, mShowIntroduce;
    protected QMUIAlphaImageButton mIbDownload;
    protected TheCheckBox mCbCollection;
    protected CBRatingBar mRatingBar;
    protected TextView mSeriesName;
    protected TextView mTotalSeries;
    protected RecyclerView mRcSeries;
    protected ThePopupWindow mVideoInfoPop, mSeriesPopup;

    protected QSPVideoAdapter mVideoAdapter;
    private QSPSeriesAdapter mSeriesAdapter;
    private QSPSeriesAdapter mSeriesPopAdapter;

    private QSPVideoDetailPresenter mPresenter;

    private EncryptM3U8Server m3u8Server = new EncryptM3U8Server();

    protected QSPVideo mVideo;
    protected String mPlayPath = "";
    private boolean isDownload;



    protected String getBaseUrl() {
        return QSPConstant.BASE_URL;
    }

    protected String getSeriesTypeName() {
        return "剧集";
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_qsp_play;
    }

    @Override
    public BasePresenter getPresenter() {
        Intent intent = getIntent();
        mVideo = intent.getParcelableExtra(DataConstant.DATA);
        if (null == mVideo) finish();
        return mPresenter = new QSPVideoDetailPresenter();
    }

    @Override
    protected void initView(View mRootView) {
        initTopBar();
        initView();
        initVideoPlayer();
        initData();
    }

    private void initData() {
        if(null == mVideo) return;
        if (null == ivCover)
            ivCover = new ImageView(this);
        GlideUtil.load(this, mVideo.cover, ivCover);
        mVideoPlayer.release();
        mVideoPlayer.onNormal();
        initVideoBuilderMode();
        initVideo();
        initVideoData();
        getVideoDetail();
    }

    private void initTopBar() {
        mTopLayout.setBackgroundAlpha(0);
        QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
        builder.background(R.attr.video_play_topbar_bg);
        QMUISkinHelper.setSkinValue(mTopLayout,builder);
        mTopLayout.getTopBar().addLeftImageButton(R.drawable.mz_titlebar_ic_back_light,false, R.id.topbar_left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        mVideoAdapter = new QSPVideoAdapter();
        mVideoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                QSPVideoSection videoSection = (QSPVideoSection) adapter.getItem(position);
                mVideo = videoSection.t;
                initData();
            }
        });

        initVideoInfo();

        mRecycleView.setLayoutManager(new GridLayoutManager(this, 2));
        SpacesItemDecoration decoration = new SpacesItemDecoration(2,mVideoAdapter.getHeaderLayoutCount(),QMUIDisplayHelper.dp2px(this, 12));
        mRecycleView.addItemDecoration(decoration);
        mRecycleView.setAdapter(mVideoAdapter);

    }

    private void initVideoInfo() {
        View infoView = LayoutInflater.from(this).inflate(R.layout.custom_play_video_info_layout, null);
        mTvTitle = infoView.findViewById(R.id.tv_title);
        mTvType = infoView.findViewById(R.id.tv_type);
        mRatingBar = infoView.findViewById(R.id.rating_bar);
        mIbDownload = infoView.findViewById(R.id.ib_download);
        mCbCollection = infoView.findViewById(R.id.cb_collection);
        mSeriesName = infoView.findViewById(R.id.series_type_name);
        mTotalSeries = infoView.findViewById(R.id.total_series);
        mRcSeries = infoView.findViewById(R.id.rc_series);

        mSeriesName.setText(getSeriesTypeName());
        mIbDownload.setEnabled(false);
        mIbDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.downloadVideo(mPlayPath, mVideo.cover, getTitleName(), getSeriesName());
            }
        });
        mCbCollection.setIsCheckDrawable(R.drawable.ic_collection_select);
        mCbCollection.setUnCheckDrawable(R.drawable.ic_collection_normal);
        mCbCollection.setOnCheckChangedListener(new TheCheckBox.OnCheckChangedListener() {
            @Override
            public void onCheckChanged(boolean check) {
                mPresenter.collectionVideo(VideoPlayActivity.this, mCbCollection, mVideo, getBaseUrl());
            }
        });

        mShowIntroduce = infoView.findViewById(R.id.tv_introduce);
        mShowIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查看简介
                showVideoIntroducePopup();
            }
        });

        mTotalSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 查看全部剧集内容
                showSeriesPopup();
            }
        });

        goneView(mRatingBar, mShowIntroduce, mCbCollection, mIbDownload, mTvType);

        mVideoAdapter.addHeaderView(infoView);
        initVideoSeries();
    }

    private void initVideoSeries() {
        mRcSeries.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mSeriesAdapter = new QSPSeriesAdapter(R.layout.item_video_series, mRcSeries);
        mSeriesAdapter.setOnItemChildClickListener(seriesClildClickListener);
        mRcSeries.setAdapter(mSeriesAdapter);
    }


    private void initVideoPlayer() {
        mVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        mVideoPlayer.getBackButton().setVisibility(View.GONE);
        initVideo();
    }


    @SuppressLint("SetTextI18n")
    private void initVideoData() {
        mTvTitle.setText(mVideo.name);
        mTvType.setText(mVideo.type);
        mCbCollection.setCheck(LitePalUtil.isExist(mVideo));
        if (mVideo.score == 0) {
            mVideo.score = (int) (Math.random() * (10) + 1);
        }
        mRatingBar.setStarProgress(mVideo.score);
        String type = mVideo.getType();
        if (!TextUtils.isEmpty(type)) {
            mTvType.setText(type);
        }

        mSeriesAdapter.setNewInstance(mVideo.series);

        if (null != mVideo && null != mVideo.series)
            mTotalSeries.setText(mVideo.series.size() + " >");
        mVideoAdapter.setNewInstance(mVideo.recommends);

        if (!TextUtils.isEmpty(mVideo.introduce)) {
            showView(mShowIntroduce);
        } else {
            goneView(mShowIntroduce);
        }
        showView(mTvType, mRatingBar, mCbCollection, mIbDownload);

        mRecycleView.scrollToPosition(0);
    }

    /**
     * 获取视频详情
     */
    private void getVideoDetail() {
        mPresenter.getVideoDetail(mVideo);
    }

    /**
     * 获取剧集的播放地址
     *
     * @param position
     */
    protected void getSeriesPlayPath(int position) {
        if (mSeriesAdapter.getSelect() == position) {
            Log.e(TAG, "getSeriesPlayPath: " +position);
            return;
        }
        QSPSeries series = (QSPSeries) mSeriesAdapter.getItem(position);
        if (null == series) {
            Log.e(TAG, "getSeriesPlayPath:  null == series" );
            return;
        }
        Log.e(TAG, "getSeriesPlayPath: "+series.toString() );
        mVideoPlayer.release();
        mVideoPlayer.onLoading();
        mIbDownload.setEnabled(false);
        setSeriesAdapterSelect(position, mSeriesAdapter, mSeriesPopAdapter);
        mPresenter.getSeriesPlayPath(series.url);
    }

    /**
     * 设置剧集适配器选中的剧集
     *
     * @param position
     * @param adapters
     */
    private void setSeriesAdapterSelect(int position, QSPSeriesAdapter... adapters) {
        for (QSPSeriesAdapter adapter : adapters) {
            if (null != adapter) {
                adapter.setSelect(position);
            }
        }
    }

    @Override
    public void onDetailComplete() {
        initVideoData();
        showContentPage();
        getSeriesPlayPath(0);
    }

    @Override
    public void onSeriesComplete(String response) {
        mPlayPath = response;
        m3u8Server.execute();
        if (isDownload = M3U8Downloader.getInstance().checkM3U8IsExist(mPlayPath)) {
             mPlayPath = M3U8PathUtil.getLocalPath(m3u8Server,mPlayPath);
        }
        mIbDownload.setEnabled(!isDownload);
        if (!TextUtils.isEmpty(mPlayPath)) {
            initVideoBuilderMode();
            mVideoPlayer.startPlayLogic();
        } else {
            showFailTips("播放地址不能为空");
        }
    }

    @Override
    public void onSeriesError() {
        mVideoPlayer.onError();
    }


    /**
     * 显示简介弹窗
     */
    private void showVideoIntroducePopup() {
        if (null == mVideoInfoPop) {
            View mContentView = LayoutInflater.from(this).inflate(R.layout.custom_video_introduce_layout, null);
            mContentView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mVideoInfoPop && mVideoInfoPop.isShowing()) {
                        mVideoInfoPop.hide();
                    }
                }
            });
            mTvIntroduce = mContentView.findViewById(R.id.tv_introduce);
            mVideoInfoPop = new ThePopupWindow(VideoPlayActivity.this, mRootView, mContentView, mVideoPlayer);
        }
        mTvIntroduce.setText(mVideo.introduce);
        mVideoInfoPop.show();
    }

    /**
     * 显示剧集全部弹窗
     */
    protected void showSeriesPopup() {
        if (null == mSeriesPopup) {
            View mContentView = LayoutInflater.from(this).inflate(R.layout.custom_video_series_layout, null);
            mContentView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mSeriesPopup && mSeriesPopup.isShowing()) {
                        mSeriesPopup.hide();
                    }
                }
            });
            TextView tvTypeName = mContentView.findViewById(R.id.type_name);
            tvTypeName.setText(getSeriesTypeName());
            RecyclerView rcSeriesPop = mContentView.findViewById(R.id.rc_series_pop);
            rcSeriesPop.setLayoutManager(new GridLayoutManager(this, 4));
            rcSeriesPop.addItemDecoration(new SpacesItemDecoration(QMUIDisplayHelper.dp2px(this, 10), 4));
            mSeriesPopAdapter = new QSPSeriesAdapter(R.layout.item_video_series_pop, rcSeriesPop);
            mSeriesPopAdapter.setOnItemChildClickListener(seriesClildClickListener);
            rcSeriesPop.setAdapter(mSeriesPopAdapter);
            mSeriesPopup = new ThePopupWindow(VideoPlayActivity.this, mRootView, mContentView, mVideoPlayer);
        }
        mSeriesPopAdapter.setNewData(mVideo.series);
        mSeriesPopAdapter.setSelect(mSeriesAdapter.getSelect());
        mSeriesPopup.show();
    }

    private boolean hidePopupWindow(ThePopupWindow popupWindow) {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.hide();
            return true;
        }
        return false;
    }

    private boolean hideSeriesPop() {
        return hidePopupWindow(mSeriesPopup);
    }

    private OnItemChildClickListener seriesClildClickListener = new OnItemChildClickListener() {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            getSeriesPlayPath(position);
        }
    };

    public void showContentPage() {
        if (null != mStatusLayout)
            mStatusLayout.showContent();
    }

    public void showLoadingPage() {
        if (null != mStatusLayout)
            mStatusLayout.showLoading();
    }

    public void showErrorPage(String title, String content, String btnString, View.OnClickListener listener) {
        if (null != mStatusLayout)
            mStatusLayout.showError(null, title, content, btnString, listener);
    }

    @Override
    public StandardTheVideoPlayer getGSYVideoPlayer() {
        return mVideoPlayer;
    }


    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        return new GSYVideoOptionBuilder()
                .setThumbImageView(ivCover)
                .setUrl(mPlayPath)
                .setCacheWithPlay(!isDownload)
                .setVideoTitle(getTitleName() + " " + getSeriesName())
                .setIsTouchWiget(true)
                .setPlayTag(mPlayPath)
                .setRotateViewAuto(false)
                .setAutoFullWithSize(true)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(3);
    }

    private String getTitleName() {
        if (null == mVideo) return "";
        return mVideo.name;
    }

    private String getSeriesName() {
        String seriesName;
        if (null != mSeriesAdapter && null != mSeriesAdapter.getSelectSeries()) {
            seriesName = mSeriesAdapter.getSelectSeries().name;
            if (!TextUtils.isEmpty(seriesName) && !seriesName.contains("播放"))
                return seriesName;
        }
        return "";
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    @Override
    protected void doOnBackPressed() {
        if (hideSeriesPop()) {
            return;
        }
        if (hidePopupWindow(mVideoInfoPop)) {
            return;
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.doOnBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        m3u8Server.encrypt();
    }

    @Override
    protected void onResume() {
        super.onResume();
        m3u8Server.decrypt();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        m3u8Server.finish();
    }

    @Override
    public void onComplete(String url, Object... objects) {

    }
}
