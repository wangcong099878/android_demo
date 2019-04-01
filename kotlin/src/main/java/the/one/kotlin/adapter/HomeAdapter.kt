package the.one.kotlin.adapter

import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.qmuiteam.qmui.widget.section.QMUISection
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter
import the.one.base.adapter.BaseSectionAdapter
import the.one.base.util.StringUtils
import the.one.kotlin.R
import the.one.kotlin.model.HomeHeadSection
import the.one.kotlin.model.HomeItemSection
import the.one.kotlin.widget.NineImageLayout
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
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class HomeAdapter : BaseSectionAdapter<HomeHeadSection, HomeItemSection>(R.layout.item_home_item, R.layout.item_home_head) {

    override fun onBindSectionHeader(holder: QMUIStickySectionAdapter.ViewHolder?, position: Int, section: QMUISection<HomeHeadSection, HomeItemSection>?) {
        val tvTitle = holder!!.itemView.findViewById<TextView>(R.id.tv_title)
        tvTitle.setText(section!!.getHeader().title)
    }

    override fun onBindSectionItem(holder: QMUIStickySectionAdapter.ViewHolder?, position: Int, section: QMUISection<HomeHeadSection, HomeItemSection>?, itemIndex: Int) {
        val tvContent = holder!!.itemView.findViewById<TextView>(R.id.tv_content)
        val nineImageLayout = holder!!.itemView.findViewById<NineImageLayout>(R.id.nine_image)
        val itemSection = section!!.getItemAt(itemIndex)
        val author = "#" + itemSection.remark + "  "
        tvContent.setText(StringUtils.SpannableString(author + itemSection.content, author, ContextCompat.getColor(mContext, R.color.qmui_config_color_blue)))
        if (null == itemSection.images || itemSection.images!!.size < 1) {
            if (itemSection.url.endsWith(".jpg")) {
                val strings = ArrayList<String>()
                strings.add(itemSection.url)
                nineImageLayout.setUrlList(strings)
            } else {
                nineImageLayout.setUrlList(null)
            }
        } else
            nineImageLayout.setUrlList(itemSection.images)
    }


}
