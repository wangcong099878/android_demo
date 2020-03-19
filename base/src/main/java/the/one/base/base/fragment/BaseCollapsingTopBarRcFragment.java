package the.one.base.base.fragment;

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
import the.one.base.widge.MyTopBar;
import the.one.base.widge.TheCollapsingTopBarLayout;

/**
 * @author The one
 * @date 2019/3/14 0014
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseCollapsingTopBarRcFragment<T> extends BaseDataFragment<T> {

    private TheCollapsingTopBarLayout mCollapsingTopBarLayout;
    private MyTopBar mTopBar;
    protected FrameLayout flCoordinatorLayout;

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
        mCollapsingTopBarLayout = rootView.findViewById(R.id.collapsing_topbar_layout);
        mTopBar = rootView.findViewById(R.id.topbar);
        super.initView(rootView);
    }

    @Override
    protected void initFragmentBack(String title) {
        mCollapsingTopBarLayout.setTitle(title);
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void setPageInfo(IPageInfo mPageInfo) {
        super.setPageInfo(mPageInfo);
        showView(flCoordinatorLayout);
    }
}
