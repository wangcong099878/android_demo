package the.one.demo.fragment.bottomIndex;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
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
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SimpleBottomItemFragment extends BaseFragment {

    public static SimpleBottomItemFragment getInstance(String title){
        SimpleBottomItemFragment fragment = new SimpleBottomItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DataConstant.TITLE,title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.textview)
    TextView textview;

    @Override
    protected int getContentViewId() {
        return R.layout.simple_bottom_index;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(View rootView) {
        assert getArguments() != null;
        String title = getArguments().getString(DataConstant.TITLE);
        mTopLayout.setTitle(title);
        textview.setText(title);
    }

}
