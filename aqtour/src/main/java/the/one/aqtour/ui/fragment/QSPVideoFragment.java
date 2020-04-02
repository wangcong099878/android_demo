package the.one.aqtour.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import the.one.aqtour.bean.QSPCategory;
import the.one.aqtour.bean.QSPContent;
import the.one.aqtour.bean.QSPVideo;
import the.one.aqtour.ui.presenter.QSPVideoPresenter;
import the.one.aqtour.widge.CustomRoundAngleImageView;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.base.util.glide.GlideUtil;

public class QSPVideoFragment extends BaseVideoFragment {

    public static QSPVideoFragment newInstance(QSPCategory category) {
        QSPVideoFragment fragment = new QSPVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DataConstant.DATA, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    private QSPVideoPresenter mPresenter;
    private Banner mBanner;

    private void initBanner() {
        mBanner = new Banner(_mActivity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIDisplayHelper.dp2px(_mActivity, 200));
        mBanner.setLayoutParams(layoutParams);
        mBanner.setImageLoader(new GlideImageLoader());
        //设置banner样式
//        mBanner.setBannerStyle(BannerConfig.);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.ScaleInOut);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
            }
        });
        mAdapter.addHeaderView(mBanner);
    }

    @Override
    protected void requestServer() {
        mPresenter.getVideoList(mCategory.url, page);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter = new QSPVideoPresenter();
    }

    @Override
    public void onSuccess(QSPContent data) {
        if (isFirst && null != mBanner) {
            setBannerData(data.lunboList);
        }
        super.onSuccess(data);
    }

    private List<QSPVideo> mBannerDatas;
    private void setBannerData(List<QSPVideo> videos) {
        if (null == videos || videos.size() == 0) {
            goneView(mBanner);
            return;
        }
        mBannerDatas = videos;
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (QSPVideo video : videos) {
            images.add(video.cover);
            titles.add(video.name);
        }
        mBanner.setImages(images);
        mBanner.setBannerTitles(titles);
        mBanner.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mBanner)
            mBanner.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mBanner)
            mBanner.stopAutoPlay();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            GlideUtil.load(context, (String) path, imageView);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            CustomRoundAngleImageView ratioImageView = new CustomRoundAngleImageView(context);
            return ratioImageView;
        }
    }
}
