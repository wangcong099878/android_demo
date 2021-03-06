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

import the.one.base.Interface.IPageInfo;
import the.one.base.R;

/**
 * @author The one
 * @date 2019/3/14 0014
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseCoordinatorFragment<T> extends BaseListFragment<T> {

    protected FrameLayout flCoordinatorLayout;

    protected abstract int getCoordinatorLayout();

    @Override
    protected int getContentViewId() {
        return R.layout.base_coordinator_recyleview;
    }

    @Override
    protected void initView(View rootView) {
        flCoordinatorLayout = rootView.findViewById(R.id.fl_coordinator_layout);
        setCustomLayout(flCoordinatorLayout, getCoordinatorLayout());
        super.initView(rootView);
    }

    @Override
    public void setPageInfo(IPageInfo mPageInfo) {
        super.setPageInfo(mPageInfo);
        showView(flCoordinatorLayout);
    }
}
