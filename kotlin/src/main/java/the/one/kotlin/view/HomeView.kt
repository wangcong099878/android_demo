package the.one.kotlin.view

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

import the.one.base.base.view.BaseView
import the.one.kotlin.model.GankBean
import the.one.kotlin.model.HomeBean

/**
 * @author The one
 * @date 2019/3/12 0012
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
interface HomeView : BaseView {

    fun onWelfareComplete(data: List<GankBean>)

    fun onTodayComplete(data: HomeBean)

}
