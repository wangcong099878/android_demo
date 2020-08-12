package the.one.base.ui.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.rxjava.rxlife.Scope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import the.one.base.ui.view.BaseView


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
open class BasePresenterK(owner: LifecycleOwner,var view : BaseView?) : Scope, LifecycleEventObserver {

    init {
        owner.lifecycle.addObserver(this)
    }

    private var mDisposables : CompositeDisposable ? = null

    private fun addDisposable(disposable: Disposable) {
        var disposables = mDisposables
        if (disposables == null) {
            mDisposables = CompositeDisposable()
            disposables = mDisposables
        }
        disposables?.add(disposable)
    }

    private fun dispose() {
        val disposables = mDisposables ?: return
        disposables.dispose()
    }

    override fun onScopeEnd() {
    }

    override fun onScopeStart(d: Disposable?) {
        addDisposable(d!!)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if(event == Lifecycle.Event.ON_DESTROY){
            source.lifecycle.removeObserver(this)
            view = null
            dispose()
        }
    }

}