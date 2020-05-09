package the.one.base.ui.fragment;

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

import android.os.Build;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIAppBarLayout;

import the.one.base.R;

/**
 * @author The one
 * @date 2018/12/29 0029
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseTitleTabFragment extends BaseTabFragment {

    protected QMUIAppBarLayout mAppBarLayout;
    protected int mTopBarHeight;

    /**
     * 是否折叠TitleBar
     *
     * @return
     */
    protected boolean isFoldTitleBar() {
        return false;
    }

    /**
     * @return 是否显示Elevation（Z轴的高度）
     */
    protected boolean showElevation() {
        return false;
    }

    /*
     * 如果需要折叠这个一定得返回false
     */
    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return showTitleBar() ? R.layout.base_title_tab_viewpager_layout : R.layout.base_fold_title_tab_viewpager_layout;
    }

    @Override
    protected void initView(View rootView) {
        mViewPager = rootView.findViewById(R.id.view_pager);
        mMagicIndicator = rootView.findViewById(R.id.indicator);
        if (!showTitleBar()) {
            mTopLayout = rootView.findViewById(R.id.topbar_layout);
            mStatusLayout = rootView.findViewById(R.id.status_layout);
            mAppBarLayout = rootView.findViewById(R.id.appbar_layout);
            if (isFoldTitleBar()) {
                mTopBarHeight = QMUIResHelper.getAttrDimen(_mActivity, R.attr.qmui_topbar_height);
                mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        float percent = 1 - Math.abs(verticalOffset) / (mTopBarHeight * 1.0f);
                        onScrollChanged(percent);
                    }
                });
            } else {
                // 这样设置，然后就不会折叠了。
                setAppBarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL);
            }
            // 一开始是不显示阴影的
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mAppBarLayout.setStateListAnimator(null);
            } else {
                mAppBarLayout.setTargetElevation(0);
            }
        }
        super.initView(rootView);
    }

    protected void onScrollChanged(float percent) {

    }

    protected void setAppBarScrollFlags(int flags) {
        AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) mAppBarLayout.getChildAt(0).getLayoutParams();
        mParams.setScrollFlags(flags);
    }

    @Override
    public void showContentPage() {
        super.showContentPage();
        if (!showTitleBar()) {
            goneView(mStatusLayout);
            //显示内容层时才判断是否显示阴影
            if (showElevation()) {
                mAppBarLayout.setElevation(10f);
            }
        }
    }
}
