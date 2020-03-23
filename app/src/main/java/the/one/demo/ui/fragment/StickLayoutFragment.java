package the.one.demo.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import the.one.base.base.fragment.BaseDataFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.R;
import the.one.demo.bean.GankBean;
import the.one.demo.constant.NetUrlConstant;
import the.one.demo.ui.adapter.GankAdapter;
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
 * @date 2020/1/15 0015
 * @describe 让其任意一个直接子控件滑动时停留在顶部
 * @email 625805189@qq.com
 * @remark 内容最好固定
 */
public class StickLayoutFragment extends BaseDataFragment<GankBean> {

    private GankPresenter presenter;

    // 这里需要对布局进行更改
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_stick_layout;
    }

    @Override
    protected boolean isNeedSpace() {
        return false;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        initFragmentBack("StickLayout");
//        recycleView.setNestedScrollingEnabled(false);
        //对LayoutManager进行重新设置
        recycleView.setLayoutManager(new LinearLayoutManager(_mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        // 这个控件会让Adapter一直触发OnLoadMoreListener，所以这里处理掉，所以不建议用这个控件有加载更多的操作
        adapter.setOnLoadMoreListener(null,recycleView);
        showLoadingPage();
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new GankAdapter();
    }

    @Override
    protected void requestServer() {
        presenter.getData(_mActivity, NetUrlConstant.ANDROID, page);
    }


    @Override
    public BasePresenter getPresenter() {
        return presenter = new GankPresenter();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
