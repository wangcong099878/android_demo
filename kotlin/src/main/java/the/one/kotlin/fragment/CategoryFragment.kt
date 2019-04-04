package the.one.kotlin.fragment

import android.view.View
import the.one.base.base.fragment.BaseFragment
import the.one.base.base.fragment.BaseTitleTabFragment
import the.one.kotlin.Constant
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
 * @date 2019/4/1 0001
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

class CategoryFragment: BaseTitleTabFragment(){

    override fun initView(rootView: View?) {
        super.initView(rootView)
        mTopLayout.setTitle("GankType").paint.isFakeBoldText = true
        mTopLayout.setBackgroundDividerEnabled(false)
    }

    override fun addTabs() {
        for (i in Constant.title.indices) {
            if (Constant.title[i] == Constant.IOS)
                addTab("IOS")
            else
                addTab(Constant.title[i])
        }
    }

    override fun addFragment(fragments: ArrayList<BaseFragment>?) {
        for (i in Constant.title.indices) {
            fragments?.add(GankFragment.newInstance(Constant.title[i]))
        }
    }

}