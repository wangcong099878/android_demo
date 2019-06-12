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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import the.one.base.R;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.widge.pullextend.ExtendListFooter;
import the.one.base.widge.pullextend.ExtendListHeader;
import the.one.base.widge.pullextend.PullExtendLayout;

/**
 * @author The one
 * @date 2019/6/12 0012
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BasePullExtendFragment extends BaseFragment {

    protected PullExtendLayout mPullExtend;
    protected ExtendListHeader mExtendHeader;
    protected ExtendListFooter mExtendFooter;
    protected FrameLayout body;

    protected RecyclerView mHeaderRc;
    protected RecyclerView mFooterRc;

    protected abstract int getBodyLayoutId();

    @Override
    protected int getContentViewId() {
        return R.layout.base_pull_extend;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(View rootView) {
        mPullExtend = rootView.findViewById(R.id.pull_extend);
        mExtendHeader = rootView.findViewById(R.id.extend_header);
        mExtendFooter = rootView.findViewById(R.id.extend_footer);
        mHeaderRc= mExtendHeader.getRecyclerView();
        mFooterRc = mExtendFooter.getRecyclerView();
        body = rootView.findViewById(R.id.body);
        setCustomLayout(body,getBodyLayoutId());
    }

}
