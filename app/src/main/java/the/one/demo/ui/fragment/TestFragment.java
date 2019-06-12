package the.one.demo.ui.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import the.one.base.base.fragment.BaseCollapsingTopBarRcFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.R;
import the.one.demo.ui.adapter.GankAdapter;
import the.one.demo.ui.bean.GankBean;


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
 * @date 2019/6/3 0003
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class TestFragment extends BaseCollapsingTopBarRcFragment<GankBean> {
    @Override
    protected int getCoordinatorLayout() {
        return R.layout.test;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new GankAdapter();
    }

    @Override
    protected void requestServer() {
        showContentPage();
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
        return null;
    }
}
