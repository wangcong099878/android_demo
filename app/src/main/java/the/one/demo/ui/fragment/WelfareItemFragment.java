package the.one.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import the.one.base.base.activity.PhotoWatchActivity;
import the.one.base.base.fragment.BaseDataFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.demo.ui.adapter.WelfareAdapter;
import the.one.demo.ui.bean.GankBean;
import the.one.demo.ui.presenter.WelfarePresenter;


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
 * @date 2019/6/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class WelfareItemFragment extends BaseDataFragment<GankBean> {

    public static WelfareItemFragment newInstance(String url){
        WelfareItemFragment fragment = new WelfareItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DataConstant.URL,url);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String url;
    private WelfarePresenter presenter;

    @Override
    protected int setType() {
        return TYPE_STAGGERED;
    }

    @Override
    protected boolean onAnimationEndInit() {
        return false;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        url = getArguments().getString(DataConstant.URL);
        goneView(mTopLayout);
        return new WelfareAdapter();
    }

    @Override
    protected void requestServer() {
        presenter.getData(url,page);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<GankBean> datas = adapter.getData();
        ArrayList<String> images = new ArrayList<>();
        for (GankBean gankBean: datas){
            images.add(gankBean.getUrl());
        }
        PhotoWatchActivity.startThisActivity(_mActivity,view,images,position);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter = new WelfarePresenter();
    }
}
