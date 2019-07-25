package the.one.kotlin.fragment

import android.support.v7.widget.GridLayoutManager
import android.view.View

import com.chad.library.adapter.base.BaseQuickAdapter

import the.one.base.base.fragment.BaseDataFragment
import the.one.base.base.presenter.BasePresenter
import the.one.kotlin.model.GankBean


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
 * @date 2019/7/23 0023
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class TestFragment : BaseDataFragment<GankBean>() {
    override fun getAdapter(): BaseQuickAdapter<*, *>? {
        return null
    }

    override fun requestServer() {
        recycleView.layoutManager = GridLayoutManager(_mActivity, 4)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }

    override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int): Boolean {
        return false
    }

    override fun getPresenter(): BasePresenter<*>? {
        return null
    }
}
