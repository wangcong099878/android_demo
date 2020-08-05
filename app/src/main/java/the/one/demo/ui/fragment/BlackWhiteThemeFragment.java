package the.one.demo.ui.fragment;

import android.view.View;
import android.widget.CompoundButton;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.ui.fragment.BaseGroupListFragment;
import the.one.base.util.AppMourningThemeUtil;
import the.one.base.util.QMUIDialogUtil;
import the.one.base.util.crash.CrashUtil;


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
 * @date 2020/4/8 0008
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BlackWhiteThemeFragment extends BaseGroupListFragment {

    private QMUICommonListItemView NOTIFY_THEME;

    @Override
    protected void addGroupListView() {
        setTitleWithBackBtn("AppMourningThemeUtil");
        NOTIFY_THEME = CreateSwitchItemView("黑白化主题","哀悼...",AppMourningThemeUtil.isMourningTheme(), new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked != AppMourningThemeUtil.isMourningTheme()){
                    showRestartDialog(isChecked);
                }
            }
        });
        addToGroup(NOTIFY_THEME);
    }

    private void showRestartDialog(final boolean isOpen){
        String status = isOpen?"开启":"关闭";
        QMUIDialogUtil.showNegativeDialog(_mActivity, "", status+"成功，重启后生效", "关闭", "立即重启", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                CrashUtil.restartApplication(_mActivity);
            }
        });
        //放在后面，不然设置了，dialog就会先变了。
        AppMourningThemeUtil.setMourningTheme(isOpen);
    }

    @Override
    public void onClick(View v) {

    }
}
