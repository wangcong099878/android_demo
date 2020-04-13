package the.one.aqtour.ui.fragment;

import android.text.TextUtils;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIKeyboardHelper;

import the.one.aqtour.R;
import the.one.aqtour.ui.presenter.QSPSearchPresenter;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.widge.TheSearchView;


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
 * @date 2020/1/8 0008
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class QSPSearchFragment extends BaseVideoFragment implements TheSearchView.OnTextChangedListener, View.OnClickListener {

    private QSPSearchPresenter mPresenter;
    private String mSearchStr;

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        addTopBarBackBtn(mTopLayout.getTopBar());
        mTopLayout.addRightTextButton("搜索", R.id.topbar_right_text).setOnClickListener(this);
        TheSearchView mSearchView = new TheSearchView(_mActivity);
        mSearchView.setOnTextChangedListener(this);
        mSearchView.getSearchEditText().setHint("输入影片名称");
        mTopLayout.setCenterView(mSearchView);
        QMUIKeyboardHelper.showKeyboard(mSearchView.getSearchEditText(),true);
    }

    @Override
    protected void requestServer() {
        if (TextUtils.isEmpty(mSearchStr)) showEmptyPage("");
        else
            mPresenter.getSearchVideoList(mSearchStr,page);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter = new QSPSearchPresenter();
    }

    @Override
    public void onChanged(String content, boolean isEmpty) {
        mSearchStr = content;
        if(isEmpty) requestServer();
    }

    @Override
    public void onSearch() {
        refresh();
    }

    @Override
    public void onClick(View v) {
        refresh();
    }

}
