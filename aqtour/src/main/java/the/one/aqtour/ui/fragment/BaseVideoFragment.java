package the.one.aqtour.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import the.one.aqtour.bean.QSPCategory;
import the.one.aqtour.bean.QSPContent;
import the.one.aqtour.bean.QSPVideoSection;
import the.one.aqtour.ui.activity.VideoPlayActivity;
import the.one.aqtour.ui.adapter.QSPVideoAdapter;
import the.one.aqtour.ui.view.QSPVideoView;
import the.one.base.ui.fragment.BaseListFragment;
import the.one.base.constant.DataConstant;

public abstract class BaseVideoFragment extends BaseListFragment<QSPVideoSection> implements QSPVideoView {


    protected QSPCategory mCategory;
    protected QSPVideoAdapter mAdapter;

    @Override
    protected int setType() {
        return TYPE_GRID;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        Bundle bundle = getArguments();
        if (null != bundle) {
            mCategory = bundle.getParcelable(DataConstant.DATA);
            if (!isIndexFragment)
                goneView(mTopLayout);
            else if (null != mCategory) {
                setTitleWithBackBtn(mCategory.title);
            }
        }
        mAdapter = new QSPVideoAdapter();
        return mAdapter;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        QSPVideoSection section = (QSPVideoSection) adapter.getItem(position);
        if (null != section && !section.isHeader()) {
            VideoPlayActivity.startThisActivity(_mActivity, section.t);
        }
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    @Override
    public void onSuccess(QSPContent data) {
        if (null != data)
            onComplete(data.videoSections);
        else
            onComplete(null);
    }

}
