package the.one.mzitu.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.rxjava.rxlife.RxLife;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rxhttp.wrapper.param.RxHttp;
import the.one.base.Interface.ImageSnap;
import the.one.base.Interface.OnError;
import the.one.base.constant.DataConstant;
import the.one.base.model.PopupItem;
import the.one.base.ui.fragment.BaseImageSnapFragment;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.DownloadUtil;
import the.one.base.util.FileDirectoryUtil;
import the.one.base.util.ShareUtil;
import the.one.mzitu.R;
import the.one.mzitu.bean.Mzitu;
import the.one.mzitu.presenter.MzituPresenter;

public class MzituDetailFragment extends BaseImageSnapFragment<Mzitu> {

    public static MzituDetailFragment newInstance(Mzitu mzitu) {
        MzituDetailFragment fragment = new MzituDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DataConstant.DATA, mzitu);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MzituDetailFragment newInstance(List<Mzitu> mzitus, String url, int position, int page) {
        MzituDetailFragment fragment = new MzituDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DataConstant.DATA, (ArrayList<? extends Parcelable>) mzitus);
        bundle.putString(DataConstant.URL, url);
        bundle.putInt(DataConstant.POSITION, position);
        bundle.putInt(DataConstant.DATA2, page);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Mzitu mMzitu;
    private List<Mzitu> mMzitus;
    private int position;
    private String URL;
    private MzituPresenter presenter;

    private int mTotal;

    @Override
    protected void initView(View rootView) {
        Bundle bundle = getArguments();
        assert bundle != null;
        mMzitu = bundle.getParcelable(DataConstant.DATA);
        if (null == mMzitu) {
            mMzitus = bundle.getParcelableArrayList(DataConstant.DATA);
            position = bundle.getInt(DataConstant.POSITION);
            page = bundle.getInt(DataConstant.DATA2);
            URL = bundle.getString(DataConstant.URL);
        }
        super.initView(rootView);
    }

    @Override
    protected void requestServer() {
        if (null == mMzitu) {
            if (isFirst) {
                onComplete(mMzitus);
                recycleView.scrollToPosition(position);
            } else {
                presenter.getCategoryData(URL, page);
            }
        } else {
            presenter.getDetailData(mMzitu.getLink());
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter = new MzituPresenter();
    }

    @Override
    public void onComplete(List<Mzitu> data) {
        mTotal = data.size();
        super.onComplete(data);
    }

    @Override
    protected void onScrollChanged(Mzitu item, int position) {
        position++;
        if (null != mMzitu)
            mTopLayout.setTitle(position + "/" + mTotal).setTextColor(mTextColor);
    }

    @Override
    public void onVideoClick(Mzitu data) {

    }

    @Override
    public boolean onImageLongClick(Mzitu data) {
        showBottomSheetDialog(data);
        return true;
    }

    private final String TAG_SHARE = "分享";

    @Override
    protected void initSheetItems(List<PopupItem> sheetItems) {
        super.initSheetItems(sheetItems);
        sheetItems.add(new PopupItem(TAG_SHARE, R.drawable.ic_share));
    }

    @Override
    protected void onPermissionComplete(ImageSnap data, String tag, int position) {
        downloadFile(data, tag.equals(TAG_SHARE));
    }

    private void downloadFile(ImageSnap data, boolean isShare) {
        showLoadingDialog(isShare ? "分享中" : "下载中");
        loadingDialog.setCanceledOnTouchOutside(false);
        //文件存储路径
        String destPath = FileDirectoryUtil.getCachePath() + File.separator +
                (isShare ? "share" : "download" + "_img_") + System.currentTimeMillis() + DownloadUtil.getFileSuffix(data.getImageUrl(), false);
        RxHttp.get(data.getImageUrl())
                .addHeader("Referer", data.getRefer())
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36")
                .asDownload(destPath)
                .doFinally(() -> {
                    hideLoadingDialog();
                })
                .as(RxLife.asOnMain(this)) //感知生命周期
                .subscribe(s -> {//s为String类型，这里为文件存储路径
                    //下载完成，处理相关逻辑
                    if (isShare)
                        ShareUtil.shareImageFile(_mActivity, new File(s), "开车了~ 系好安全带");
                    else
                        showToast("已下载到："+s);
                }, (OnError) error -> {
                    //下载失败，处理相关逻辑
                    showFailTips(error.getErrorMsg());
                });
    }
}
