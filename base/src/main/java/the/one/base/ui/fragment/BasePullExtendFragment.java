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

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import the.one.base.R;
import the.one.base.ui.presenter.BasePresenter;
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

    protected abstract int getBodyLayoutId();

    protected abstract void initHeaderRc(RecyclerView rcHeader);
    protected abstract void initFooterRc(RecyclerView rcFooter);

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
        body = rootView.findViewById(R.id.body);
        setCustomLayout(body,getBodyLayoutId());
        initHeaderRc(mExtendHeader.getRecyclerView());
        initFooterRc(mExtendFooter.getRecyclerView());
    }

}
