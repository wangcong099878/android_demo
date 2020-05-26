package the.one.aqtour.ui.fragment;

import android.os.Bundle;


import the.one.aqtour.bean.QSPCategory;
import the.one.aqtour.ui.presenter.QSPVideoPresenter;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.constant.DataConstant;

public class QSPVideoFragment extends BaseVideoFragment {

    public static QSPVideoFragment newInstance(QSPCategory category) {
        QSPVideoFragment fragment = new QSPVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DataConstant.DATA, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    private QSPVideoPresenter mPresenter;

    @Override
    protected void requestServer() {
        mPresenter.getVideoList(mCategory.url, page,getParentFragment() != null);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter = new QSPVideoPresenter();
    }

}
