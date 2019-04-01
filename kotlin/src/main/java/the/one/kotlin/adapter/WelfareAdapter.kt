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

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

import the.one.base.util.GlideUtil
import the.one.base.widge.ScaleImageView
import the.one.kotlin.R
import the.one.kotlin.model.GankBean

/**
 * @author The one
 * @date 2019/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class WelfareAdapter : BaseQuickAdapter<GankBean, BaseViewHolder>(R.layout.item_welfare) {

    override fun convert(helper: BaseViewHolder, item: GankBean) {
        val imageView = helper.getView<ScaleImageView>(R.id.girl_item_iv)
        imageView.setInitSize(item.width, item.height)
        GlideUtil.load(mContext, item.url, imageView)
    }
}
