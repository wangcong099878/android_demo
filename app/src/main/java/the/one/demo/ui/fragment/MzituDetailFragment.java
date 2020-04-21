package the.one.demo.ui.fragment;

import android.content.DialogInterface;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.rxjava.rxlife.RxLife;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import rxhttp.wrapper.param.RxHttp;
import the.one.base.Interface.ImageSnap;
import the.one.base.Interface.OnError;
import the.one.base.model.PopupItem;
import the.one.base.ui.fragment.BaseImageSnapFragment;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.DownloadUtil;
import the.one.base.util.FileDirectoryUtil;
import the.one.base.util.QMUIDialogUtil;
import the.one.base.util.QMUIPopupUtil;
import the.one.base.util.ShareUtil;
import the.one.demo.R;
import the.one.demo.bean.Mzitu;
import the.one.demo.ui.presenter.MzituPresenter;

/**
 * 实体需 implements {@link ImageSnap}
 */
public class MzituDetailFragment extends BaseImageSnapFragment<Mzitu> {

    private MzituPresenter presenter;
    private int mTotal;
    private String[] mMenus = new String[]{"Orientation", "Theme"};
    private String[] mOrientationItems = new String[]{"HORIZONTAL", "VERTICAL"};
    private String[] mThemeItems = new String[]{"White", "Dark"};
    private QMUIAlphaImageButton mSettingIcon;
    private QMUIPopup mSettingPopup;

    private final String TAG_SHARE = "分享";
    private final String TAG_SHARE_URL = "分享图片地址";

    private int mCurrentOrientation = RecyclerView.HORIZONTAL;
    private boolean isWhite = true;

    /**
     * 也就是 RecyclerView.HORIZONTAL or RecyclerView.VERTICAL
     *
     * @return
     */
    @Override
    protected int getOrientation() {
        return mCurrentOrientation;
    }

    /**
     * 改变一下这个值试试看
     *
     * @return
     */
    @Override
    protected boolean isStatusBarLightMode() {
        return isWhite;
    }

    /**
     * 代码写在super()的前面或者后面决定了代码执行顺序，所以有些地方的代码先后顺序一定要注意
     *
     * @param rootView
     */
    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        initTestList();
    }

    private void showSettingPopup() {
        if (null == mSettingPopup) {
            mSettingPopup = QMUIPopupUtil.createListPop(_mActivity, mMenus, new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    if (position == 0) showOrientationDialog();
                    else showThemeDialog();
                    mSettingPopup.dismiss();
                }
            });
        }
        mSettingPopup.show(mSettingIcon);
    }

    private void showOrientationDialog() {
        final int selectIndex = getOrientation() == RecyclerView.HORIZONTAL ? 0 : 1;
        QMUIDialogUtil.showSingleChoiceDialog(_mActivity, mOrientationItems, selectIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectIndex != which) {
                    mCurrentOrientation = which == 0 ? RecyclerView.HORIZONTAL : RecyclerView.VERTICAL;
                    mPagerLayoutManager.setOrientation(mCurrentOrientation);
                }
                dialog.dismiss();
            }
        });
    }

    private void showThemeDialog() {
        final int selectIndex = isStatusBarLightMode() ? 0 : 1;
        QMUIDialogUtil.showSingleChoiceDialog(_mActivity, mThemeItems, selectIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectIndex != which) {
                    isWhite = which == 0;
                    updateBgColor(isWhite);
                }
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void updateBgColor(boolean isWhite) {
        super.updateBgColor(isWhite);
        int mSettingDrawable = isWhite ? R.drawable.mz_titlebar_ic_setting_dark : R.drawable.mz_titlebar_ic_setting_light;
        if (null == mSettingIcon) {
            mSettingIcon = mTopLayout.addRightImageButton(mSettingDrawable, R.id.topbar_right_button1);
            mSettingIcon.setOnClickListener(v -> {
                showSettingPopup();
            });
            goneView(mSettingIcon);
        } else
            mSettingIcon.setImageDrawable(getDrawablee(mSettingDrawable));
    }

    @Override
    protected void requestServer() {
        presenter.getCategoryData(page);
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter = new MzituPresenter();
    }

    @Override
    public void onComplete(List<Mzitu> data) {
        if (isFirst) {
            showView(mSettingIcon);
            mzitus.addAll(data);
            super.onComplete(mzitus);
        } else
            super.onComplete(data);
        mTotal = adapter.getData().size();
    }

    private List<Mzitu> mzitus = new ArrayList<>();

    private List<Mzitu> initTestList() {
        // 测试 大图
        mzitus.add(new Mzitu("http://img6.16fan.com/attachments/wenzhang/201805/18/152660818127263ge.jpeg"));
        // 测试 大图
        mzitus.add(new Mzitu("http://img6.16fan.com/attachments/wenzhang/201805/18/152660818716180ge.jpeg"));
        // 测试 超宽图
        mzitus.add(new Mzitu("http://cache.house.sina.com.cn/citylifehouse/citylife/de/26/20090508_7339__.jpg"));
        // 测试 GIF
        mzitus.add(new Mzitu("https://gank.io/images/4ff6f5d09ba241ddb6ffd066280b51cf"));
        return mzitus;
    }

    @Override
    protected void onScrollChanged(Mzitu item, int position) {
        mTopLayout.setTitle(++position + "/" + mTotal);
    }

    /**
     * 如果需要向BottomSheet里添加内容
     *
     * @param sheetItems
     */
    @Override
    protected void initSheetItems(List<PopupItem> sheetItems) {
        super.initSheetItems(sheetItems);
        sheetItems.add(new PopupItem(TAG_SHARE, R.drawable.ic_share));
        sheetItems.add(new PopupItem(TAG_SHARE_URL, R.drawable.ic_share));
    }

    @Override
    public void onSheetItemClick(int position, String title, QMUIBottomSheet dialog, View itemView) {
        // 默认是直接申请权限，如果有些操作不需要，这里直接判断然后进行操作
        if(title.equals(TAG_SHARE_URL)){
            ShareUtil.shareText(_mActivity,"分享图片",mData.getImageUrl());
            dialog.dismiss();
        }else {
            super.onSheetItemClick(position, title, dialog, itemView);
        }
    }

    @Override
    protected void onPermissionComplete(ImageSnap data, String tag, int position) {
        super.onPermissionComplete(data, tag, position);
        if (tag.equals(TAG_SHARE)) {
            startShare(data);
        }
    }

    @Override
    public void onVideoClick(Mzitu data) {

    }

    @Override
    public boolean onImageLongClick(Mzitu data) {
        showBottomSheetDialog(data);
        return true;
    }

    private void startShare(ImageSnap data) {
        showLoadingDialog("");
        String savePath = FileDirectoryUtil.getCachePath() + File.separator + "share_" + System.currentTimeMillis()
                + DownloadUtil.getFileSuffix(data.getImageUrl(), data.isVideo());
        RxHttp.get(data.getImageUrl())
                .asDownload(savePath)
                .doFinally(()->{
                    hideLoadingDialog();
                })
                .as(RxLife.asOnMain(this))
                .subscribe(s -> {
                            ShareUtil.shareImageFile(_mActivity, new File(s), "分享图片");
                        }
                        , (OnError) error -> {
                            showFailTips("分享失败");
                        });
    }


}
