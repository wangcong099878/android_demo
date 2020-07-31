package the.one.demo.ui.activity;

import android.Manifest;
import android.os.Bundle;

import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import the.one.base.ui.activity.BaseFragmentActivity;
import the.one.base.ui.fragment.BaseFragment;
import the.one.demo.ui.fragment.SampleFragment;
import the.one.demo.skin.SkinManager;


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
 * @date 2020/1/15 0015
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SampleActivity extends BaseFragmentActivity implements  Observer<Boolean>,QMUISkinManager.OnSkinChangeListener {

    @Override
    protected void onStart() {
        super.onStart();
        if(getSkinManager() != null){
            getSkinManager().addSkinChangeListener(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(getSkinManager() != null){
            getSkinManager().removeSkinChangeListener(this);
        }
    }

    @Override
    public void onSkinChange(QMUISkinManager skinManager, int oldSkin, int newSkin) {
        if (newSkin == SkinManager.SKIN_WHITE) {
            QMUIStatusBarHelper.setStatusBarLightMode(SampleActivity.this);
        } else {
            QMUIStatusBarHelper.setStatusBarDarkMode(SampleActivity.this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
    }

    protected void requestPermission() {
        new RxPermissions(this)
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET)
                .subscribe(this);
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return new SampleFragment();
    }

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

    }

}
