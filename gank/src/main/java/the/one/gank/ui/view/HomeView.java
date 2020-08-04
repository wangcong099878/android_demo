package the.one.gank.ui.view;

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

import java.util.List;

import the.one.base.ui.view.BaseView;
import the.one.gank.bean.BannerBean;
import the.one.gank.bean.GankBean;
import the.one.gank.bean.HomeBean;

/**
 * @author The one
 * @date 2019/3/12 0012
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public interface HomeView extends BaseView {

    void onBannerComplete(List<BannerBean> data);
    void onHotComplete(List<GankBean> data);
    void onTodayComplete(HomeBean data);

    void onError(String msg);

}
