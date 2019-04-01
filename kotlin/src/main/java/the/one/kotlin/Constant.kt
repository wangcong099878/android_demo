package the.one.kotlin

/**
 * @author The one
 * @date 2018/8/10 0010
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
object Constant {

    val GANK_BASE = "http://gank.io/"
    val GANK_API = GANK_BASE + "api/"
    val GANK_CATEGORY = GANK_API + "data/"
    val GANK_PUBLISH = GANK_API + "add2gank"

    val TODAY = GANK_API + "today"

    val ANDROID = "Android"
    val IOS = "iOS"
    val APP = "App"
    val RELAX = "休息视频"
    val FRONT = "前端"
    val EXTENSION = "拓展资源"
    val RECOMMEND = "瞎推荐"
    val WELFARE = "福利"

    var title = arrayOf(Constant.ANDROID, Constant.APP, Constant.IOS, Constant.EXTENSION, Constant.RECOMMEND, Constant.FRONT, Constant.RELAX, Constant.WELFARE)

    val COUNT = 20

}
