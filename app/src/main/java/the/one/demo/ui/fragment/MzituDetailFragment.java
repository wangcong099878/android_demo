package the.one.demo.ui.fragment;

import android.content.DialogInterface;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

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
 * BaseImageSnapFragment基本上就是RC的结构，只是让其一个item一页的显示
 * 可以当做一个图片查看器，最重要的是可以加载更多，数据是分页的
 * 包含了图片加载进度显示
 * 这个最开始的需求是一个分类里有很多图片，以前都是外部显示小图，
 * 然后点击后查看大图，这个时候只能显示已经加载的数据，滑动到最后一条数据时也应该继续显示
 * 实体需 implements {@link ImageSnap}
 */
public class MzituDetailFragment extends BaseImageSnapFragment<Mzitu> {

    private MzituPresenter presenter;
    private int mTotal;
    private String[] mMenus = new String[]{"方向", "全屏"};
    private String[] mOrientationItems = new String[]{"HORIZONTAL", "VERTICAL"};
    private String[] mFullScreenItems = new String[]{"全屏", "TopBar下面"};
    private QMUIAlphaImageButton mSettingIcon;
    private QMUIPopup mSettingPopup;

    private int mCurrentOrientation = RecyclerView.HORIZONTAL;
    private boolean isFullScreen = true;

    /**
     * @return true or false 自己改变看看效果有什么不一样
     */
    @Override
    protected boolean isFullScreen() {
        return isFullScreen;
    }

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
     * 代码写在super()的前面或者后面决定了代码执行顺序，所以有些地方的代码先后顺序一定要注意
     * @param rootView
     */
    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mSettingIcon = mTopLayout.addRightImageButton(R.drawable.mz_titlebar_ic_more_light, R.id.topbar_right_about_button);
        mSettingIcon.setOnClickListener(v -> {
            showSettingPopup();
        });
    }

    private void showSettingPopup() {
        if (null == mSettingPopup) {
            mSettingPopup = QMUIPopupUtil.createListPop(_mActivity, mMenus, new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    if(position == 0){
                        showOrientationDialog();
                    }else if(position == 1){
                        showFullScreenDialog();
                    }
                    mSettingPopup.dismiss();
                }
            });
        }
        mSettingPopup.show(mSettingIcon);
    }

    private void showOrientationDialog(){
       final int selectIndex = getOrientation() == RecyclerView.HORIZONTAL?0:1;
        QMUIDialogUtil.showSingleChoiceDialog(_mActivity, mOrientationItems, selectIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(selectIndex != which){
                    mCurrentOrientation = which== 0?RecyclerView.HORIZONTAL:RecyclerView.VERTICAL;
                    updateOrientation();
                }
                dialog.dismiss();
            }
        });
    }

    private void showFullScreenDialog(){
        final int selectIndex = isFullScreen() ?0:1;
        QMUIDialogUtil.showSingleChoiceDialog(_mActivity, mFullScreenItems, selectIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(selectIndex != which){
                    isFullScreen = which== 0;
                    updateStatusView();
                }
                dialog.dismiss();
            }
        });
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
        mTotal = adapter.getData().size() + data.size();
        if(isFirst){
            Mzitu mzitu = new Mzitu();
            // 大图
            mzitu.setUrl("http://img6.16fan.com/attachments/wenzhang/201805/18/152660818127263ge.jpeg");
            data.set(0,mzitu);
        }
        super.onComplete(data);
    }

    @Override
    protected void onScrollChanged(Mzitu item, int position) {
        position++;
        mTopLayout.setTitle(position + "/" + mTotal);
    }

    @Override
    public void onClick(Mzitu data) {

    }

    @Override
    public boolean onLongClick(Mzitu data) {
        DownloadSheetDialogUtil.show(getBaseFragmentActivity(),data.getImageUrl());
        return true;
    }
}
