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

import android.view.View;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;

import the.one.base.R;

/**
 * @author The one
 * @date 2019/3/14 0014
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseCollapsingTopBarFragment<T> extends BaseListFragment<T> {

    protected QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    protected QMUITopBar mTopBar;
    protected FrameLayout flCollapsingContainer;

    protected abstract Object getCollapsingContentLayout();

    @Override
    protected boolean isStatusBarLightMode() {
        return true;
    }

    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.base_collapsing_recyleview;
    }

    @Override
    protected void initView(View rootView) {
        mStatusLayout = rootView.findViewById(R.id.status_layout);
        mCollapsingTopBarLayout = rootView.findViewById(R.id.collapsing_topbar_layout);
        mTopBar = rootView.findViewById(R.id.topbar);
        flCollapsingContainer = rootView.findViewById(R.id.fl_body);
        setCustomLayout(flCollapsingContainer,getCollapsingContentLayout());
        super.initView(rootView);
    }

    @Override
    public void showContentPage() {
        showView(flCollapsingContainer);
        goneView(mStatusLayout);
        super.showContentPage();
    }

}
