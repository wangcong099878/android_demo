package the.one.base.ui.fragment;

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

import the.one.base.Interface.ICitySectionListener;
import the.one.base.model.CitySection;
import the.one.base.util.ProvinceUtil;

/**
 * @author The one
 * @date 2019/8/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseCitySelectFragment extends BaseLetterSearchFragment<CitySection> {

    @Override
    protected boolean isNeedMultiChoose() {
        return false;
    }

    @Override
    protected String getTitleString() {
        return "选择城市";
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        showLoadingPage();
    }

    @Override
    protected void onLazyInit() {
        ProvinceUtil.getCitySections(new ICitySectionListener() {
            @Override
            public void onCitySectionComplete(List<CitySection> provinces) {
                notifyData(provinces, "城市数据为空", "重新加载", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLazyInit();
                    }
                });
            }

            @Override
            public void onCitySectionError() {
                showErrorPage("获取失败", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLazyInit();
                    }
                });
            }
        });
    }

    @Override
    protected void onConfirmSelect(List<CitySection> selects) {

    }

}
