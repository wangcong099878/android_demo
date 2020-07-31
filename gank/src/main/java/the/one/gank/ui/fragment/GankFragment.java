package the.one.gank.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import the.one.base.constant.DataConstant;
import the.one.base.ui.activity.BaseWebExplorerActivity;
import the.one.base.ui.fragment.BaseListFragment;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.ImagePreviewUtil;
import the.one.gank.bean.GankBean;
import the.one.gank.constant.NetUrlConstant;
import the.one.gank.ui.adapter.GankAdapter;
import the.one.gank.ui.adapter.WelfareAdapter;
import the.one.gank.ui.presenter.GankPresenter;


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
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class GankFragment extends BaseListFragment<GankBean> {

    public static GankFragment newInstance(String type) {
        GankFragment fragment = new GankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DataConstant.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mType;
    private boolean isWelfare;
    private GankPresenter gankPresenter;

    @Override
    protected void initView(View rootView) {
        goneView(mTopLayout);
        mType = getArguments().getString(DataConstant.TYPE);
        isWelfare = mType.equals(NetUrlConstant.WELFARE);
        super.initView(rootView);
    }

    @Override
    public BasePresenter getPresenter() {
        return gankPresenter = new GankPresenter();
    }

    @Override
    protected int setType() {
        return isWelfare ? TYPE_STAGGERED : TYPE_LIST;
    }

    @Override
    protected int setColumn() {
        return 3;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return isWelfare ? new WelfareAdapter() : new GankAdapter();
    }

    @Override
    protected void requestServer() {
        gankPresenter.getData(_mActivity, mType, page);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if(!isWelfare){
            GankBean  bean = (GankBean) adapter.getItem(position);
            BaseWebExplorerActivity.newInstance(_mActivity,bean.getDesc(),bean.getUrl());
        }else{
            List<GankBean> datas = adapter.getData();
            ArrayList<String> images = new ArrayList<>();
            for (GankBean gankBean: datas){
                images.add(gankBean.getUrl());
            }
            ImagePreviewUtil.start(_mActivity,images,position);
        }
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
