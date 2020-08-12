package the.one.base.ui.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.rxjava.rxlife.Scope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import the.one.base.ui.view.BaseDataView
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
open class BaseListPresenterK<T>(
        owner: LifecycleOwner,
        view: BaseDataView<T>
) : BasePresenterK(owner, view){



}