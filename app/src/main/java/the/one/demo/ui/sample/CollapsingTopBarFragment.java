package the.one.demo.ui.sample;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import the.one.base.base.fragment.BaseCollapsingTopBarRcFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.Constant;
import the.one.demo.R;
import the.one.demo.ui.adapter.GankAdapter;
import the.one.demo.ui.bean.GankBean;
import the.one.demo.ui.presenter.GankPresenter;


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

/**
 * @author The one
 * @date 2019/6/10 0010
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class CollapsingTopBarFragment extends BaseCollapsingTopBarRcFragment<GankBean> {

    private GankPresenter presenter;

    @Override
    protected int getCoordinatorLayout() {
        return R.layout.custom_collapsing_layout;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        initFragmentBack("CollapsingTopBarFragment");
        return new GankAdapter();
    }

    @Override
    protected void requestServer() {
        presenter.getData(_mActivity, Constant.ANDROID,page);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter = new GankPresenter();
    }
}
