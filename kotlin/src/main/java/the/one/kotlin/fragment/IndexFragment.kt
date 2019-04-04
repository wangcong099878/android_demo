package the.one.kotlin.fragment

import the.one.base.base.fragment.BaseFragment
import the.one.base.base.fragment.BaseHomeFragment
import the.one.kotlin.R
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
class IndexFragment : BaseHomeFragment() {

    override fun setViewPagerSwipe(): Boolean {
        return false
    }

    override fun addTabs() {
        addTab(R.drawable.ic_home_normal, R.drawable.ic_home_selected, "主页")
        addTab(R.drawable.ic_classification_normal, R.drawable.ic_classification_selected, "分类")
    }

    override fun addFragment(fragments: ArrayList<BaseFragment>) {
        fragments.add(HomeFragment())
        fragments.add(CategoryFragment())
    }
}
