package the.one.demo.ui.sample;

import android.support.v4.app.FragmentManager;
import android.view.View;

import butterknife.OnClick;
import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.fragment.BasePopupLayoutFragment;
import the.one.base.base.presenter.BasePresenter;
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
 * @date 2019/5/14 0014
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class PopupLayoutFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_pupup_layout;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(View rootView) {
    }

    @OnClick(R.id.click)
    public void onViewClicked() {
        FragmentManager manager = getChildFragmentManager();
        BasePopupLayoutFragment fragment = new BasePopupLayoutFragment();
        fragment.show(manager,TAG);
    }
}
