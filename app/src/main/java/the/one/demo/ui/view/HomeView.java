package the.one.demo.ui.view;

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

import the.one.base.base.view.BaseView;
import the.one.demo.ui.bean.GankBean;
import the.one.demo.ui.bean.HomeBean;

/**
 * @author The one
 * @date 2019/3/12 0012
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public interface HomeView extends BaseView {

    void onWelfareComplete(List<GankBean> data);

    void onTodayComplete(HomeBean data);

}
