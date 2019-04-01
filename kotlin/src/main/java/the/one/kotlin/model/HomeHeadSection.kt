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
class HomeHeadSection(var title: String?) : QMUISection.Model<HomeHeadSection> {

    override fun cloneForDiff(): HomeHeadSection {
        return HomeHeadSection(title)
    }

    override fun isSameItem(other: HomeHeadSection): Boolean {
        return title === other.title || title != null && title == other.title
    }

    override fun isSameContent(other: HomeHeadSection): Boolean {
        return true
    }
}
