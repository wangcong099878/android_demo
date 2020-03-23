package the.one.demo.ui.fragment;

import android.view.View;

import the.one.base.base.fragment.BaseGroupListFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.widge.TheSearchView;
import the.one.demo.R;


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
 * @date 2020/1/14 0014
 * @describe 自定义的SearchView
 * @email 625805189@qq.com
 * @remark
 */
public class SearchViewFragment extends BaseGroupListFragment {

    @Override
    protected void addGroupListView() {
        // 左右两边内容如果有，要放在前面
        addTopBarBackBtn();
        mTopLayout.addRightTextButton("搜索", R.id.topbar_right_text);
        // 左右的内容固定好后，才能往中间放SearchView, 这样才能确定宽度
        TheSearchView searchView = new TheSearchView(_mActivity);
        searchView.setOnTextChangedListener(new TheSearchView.OnTextChangedListener() {
            @Override
            public void onChanged(String content, boolean isEmpty) {

            }

            @Override
            public void onSearch() {
                // 这里是点击了键盘上的搜索按键
            }
        });
        mTopLayout.setCenterView(searchView);

        showEmptyPage("");
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
