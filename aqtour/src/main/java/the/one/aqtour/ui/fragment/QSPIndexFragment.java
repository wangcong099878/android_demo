package the.one.aqtour.ui.fragment;

import android.view.View;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;

import java.util.ArrayList;
import java.util.List;

import the.one.aqtour.R;
import the.one.aqtour.bean.QSPCategory;
import the.one.aqtour.ui.presenter.QSPCategoryPresenter;
import the.one.aqtour.ui.view.QSPCategoryView;
import the.one.base.ui.fragment.BaseFragment;
import the.one.base.ui.fragment.BaseTitleTabFragment;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.widge.TheSearchView;

public class QSPIndexFragment extends BaseTitleTabFragment implements QSPCategoryView {

    private QSPCategoryPresenter mPresenter;
    private static List<QSPCategory> mCategories;
    private QMUIAlphaImageButton mDownload, mCollection;
    private TheSearchView mSearchView;

    @Override
    protected boolean isExitFragment() {
        return true;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mDownload = mTopLayout.addRightImageButton(R.drawable.ic_svg_download, R.id.topbar_right_button1);
        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new DownloadFragment());
            }
        });
        mCollection = mTopLayout.addRightImageButton(R.drawable.ic_collection_normal, R.id.topbar_right_button2);
        mCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new CollectionVideoFragment());
            }
        });
        mSearchView = new TheSearchView(_mActivity, false);
        mSearchView.getSearchEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new QSPSearchFragment());
            }
        });
        mTopLayout.setCenterView(mSearchView);
        goneView(mSearchView, mDownload, mCollection);
    }


    @Override
    protected boolean tabFromNet() {
        return true;
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter = new QSPCategoryPresenter();
    }

    @Override
    protected void requestServer() {
        showLoadingPage();
        if (null == mCategories || mCategories.size() == 0)
            mPresenter.getCategoryList();
        else
            onComplete(mCategories);
    }

    @Override
    protected void addTabs() {

    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        for (QSPCategory QSPCategory : mCategories) {
            addTab(QSPCategory.title);
            fragments.add(QSPVideoFragment.newInstance(QSPCategory));
        }
    }

    @Override
    public void onComplete(List<QSPCategory> QSPCategoryList) {
        mCategories = QSPCategoryList;
        startInit();
        showView(mSearchView, mDownload, mCollection);
    }

}
