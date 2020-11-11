package the.one.gank.ui.fragment

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.qmuiteam.qmui.qqface.QMUIQQFaceView
import com.qmuiteam.qmui.util.QMUIColorHelper
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.util.QMUIResHelper
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorGravity
import the.one.base.model.SamplePageInfoBean
import the.one.base.ui.activity.BaseWebExplorerActivity
import the.one.base.ui.fragment.BaseListFragment
import the.one.base.ui.presenter.BasePresenter
import the.one.base.util.QMUIStatusBarHelper
import the.one.base.util.ViewUtil
import the.one.base.widge.OffsetLinearLayoutManager
import the.one.gank.R
import the.one.gank.bean.BannerBean
import the.one.gank.bean.HomeSection
import the.one.gank.constant.NetUrlConstantV2
import the.one.gank.ui.adapter.BannerViewHolder
import the.one.gank.ui.adapter.Home2Adapter
import the.one.gank.ui.presenter.HomePresenterK
import the.one.gank.ui.view.HomeViewK


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
 * @date 2020/8/12 0012
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class HomeV2FragmentK : BaseListFragment<HomeSection>(), HomeViewK {

    private lateinit var mBannerViewPager: BannerViewPager<BannerBean, BannerViewHolder>
    private lateinit var mTitleView: QMUIQQFaceView

    private var mBannerBeanData: List<BannerBean>? = null
    private var mTitleStr: String = ""

    private var mStatusBarHeight: Int = 0
    private var mBannerHeight: Int = 0
    private var isLightMode: Boolean = false
    private var isFragmentVisible: Boolean = false
    private lateinit var mPresenter: HomePresenterK
    private var isBannerFirst :Boolean = true


    override fun getAdapter(): BaseQuickAdapter<*, *> {
        return Home2Adapter()
    }

    override fun initView(rootView: View?) {
        mBannerHeight = QMUIDisplayHelper.dp2px(_mActivity, 250)
        mStatusBarHeight = QMUIStatusBarHelper.getStatusbarHeight(_mActivity) / 2
        mTopLayout.setBackgroundColor(getColor(R.color.qmui_config_color_transparent))
        mTitleView = mTopLayout.topBar.titleView
        mTitleView.paint.isFakeBoldText = true
        mPresenter = HomePresenterK(this,this)
        super.initView(rootView)
        initBanner()
        ViewUtil.setMargins(mStatusLayout, 0, 0, 0, 0)
        mStatusLayout.fitsSystemWindows = false
        recycleView.setItemViewCacheSize(50)
        mPresenter.getBanner(isBannerFirst)
    }

    private fun initBanner() {
        mBannerViewPager = getView(R.layout.banner_viewpager) as BannerViewPager<BannerBean, BannerViewHolder>
        mBannerViewPager.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mBannerHeight)
        mBannerViewPager
                .setIndicatorGravity(IndicatorGravity.END)
                .setIndicatorSliderColor(getColor(R.color.white), QMUIResHelper.getAttrColor(_mActivity, R.attr.app_skin_primary_color))
                .setHolderCreator { BannerViewHolder() }
                .setOnPageClickListener { position: Int ->
                    val bannerBean = mBannerBeanData!![position]
                    BaseWebExplorerActivity.newInstance(_mActivity, bannerBean.title, bannerBean.url)
                }
        adapter.addHeaderView(mBannerViewPager)
    }

    override fun getLayoutManager(type: Int): RecyclerView.LayoutManager {
        return OffsetLinearLayoutManager(_mActivity)
    }

    override fun getOnScrollListener(): RecyclerView.OnScrollListener? {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val offsetLinearLayoutManager = recyclerView.layoutManager as OffsetLinearLayoutManager?
                val y = offsetLinearLayoutManager!!.computeVerticalScrollOffset(null)
                val height = mBannerHeight - mTopLayout.height
                //                int height = mBannerHeight/2;
                val percent: Float
                percent = if (y >= height) 1f else y / (height * 1.0f)
                val isLight = percent > 0.7
                if (!isLightMode && isLight) {
                    setStatusBarMode(true)
                } else if (isLightMode && !isLight) {
                    setStatusBarMode(false)
                }
                //                setBannerStatus(percent == 1);
                mTitleView.setTextColor(QMUIColorHelper.setColorAlpha(getColor(R.color.qmui_config_color_gray_1), percent))
                mTopLayout.updateBottomSeparatorColor(QMUIColorHelper.setColorAlpha(getColor(R.color.qmui_config_color_separator), percent))

                // 两种写法
                // 1
                mTopLayout.setBackgroundColor(QMUIColorHelper.setColorAlpha(getColor(R.color.qmui_config_color_white), percent))
                // 2
                //mTopLayout.setBackgroundAlpha((int) (percent * 255));
                updateTitle()
            }
        }
    }

    override fun onMoveTarget(offset: Int) {
        if (offset > mStatusBarHeight && !isLightMode) {
            setStatusBarMode(true)
        } else if (offset < mStatusBarHeight && isLightMode) {
            setStatusBarMode(false)
        }
    }

    override fun requestServer() {
        mPresenter.getData(isFirst, NetUrlConstantV2.HOT_TYPE_LIKES, page)
    }

    private fun updateTitle() {
        val linearLayoutManager = recycleView.layoutManager as LinearLayoutManager? ?: return
        val position = linearLayoutManager.findFirstVisibleItemPosition()
        if (position > 0) {
            val section = adapter.data[position] as HomeSection?
            if (null != section) {
                setTitle(section.header)
            }
        } else {
            setTitle("本周最热")
        }
    }

    private fun setTitle(title: String) {
        if (mTitleStr != title) {
            mTitleStr = title
            mTitleView.text = mTitleStr
        }
    }

    /**
     * 更改状态栏模式
     * 由于只有折叠和展开时才会调用，所以在这里对轮播也进行处理下
     * @param isLight
     * @remark 显示的时候才做更改
     */
    private fun setStatusBarMode(isLight: Boolean) {
        isLightMode = isLight
        if (isFragmentVisible) {
            setBannerStatus(isLight)
            updateStatusBarMode(isLight)
        }
    }

    /**
     * 设置轮播状态
     * @param start
     */
    private fun setBannerStatus(start: Boolean) {
        if(isLightMode) return
        if (start) {
            mBannerViewPager.stopLoop()
        } else {
            mBannerViewPager.startLoop()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
        super.onPause()
        isFragmentVisible = false
        setBannerStatus(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onLazyResume() {
        isFragmentVisible = true
        super.onLazyResume()
        setBannerStatus(true)
    }

    override fun getPresenter(): BasePresenter<*>? {
        return null
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val itemSection = adapter.getItem(position) as HomeSection
        val bean = itemSection.t
        if (itemSection.header == NetUrlConstantV2.CATEGORY_GIRL) return
        BaseWebExplorerActivity.newInstance(_mActivity, bean.desc, bean.url)
    }

    override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onBannerComplete(data: List<BannerBean>?) {
        mBannerBeanData = data
        mBannerViewPager.create(data)
        mBannerViewPager.startLoop()
        isBannerFirst = false
    }

    override fun onHotComplete(data: List<HomeSection>?) {
        onComplete(data, SamplePageInfoBean(3, page))
        setStatusBarMode(false)
    }

    override fun onError(msg: String?) {

    }

    override fun translucentFull(): Boolean = true

    override fun isNeedChangeStatusBarMode(): Boolean = true

    override fun isStatusBarLightMode(): Boolean = isLightMode

    override fun isNeedSpace(): Boolean = false

}
