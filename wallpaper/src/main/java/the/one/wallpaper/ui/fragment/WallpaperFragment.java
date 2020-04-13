package the.one.wallpaper.ui.fragment;

import android.Manifest;
import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import the.one.base.Interface.IOnKeyBackClickListener;
import the.one.base.ui.fragment.BaseFragment;
import the.one.base.ui.fragment.BaseTabOnTitleFragment;
import the.one.wallpaper.R;
import the.one.wallpaper.constant.WallpaperConstant;

public class WallpaperFragment extends BaseTabOnTitleFragment implements Observer<Boolean> {

    @Override
    protected boolean isExitFragment() {
        return true;
    }

    @Override
    protected boolean tabFromNet() {
        return true;
    }

    @Override
    protected void requestServer() {
       requestPermission();
    }

    @Override
    protected void startInit() {
        super.startInit();
        mTopLayout.addRightImageButton(R.drawable.mz_titlebar_ic_setting_dark,R.id.topbar_right_button1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startFragment(new SettingFragment());
                    }
                });
    }

    @Override
    protected void addTabs() {
        for (String title: WallpaperConstant.TYPE_TITLES){
            addTab(title);
        }
    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        for (int type:WallpaperConstant.TYPES){
            fragments.add(WallpaperItemFragment.newInstance(type));
        }
    }

    private void requestPermission() {
        final RxPermissions permissions = new RxPermissions(this);
        permissions
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Boolean aBoolean) {
        if (aBoolean) {
            startInit();
        } else {
            showFailDialog("权限被禁止，请在设置里打开权限。");
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    private void showFailDialog(String tips) {
        new QMUIDialog.MessageDialogBuilder(_mActivity)
                .setTitle("提示")
                .setMessage(tips)
                .addAction("退出", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .addAction(0, "重新获取", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        requestPermission();
                    }
                })
                .setCanceledOnTouchOutside(false)
                .show()
                .setOnKeyListener(new IOnKeyBackClickListener());
    }

}
