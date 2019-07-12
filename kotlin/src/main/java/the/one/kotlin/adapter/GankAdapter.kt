package the.one.kotlin.adapter

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

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import the.one.base.util.StringUtils
import the.one.kotlin.R
import the.one.kotlin.model.GankBean
import the.one.kotlin.widget.NineImageLayout
import java.util.*

/**
 * @author The one
 * @date 2019/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class GankAdapter : BaseQuickAdapter<GankBean, BaseViewHolder>(R.layout.item_home_item) {

    override fun convert(helper: BaseViewHolder, item: GankBean) {
        val author = "#" + item.who + "  "
        helper.setText(R.id.tv_content, StringUtils.SpannableForegroundColorString(author + item.desc, author, ContextCompat.getColor(mContext, R.color.qmui_config_color_blue)))
        val nineImageLayout = helper.getView<NineImageLayout>(R.id.nine_image)
        if (null == item.images || item.images!!.isEmpty()) {
            if (item.url!!.endsWith(".jpg")) {
                val strings = ArrayList<String>()
                strings.add(item.url!!)
                nineImageLayout.setUrlList(strings)
            } else {
                nineImageLayout.setUrlList(null)
            }
        } else
            nineImageLayout.setUrlList(item.images)
    }
}
