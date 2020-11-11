package the.one.gank.ui.presenter

import androidx.lifecycle.LifecycleOwner
import com.rxjava.rxlife.BaseScope
import com.rxjava.rxlife.ObservableLife
import com.rxjava.rxlife.RxLife
import com.rxlife.coroutine.RxLifeScope
import io.reactivex.functions.Consumer
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse
import the.one.base.Interface.OnError
import the.one.base.model.ErrorInfo
import the.one.gank.bean.BannerBean
import the.one.gank.bean.GankBean
import the.one.gank.bean.HomeSection
import the.one.gank.constant.NetUrlConstantV2
import the.one.gank.net.Response
import the.one.gank.ui.view.HomeViewK
import java.util.*


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
class HomePresenterK(owner: LifecycleOwner, val view: HomeViewK) : BaseScope(owner) {

    fun getData(isFirst: Boolean, hotType: String, page: Int) {
        RxLifeScope().launch({
            val response = RxHttp.get(NetUrlConstantV2.getHotUrl(hotType, getCategory(page)))
                    .setDomainTov2UrlIfAbsent()
                    .setCacheMode(if (isFirst) CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK else CacheMode.NETWORK_SUCCESS_WRITE_CACHE)
                    .toResponse<List<GankBean>>()
                    .await()
            view.onHotComplete(parseSection(response.data, getCategory(page)))
        }, {
            view.onError(it.message)
        })
    }

    fun getBanner(isFirst: Boolean) {
        RxLifeScope().launch {
            val response = RxHttp.get(NetUrlConstantV2.BANNER)
                    .setDomainTov2UrlIfAbsent()
                    .setCacheMode(if (isFirst) CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK else CacheMode.NETWORK_SUCCESS_WRITE_CACHE)
                    .toResponse<List<BannerBean>>()
                    .await();
            view.onBannerComplete(response.data)
        }
    }

    private fun parseSection(gankBeans: List<GankBean>, head: String): List<HomeSection> {
        val sections: MutableList<HomeSection> = ArrayList()
        sections.add(HomeSection(head))
        for (i in gankBeans.indices) {
            sections.add(HomeSection(head, gankBeans[i]))
        }
        return sections
    }

    private fun getCategory(page: Int): String {
        var url = NetUrlConstantV2.CATEGORY_GIRL
        when (page) {
            1 -> url = NetUrlConstantV2.CATEGORY_ARTICLE
            2 -> url = NetUrlConstantV2.CATEGORY_GANHUO
        }
        return url
    }

}