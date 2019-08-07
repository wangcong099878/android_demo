package the.one.kotlin.fragment

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import the.one.base.base.activity.PhotoWatchActivity
import the.one.base.base.fragment.BaseDataFragment
import the.one.base.base.activity.BaseWebExplorerActivity
import the.one.base.base.presenter.BasePresenter
import the.one.base.constant.DataConstant
import the.one.kotlin.Constant
import the.one.kotlin.adapter.GankAdapter
import the.one.kotlin.adapter.WelfareAdapter
import the.one.kotlin.model.GankBean
import the.one.kotlin.presenter.GankPresenter
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
class GankFragment : BaseDataFragment<GankBean>() {

    private var mType: String? = null
    private var isWelfare: Boolean = false
    private var gankPresenter: GankPresenter? = null

    override fun initView(rootView: View) {
        goneView(mTopLayout)
        mType = arguments!!.getString(DataConstant.TYPE)
        isWelfare = mType == Constant.WELFARE
        super.initView(rootView)
    }

    override fun onAnimationEndInit(): Boolean = false

    override fun getPresenter(): BasePresenter<*>? {
        gankPresenter = GankPresenter()
        return gankPresenter
    }

    override fun setType(): Int =  if (isWelfare) BaseDataFragment.TYPE_STAGGERED else BaseDataFragment.TYPE_LIST

    override fun getAdapter(): BaseQuickAdapter<*, *> = if (isWelfare) WelfareAdapter() else GankAdapter()

    override fun requestServer() {
        gankPresenter!!.getData(_mActivity, mType, page)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (!isWelfare) {
            val bean = adapter.getItem(position) as GankBean?
            BaseWebExplorerActivity.newInstance(_mActivity, bean!!.desc, bean!!.url)
        } else {
            val datas = adapter.data as List<GankBean>
            val images = ArrayList<String>()
            for (gankBean  in datas) {
                gankBean.url?.let { images.add(it) }
            }
            PhotoWatchActivity.startThisActivity(_mActivity,view, images, position)
        }
    }

    override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int): Boolean {
        return false
    }

    companion object {

        fun newInstance(type: String): GankFragment {
            val fragment = GankFragment()
            val bundle = Bundle()
            bundle.putString(DataConstant.TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }
}
