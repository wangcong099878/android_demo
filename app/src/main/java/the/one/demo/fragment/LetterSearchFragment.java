package the.one.demo.fragment;

import android.Manifest;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import the.one.base.base.fragment.BaseLetterSearchFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.model.Contact;
import the.one.demo.util.ContactsUtil;


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
 * @date 2019/1/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class LetterSearchFragment extends BaseLetterSearchFragment {

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        initFragmentBack("联系人");
        showLoadingDialog("加载联系人中");
        final RxPermissions permissions = new RxPermissions(this);
        permissions
                .request(
                        Manifest.permission.READ_CONTACTS
                )
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        importContacts();
                    }
                });
    }

    private void importContacts() {
        List<Contact> contacts = ContactsUtil.getContacts(getActivity());
        notifyData(contacts, "添加联系人，方便记录", "点我添加", null);
        hideLoadingDialog();
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
