package the.one.kotlin.presenter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import org.json.JSONException
import org.json.JSONObject
import the.one.base.base.presenter.BasePresenter
import the.one.base.base.view.BaseDataView
import the.one.kotlin.Constant
import the.one.kotlin.model.GankBean
import the.one.net.entity.PageInfoBean


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
class GankPresenter : BasePresenter<BaseDataView<GankBean>>() {

    fun getData(context: Context, type: String?, page: Int) {
        OkHttpUtils.get().url(Constant.GANK_CATEGORY + type + "/" + Constant.COUNT + "/" + page).build().execute(object : StringCallback() {
            override fun onError(call: Call, e: Exception, id: Int) {
                if (isViewAttached)
                    view.onFail(e)
            }

            override fun onResponse(response: String, id: Int) {
                if (isViewAttached) {
                    var jsonObject: JSONObject? = null
                    try {
                        jsonObject = JSONObject(response)
                        val error = jsonObject.getBoolean("error")
                        if (error) {
                            view.onFail("错误")
                        } else {
                            val personObject = jsonObject.getString("results")
                            val itemData = Gson().fromJson<List<GankBean>>(personObject,
                                    object : TypeToken<List<GankBean>>() {

                                    }.type)
                            val pageInfoBean = PageInfoBean( 1000,page)
                            if (type !== Constant.WELFARE) {
                                view.onComplete(itemData, pageInfoBean, "无" + type + "相关数据")
                            } else
                                parseSize(context, itemData, pageInfoBean)
                        }

                    } catch (e: JSONException) {
                        view.onFail("解析错误")
                        e.printStackTrace()
                    }

                }
            }
        })
    }

    private fun parseSize(context: Context, data: List<GankBean>, pageInfoBean: PageInfoBean) {
        for (i in data.indices) {
            val item = data[i]
            Glide.with(context).asBitmap().load(item.url).into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    item.width = resource.width
                    item.height = resource.height
                    if (i == data.size - 1) {
                        view.onComplete(data, pageInfoBean)
                    }
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    if (i == data.size - 1) {
                        view.onComplete(data, pageInfoBean)
                    }
                    super.onLoadFailed(errorDrawable)
                }
            })
        }
    }

    companion object {

        private val TAG = "WelfarePresenter"
    }
}
