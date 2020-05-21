package the.one.demo.ui.fragment;

import the.one.base.model.CitySection;
import the.one.base.ui.fragment.BaseCitySelectFragment;
import the.one.base.ui.presenter.BasePresenter;


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
 * @date 2020/5/21 0021
 * @describe 城市选择
 * @email 625805189@qq.com
 * @remark
 */
public class CitySelectFragment extends BaseCitySelectFragment {

    @Override
    protected void onItemClick(CitySection citySection) {
        showToast(citySection.getName());
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
