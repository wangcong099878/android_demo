package the.one.base.ui.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.Interface.ImageSnap;
import the.one.base.R;
import the.one.base.adapter.ImageSnapAdapter;


public abstract class BaseImageSnapFragment<T extends ImageSnap> extends BaseDataFragment<T> implements
        QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener, ImageSnapAdapter.OnImageClickListener<T> {

    protected final String mDelete = "删除";

    protected PagerSnapHelper mPagerSnapHelper;
    protected ImageSnapAdapter<T> mImageSnapAdapter;

    private QMUIBottomSheet mBottomSheet;
    protected T mClickData;

    /**
     * 是否全屏 默认在TopBar下面，如果为全屏则延伸至状态栏
     * @return
     */
    protected boolean isFullScreen() {
        return true;
    }


    protected void onScrollChanged(T item,int position) {

    }

    /**
     * 滑动方向
     * @return
     */
    protected int getOrientation(){
        return RecyclerView.HORIZONTAL;
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return false;
    }

    @Override
    protected boolean isNeedSpace() {
        return false;
    }

    @Override
    protected void initView(View rootView) {
        rootView.setBackgroundColor(getColorr(R.color.we_chat_black));
        mStatusLayout.setBackgroundColor(getColorr(R.color.we_chat_black));
        mTopLayout.setBackgroundColor(getColorr(R.color.qmui_config_color_transparent));
        mTopLayout.addLeftImageButton(R.drawable.mz_titlebar_ic_back_light, R.id.topbar_left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        super.initView(rootView);
        if (isFullScreen()){
            setMargins(mStatusLayout, 0, 0, 0, 0);
            mStatusLayout.setFitsSystemWindows(false);
        }
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        mImageSnapAdapter = new ImageSnapAdapter<T>();
        mImageSnapAdapter.setOnImageClickListener(this);
        return mImageSnapAdapter;
    }

    @Override
    protected void initRecycleView(RecyclerView recycleView, int type, BaseQuickAdapter adapter) {
        recycleView.setBackgroundColor(getColorr(R.color.we_chat_black));
        super.initRecycleView(recycleView, type, adapter);
        mPagerSnapHelper = new PagerSnapHelper();
        recycleView.setLayoutManager(new LinearLayoutManager(_mActivity, getOrientation(), false));
        mPagerSnapHelper.attachToRecyclerView(recycleView);
    }

    protected RecyclerView.OnScrollListener getOnScrollListener() {
        return new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert linearLayoutManager != null;
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                if (adapter.getData().size() > position)
                    onScrollChanged((T) adapter.getData().get(position),position);
            }
        };
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return true;
    }

    @Override
    public void onClick(T data) {
        mClickData = data;
    }

    @Override
    public boolean onLongClick(T data) {
//        mClickData = data;
//        showBottomSheetDialog();
        return true;
    }

    protected void showBottomSheetDialog() {
        if (null == mBottomSheet) {
            QMUIBottomSheet.BottomGridSheetBuilder mBuilder = new QMUIBottomSheet.BottomGridSheetBuilder(_mActivity);
            addItem(mBuilder);
            mBuilder.setOnSheetItemClickListener(this);
            mBottomSheet = mBuilder.build();
        }
        mBottomSheet.show();
    }

    protected void addItem(QMUIBottomSheet.BottomGridSheetBuilder builder) {
            createDeleteItem(builder);
    }

    protected void createDeleteItem(QMUIBottomSheet.BottomGridSheetBuilder builder) {
//        builder.addItem(R.drawable.ic_sheet_delete, mDelete, mDelete, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
    }

    @Override
    public void onClick(QMUIBottomSheet dialog, View itemView) {
        dialog.dismiss();
        String mTag = (String) itemView.getTag();
        if (mTag.equals(mDelete)) {

        } else {
            onCustomTagClick(mTag, mClickData);
        }
    }


    protected void onCustomTagClick(String tag, T data) {

    }


}
