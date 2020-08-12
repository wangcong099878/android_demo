package the.one.gank.ui.fragment

import androidx.lifecycle.rxLifeScope
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse
import the.one.base.ui.presenter.BasePresenter
import the.one.gank.bean.GankBean
import the.one.gank.constant.NetUrlConstantV2


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
 * @date 2020/8/12 0012
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class GankV2FragmentK : BaseGankFragment() {

    override fun requestServer() {
        rxLifeScope.launch {
            val response =  RxHttp.get(NetUrlConstantV2.getCategoryData(mCategory, mType, page))
                    .setDomainTov2UrlIfAbsent()
                    .toResponse<List<GankBean>>()
                    .await()
            onComplete(response.data,response.pageInfo)
        }
    }

    override fun getPresenter(): BasePresenter<*>? {
       return null
    }

    override fun isWelfare(): Boolean {
       return mType.equals(NetUrlConstantV2.CATEGORY_GIRL)
    }

}