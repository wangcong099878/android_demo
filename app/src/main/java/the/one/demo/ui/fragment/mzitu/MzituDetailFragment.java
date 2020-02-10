package the.one.demo.ui.fragment.mzitu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import the.one.base.base.fragment.BaseImageSnapFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.demo.bean.MzituBean;
import the.one.demo.ui.presenter.MzituPresenter;

public class MzituDetailFragment extends BaseImageSnapFragment<MzituBean> {

    public static MzituDetailFragment newInstance(String url){
        MzituDetailFragment fragment =  new MzituDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DataConstant.URL,url);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mUrl;
    private MzituPresenter presenter;

    private int mTotal;

    @Override
    protected int getOrientation() {
        return LinearLayoutManager.VERTICAL;
    }

    @Override
    protected void initView(View rootView) {
        assert getArguments() != null;
        mUrl = getArguments().getString(DataConstant.URL);
        super.initView(rootView);
    }

    @Override
    protected void requestServer() {
        presenter.getDetailData(mUrl);
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter = new MzituPresenter();
    }

    @Override
    public void onComplete(List<MzituBean> data) {
        mTotal = data.size();
        super.onComplete(data);
    }

    @Override
    protected void onScrollChanged(MzituBean item,int position) {
        position++;
        mTopLayout.setTitle(position+"/"+mTotal);
    }

    @Override
    public void onClick(MzituBean data) {

    }

    @Override
    public boolean onLongClick(MzituBean data) {
        return false;
    }
}
