package the.one.mzitu.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import the.one.base.ui.fragment.BaseImageSnapFragment;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.mzitu.R;
import the.one.mzitu.bean.Mzitu;
import the.one.mzitu.presenter.MzituPresenter;

public class MzituDetailFragment extends BaseImageSnapFragment<Mzitu> {

    public static MzituDetailFragment newInstance(Mzitu mzitu) {
        MzituDetailFragment fragment = new MzituDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DataConstant.DATA, mzitu);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MzituDetailFragment newInstance(List<Mzitu> mzitus, String url, int position, int page) {
        MzituDetailFragment fragment = new MzituDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DataConstant.DATA, (ArrayList<? extends Parcelable>) mzitus);
        bundle.putString(DataConstant.URL, url);
        bundle.putInt(DataConstant.POSITION, position);
        bundle.putInt(DataConstant.DATA2, page);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Mzitu mMzitu;
    private List<Mzitu> mMzitus;
    private int position;
    private String URL;
    private MzituPresenter presenter;

    private int mTotal;

    @Override
    protected void initView(View rootView) {
        Bundle bundle = getArguments();
        assert bundle != null;
        mMzitu = bundle.getParcelable(DataConstant.DATA);
        if (null == mMzitu) {
            mMzitus = bundle.getParcelableArrayList(DataConstant.DATA);
            position = bundle.getInt(DataConstant.POSITION);
            page = bundle.getInt(DataConstant.DATA2);
            URL = bundle.getString(DataConstant.URL);
        }
        super.initView(rootView);
    }

    @Override
    protected void requestServer() {
        if (null == mMzitu) {
            if (isFirst) {
                onComplete(mMzitus);
                recycleView.scrollToPosition(position);
            } else {
                presenter.getCategoryData(URL,page);
            }
        } else {
            presenter.getDetailData(mMzitu.getLink());
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter = new MzituPresenter();
    }

    @Override
    public void onComplete(List<Mzitu> data) {
        mTotal = data.size();
        super.onComplete(data);
    }

    @Override
    protected void onScrollChanged(Mzitu item, int position) {
        position++;
        if (null != mMzitu)
            mTopLayout.setTitle(position + "/" + mTotal).setTextColor(getColorr(R.color.white));
    }

    @Override
    public void onVideoClick(Mzitu data) {

    }

    @Override
    public boolean onImageLongClick(Mzitu data) {
        showBottomSheetDialog(data);
        return true;
    }
}
