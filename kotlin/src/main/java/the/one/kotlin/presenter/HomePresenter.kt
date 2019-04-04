package the.one.kotlin.presenter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import org.json.JSONException
import org.json.JSONObject
import the.one.base.base.presenter.BasePresenter
import the.one.base.util.JsonUtil
import the.one.kotlin.Constant
import the.one.kotlin.model.GankBean
import the.one.kotlin.model.HomeBean
import the.one.kotlin.view.HomeView
import the.one.net.FailUtil


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
 * @date 2019/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class HomePresenter : BasePresenter<HomeView>() {

    fun getData(type: Int) {
        val url = if (type == TYPE_TODAY) Constant.TODAY else Constant.GANK_CATEGORY + Constant.WELFARE + "/" + Constant.COUNT + "/1"
        OkHttpUtils.get().url(url).build().execute(object : StringCallback() {
            override fun onError(call: Call, e: Exception, id: Int) {
                if (isViewAttached)
                    view.showErrorPage(FailUtil.getFailString(e))
            }

            override fun onResponse(response: String, id: Int) {
                if (isViewAttached) {
                    var jsonObject: JSONObject? = null
                    try {
                        jsonObject = JSONObject(response)
                        val error = jsonObject.getBoolean("error")
                        if (error) {
                            view.showErrorPage("请求失败")
                        } else {
                            val result = jsonObject.getString("results")
                            if (type == TYPE_TODAY) {
                                val homeBean = JsonUtil.fromJson<HomeBean>(result, HomeBean::class.java)
                                view.onTodayComplete(homeBean)
                            } else {
                                val gankBeans = Gson().fromJson<List<GankBean>>(result,
                                        object : TypeToken<List<GankBean>>() {

                                        }.type)
                                view.onWelfareComplete(gankBeans)
                            }
                        }
                    } catch (e: JSONException) {
                        view.showErrorPage("解析错误")
                        e.printStackTrace()
                    }

                }
            }
        })
    }

    companion object {
        private val TAG = "HomePresenter"

        val TYPE_TODAY = 0
        val TYPE_WELFARE = 1
    }
}
