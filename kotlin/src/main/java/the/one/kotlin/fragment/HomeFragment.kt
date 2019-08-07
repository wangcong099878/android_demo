package the.one.kotlin.fragment

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout
import com.qmuiteam.qmui.widget.QMUITopBar
import com.qmuiteam.qmui.widget.section.QMUISection
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter
import com.qmuiteam.qmui.widget.section.QMUIStickySectionLayout
import the.one.base.base.fragment.BaseSectionLayoutFragment
import the.one.base.base.activity.BaseWebExplorerActivity
import the.one.base.base.presenter.BasePresenter
import the.one.base.util.GlideUtil
import the.one.kotlin.Constant
import the.one.kotlin.R
import the.one.kotlin.adapter.HomeAdapter
import the.one.kotlin.model.GankBean
import the.one.kotlin.model.HomeBean
import the.one.kotlin.model.HomeHeadSection
import the.one.kotlin.model.HomeItemSection
import the.one.kotlin.presenter.HomePresenter
import the.one.kotlin.view.HomeView
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
class HomeFragment : BaseSectionLayoutFragment<HomeHeadSection, HomeItemSection>(), HomeView {

    @BindView(R.id.topbar)
    lateinit var topbar: QMUITopBar
    @BindView(R.id.iv_head)
    lateinit var ivHead: ImageView
    @BindView(R.id.collapsing_topbar_layout)
    lateinit var collapsingTopbarLayout: QMUICollapsingTopBarLayout

    private var presenter: HomePresenter? = null
    private var sections: MutableList<QMUISection<HomeHeadSection, HomeItemSection>>? = null
    private var welfare: List<GankBean>? = null

    override fun showTitleBar(): Boolean  = false

    override fun isStickyHeader(): Boolean = true

    override fun onAnimationEndInit(): Boolean = false

    override fun getContentViewId(): Int  = R.layout.fragment_home

    override fun initView(rootView: View) {
        mSectionLayout = rootView.findViewById<QMUIStickySectionLayout>(R.id.section_layout)
        collapsingTopbarLayout!!.setCollapsedTitleTextColor(ContextCompat.getColor(_mActivity, R.color.we_chat_black))
        initStickyLayout()
        initData()
    }

    override fun createAdapter(): QMUIStickySectionAdapter<HomeHeadSection, HomeItemSection, QMUIStickySectionAdapter.ViewHolder> =  HomeAdapter()

    override fun requestServer() {
        presenter!!.getData(HomePresenter.TYPE_WELFARE)
        presenter!!.getData(HomePresenter.TYPE_TODAY)
    }

    override fun onWelfareComplete(data: List<GankBean>) {
        welfare = data
        setWelfare()
    }

    private fun setWelfare() {
        if (null != welfare) {
            val size = welfare!!.size
            if (size > 0) {
                val index = (Math.random() * size).toInt()
                val gankBean = welfare!![index]
                GlideUtil.load(_mActivity, gankBean.url, ivHead)
            }
        }
    }

    override fun onTodayComplete(resultsBean: HomeBean) {
        collapsingTopbarLayout!!.title = "今日最新干货"
        sections = ArrayList()
        sections!!.add(parseSection(resultsBean.Android!!, Constant.ANDROID))
        sections!!.add(parseSection(resultsBean.iOS!!, Constant.IOS))
        sections!!.add(parseSection(resultsBean.App!!, Constant.APP))
        sections!!.add(parseSection(resultsBean.relax!!, Constant.RELAX))
        sections!!.add(parseSection(resultsBean.front!!, Constant.FRONT))
        sections!!.add(parseSection(resultsBean.extension!!, Constant.EXTENSION))
        sections!!.add(parseSection(resultsBean.recommend!!, Constant.RECOMMEND))
        sections!!.add(parseSection(resultsBean.welfare!!, Constant.WELFARE))
        mAdapter.setData(sections)
    }

    private fun parseSection(gankBeans: List<GankBean>, head: String): QMUISection<HomeHeadSection, HomeItemSection> {
        val itemSections = ArrayList<HomeItemSection>()
        for (gankBean in gankBeans) {
            itemSections.add(HomeItemSection(gankBean.desc, gankBean.images, gankBean.who!!, gankBean.url!!))
        }
        return QMUISection(HomeHeadSection(head), itemSections)
    }

    override fun loadMore(section: QMUISection<HomeHeadSection, HomeItemSection>, loadMoreBefore: Boolean) {

    }

    override fun onItemClick(holder: QMUIStickySectionAdapter.ViewHolder, position: Int) {
        val itemSection = mAdapter.getSectionItem(position) as HomeItemSection?
        startFragment(BaseWebExplorerActivity.newInstance(itemSection!!.content, itemSection.url))
    }

    override fun onItemLongClick(holder: QMUIStickySectionAdapter.ViewHolder, position: Int): Boolean {
        return false
    }

    override fun getPresenter(): BasePresenter<*>? {
        presenter = HomePresenter()
        return presenter
    }

    override fun onPause() {
        super.onPause()
        setWelfare()
    }
}
