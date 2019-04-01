package the.one.kotlin.model

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

import com.google.gson.annotations.SerializedName

/**
 * @author The one
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class HomeBean {

    @SerializedName("Android")
    var Android: List<GankBean>? = null
    @SerializedName("App")
    var App: List<GankBean>? = null
    @SerializedName("iOS")
    var iOS: List<GankBean>? = null
    @SerializedName("休息视频")
    var relax: List<GankBean>? = null
    @SerializedName("前端")
    var front: List<GankBean>? = null
    @SerializedName("拓展资源")
    var extension: List<GankBean>? = null
    @SerializedName("瞎推荐")
    var recommend: List<GankBean>? = null
    @SerializedName("福利")
    var welfare: List<GankBean>? = null
}
