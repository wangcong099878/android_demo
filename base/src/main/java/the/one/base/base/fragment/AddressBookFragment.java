package the.one.base.base.fragment;

import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import the.one.base.Interface.ReadContactCompleteListener;
import the.one.base.R;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.model.Contact;
import the.one.base.util.ContactUtil;
import the.one.base.util.QMUIDialogUtil;


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
 * @date 2019/1/18 0018
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class AddressBookFragment extends BaseLetterSearchFragment<Contact> {

    protected TextView tvLeft, tvRight;

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        initFragmentBack("联系人");
        View view = getView(R.layout.custom_address_book_bottom_layout);
        tvLeft = view.findViewById(R.id.tv_left);
        tvRight = view.findViewById(R.id.tv_right);
        flBottomLayout.addView(view, -1, -1);
        goneView(flBottomLayout, tvLeft);
        checkPermission();

    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        final RxPermissions permissions = new RxPermissions(this);
        permissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CONTACTS
                )
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.e(TAG, "onNext: "+aBoolean );
                        if (aBoolean)
                            requestServer();
                        else {
                            QMUIDialogUtil.showSimpleDialog(_mActivity,"What?","不给权限怎么加？");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void requestServer() {
        showLoadingDialog("加载中");
        new Thread() {
            @Override
            public void run() {
                super.run();
                ContactUtil.getInstance().getContactList(_mActivity, new ReadContactCompleteListener() {
                    @Override
                    public void onComplete(final List<Contact> contacts) {
                        _mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideLoadingDialog();
                                notifyData(contacts, "没有联系人", "", null);
                            }
                        });
                    }
                });
            }
        }.start();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (mAdapter.isShowCheckBox()) {
            mAdapter.setSelects(position, getDatas().get(position));
            mTopLayout.setTitle("已选择" + mAdapter.getSelects().size() + "项");
        }
    }

    @Override
    public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        mAdapter.setShowCheckBox(true);
        mAdapter.setSelects(position, getDatas().get(position));
        initSelectTopBar();
        return true;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    protected Button topRightText;

    private void initSelectTopBar() {
        showView(flBottomLayout);
        mTopLayout.removeAllLeftViews();
        mTopLayout.addLeftTextButton("取消", R.id.topbar_left_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.getSelects().clear();
                mAdapter.setShowCheckBox(false);
                mTopLayout.removeAllLeftViews();
                mTopLayout.removeAllRightViews();
                goneView(flBottomLayout);
                initFragmentBack("联系人");
            }
        });
        mTopLayout.setTitle("已选择1项");
        topRightText = mTopLayout.addRightTextButton("全选", R.id.topbar_right_1);
        topRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdapter.getSelects().size() == getDatas().size()) {
                    // 已经是全选状态，点击后变成全不选
                    topRightText.setText("全选");
                    mAdapter.selectAll(false);
                    mTopLayout.setTitle("请选择");

                } else {
                    // 已经是全选状态，点击后变成全不选
                    topRightText.setText("全不选");
                    mAdapter.selectAll(true);
                    mTopLayout.setTitle("已选择" + mAdapter.getSelects().size() + "项");
                }
            }
        });
    }
}
