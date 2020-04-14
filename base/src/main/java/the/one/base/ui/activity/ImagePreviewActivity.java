package the.one.base.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.Interface.ImageSnap;
import the.one.base.R;
import the.one.base.adapter.ImageSnapAdapter;
import the.one.base.constant.DataConstant;
import the.one.base.model.ImagePreviewBean;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.DownloadSheetDialogUtil;
import the.one.base.widge.MyTopBarLayout;

import static the.one.base.ui.fragment.BaseFragment.setMargins;

public class ImagePreviewActivity extends BaseActivity implements ImageSnapAdapter.OnImageClickListener {

    private static long mLastClickTime;
    private static final int MIN_DOUBLE_CLICK_TIME = 1500;

    public static void startThisActivity(Activity activity, ArrayList<String> list, int position) {
        if (null == activity || activity.isFinishing() || activity.isDestroyed() || System.currentTimeMillis() - mLastClickTime <= MIN_DOUBLE_CLICK_TIME)
            return;
        mLastClickTime = System.currentTimeMillis();
        Intent in = new Intent(activity, ImagePreviewActivity.class);
        in.putStringArrayListExtra(DataConstant.DATA, list);
        in.putExtra(DataConstant.POSITION, position);
//        if (null != image)
//            activity.startActivity(in, ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    activity,
//                    image,
//                    BaseApplication.getInstance().getString(R.string.image))
//                    .toBundle());
//        else
        activity.startActivity(in);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    protected MyTopBarLayout mTopBarLayout;
    protected RecyclerView mRecyclerView;
    protected ImageSnapAdapter mAdapter;
    protected LinearLayoutManager mLayoutManager;
    protected PagerSnapHelper mPagerSnapHelper;

    protected List<ImagePreviewBean> mImageList;

    /**
     * 是否全屏 默认在TopBar下面，如果为全屏则延伸至状态栏
     *
     * @return
     */
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_image_preview;
    }

    @Override
    protected void initView(View mRootView) {
        mTopBarLayout = findViewById(R.id.topbar_layout);
        mRecyclerView = findViewById(R.id.recycle_view);
        mStatusLayout = findViewById(R.id.status_layout);
        mStatusLayout.setBackgroundColor(getColorr(R.color.we_chat_black));
        mTopBarLayout.setBackgroundColor(getColorr(R.color.qmui_config_color_transparent));
        mTopBarLayout.getTopBar().getTitleView().setTextColor(getColorr(R.color.white));
        mTopBarLayout.addLeftImageButton(R.drawable.mz_titlebar_ic_back_light, R.id.topbar_left_button).setOnClickListener((v) -> onBackPressed());
        updateStatusView();
        initAdapter();
        initRecyclerView();
        initData();
    }

    protected void updateStatusView() {
        if (isFullScreen()) {
            setMargins(mStatusLayout, 0, 0, 0, 0);
            mStatusLayout.setFitsSystemWindows(false);
        } else {
            mStatusLayout.setFitsSystemWindows(true);
            setMargins(mStatusLayout, 0, QMUIResHelper.getAttrDimen(this, R.attr.qmui_topbar_height) + QMUIStatusBarHelper.getStatusbarHeight(this), 0, 0);
        }
    }

    protected void initAdapter() {
        mAdapter = new ImageSnapAdapter();
        mAdapter.setOnImageClickListener(this);
        mAdapter.setAnimationEnable(true);
        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
        mAdapter.setAnimationFirstOnly(false);
    }

    protected void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(getScrollListener());
        mRecyclerView.setAdapter(mAdapter);
        mPagerSnapHelper = new PagerSnapHelper();
        mPagerSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    protected void initData() {
        List<String> images = getIntent().getStringArrayListExtra(DataConstant.DATA);
        int position = getIntent().getIntExtra(DataConstant.POSITION, 0);
        mImageList = new ArrayList<>();
        for (String image : images) {
            mImageList.add(new ImagePreviewBean(image));
        }
        mAdapter.setNewInstance(mImageList);
        mRecyclerView.scrollToPosition(position);
        updateSelectIndex(position);
    }

    protected RecyclerView.OnScrollListener getScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert linearLayoutManager != null;
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                updateSelectIndex(position);
            }
        };
    }

    protected void updateSelectIndex(int position) {
        mTopBarLayout.setTitle(++position + "/" + mImageList.size());
    }

    @Override
    public void onClick(ImageSnap data) {

    }

    @Override
    public boolean onLongClick(ImageSnap data) {
        DownloadSheetDialogUtil.show(this,data.getImageUrl());
        return false;
    }


    @Override
    public void showLoadingPage() {
        super.showLoadingPage();
        mStatusLayout.getLoadingTipsView().setTextColor(getColorr(R.color.white));
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
     }
}
