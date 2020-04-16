package the.one.demo.ui.fragment;

import android.content.DialogInterface;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.Interface.ImageSnap;
import the.one.base.ui.fragment.BaseImageSnapFragment;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.DownloadSheetDialogUtil;
import the.one.base.util.QMUIDialogUtil;
import the.one.base.util.QMUIPopupUtil;
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
                    updateOrientation();
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

    @Override
    public boolean onLongClick(Mzitu data) {
        DownloadSheetDialogUtil.show(getBaseFragmentActivity(), data.getImageUrl());
        return true;
    }
}
