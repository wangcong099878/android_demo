package the.one.gank

import android.app.Activity
import android.graphics.Bitmap
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.param.RxHttp
import the.one.base.BaseApplication
import the.one.base.util.RxHttpManager
import the.one.gank.net.ResponseBuilder
import the.one.gank.ui.activity.LauncherActivity


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
class GankApp : BaseApplication() {

    override fun getStartActivity(): Class<out Activity> {
        return LauncherActivity::class.java
    }

    override fun isDebug(): Boolean {
        return true
    }

    override fun getHttpBuilder(): RxHttpManager.HttpBuilder {
        return super.getHttpBuilder().setCacheMode(CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK)
    }

    override fun onCreate() {
        super.onCreate()
        ResponseBuilder
                .getBuilder()
                .setEventStr("status")
                .setMsgStr("msg")
                .setDataStr("data").successCode = 100
    }

    override fun initHttp(builder: RxHttpManager.HttpBuilder?) {
        RxHttp.init(RxHttpManager.getHttpClient(builder),BuildConfig.DEBUG)
        RxHttpManager.initCacheMode(builder)
    }

}