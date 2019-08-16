package the.one.base.base.fragment;

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

import android.view.View;

import java.util.List;

import the.one.base.base.presenter.BasePresenter;
import the.one.base.model.CitySection;
import the.one.base.util.CityUtil;

/**
 * @author The one
 * @date 2019/8/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class CitySelectFragment extends BaseLetterSearchFragment<CitySection> {

    @Override
    protected boolean isNeedDelete() {
        return false;
    }

    @Override
    protected String getTitleString() {
        return "选择城市";
    }

    @Override
    protected void onLazyInit() {
        showLoadingPage();
        notifyData(CityUtil.getCityDatas(_mActivity), "城市数据为空", "重新加载", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLazyInit();
            }
        });
    }

    @Override
    protected void onItemClick(CitySection citySection) {
        showToast(citySection.getFull());
    }

    @Override
    protected void onConfirmSelect(List<CitySection> selects) {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
