package the.one.base.Interface;

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

import android.content.DialogInterface;
import android.view.KeyEvent;

/**
 * @author The one
 * @date 2019/10/21 0021
 * @describe 返回键监听
 * @email 625805189@qq.com
 * @remark
 */
public class IOnKeyBackClickListener implements DialogInterface.OnKeyListener {

    /**
     * 是否强制让返回键无作用
     */
    private boolean isForce = true;

    public IOnKeyBackClickListener() {
    }

    public IOnKeyBackClickListener(boolean isForce) {
        this.isForce = isForce;
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && isForce;
    }

}
