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
class GankBean {

    @SerializedName("_id")
    var id: String? = null
    @SerializedName("createdAt")
    var createdAt: String? = null
    @SerializedName("desc")
    var desc: String? = null
    @SerializedName("publishedAt")
    var publishedAt: String? = null
    @SerializedName("source")
    var source: String? = null
    @SerializedName("type")
    var type: String? = null
    @SerializedName("url")
    var url: String? = null
    @SerializedName("used")
    var isUsed: Boolean = false
    @SerializedName("who")
    var who: String? = null
    @SerializedName("images")
    var images: List<String>? = null

    var width: Int = 0
    var height: Int = 0
}