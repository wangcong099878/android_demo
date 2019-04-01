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

import com.qmuiteam.qmui.widget.section.QMUISection

/**
 * @author The one
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class HomeItemSection(var content: String?, var images: List<String>?, var remark: String, var url: String) : QMUISection.Model<HomeItemSection> {

    override fun cloneForDiff(): HomeItemSection {
        return HomeItemSection(content, images, remark, url)
    }

    override fun isSameItem(other: HomeItemSection): Boolean {
        return content === other.content || content != null && content == other.content
    }

    override fun isSameContent(other: HomeItemSection): Boolean {
        return true
    }
}
