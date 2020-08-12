package the.one.gank.ui.view

import the.one.base.ui.view.BaseView
import the.one.gank.bean.BannerBean
import the.one.gank.bean.GankBean
import the.one.gank.bean.HomeSection

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
 * @date 2019/3/12 0012
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
interface HomeViewK : BaseView {

    fun onBannerComplete(data: List<BannerBean>?)
    fun onHotComplete(data: List<HomeSection>?)
    fun onError(msg: String?)

}