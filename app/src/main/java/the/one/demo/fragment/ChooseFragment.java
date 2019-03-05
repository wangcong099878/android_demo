package the.one.demo.fragment;

import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import the.one.base.base.fragment.AddressBookFragment;
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
 * @date 2019/2/13 0013
 * @describe 选择联系人
 * @email 625805189@qq.com
 * @remark
 */
public class ChooseFragment extends AddressBookFragment {

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoose();
            }
        });
    }

    private void showChoose(){
        QMUIDialogUtil.showLongMessageDialog(_mActivity, "选择的联系人", mAdapter.getSelects().toString(), "确定", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        });

    }
}
