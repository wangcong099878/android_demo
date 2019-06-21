package the.one.base.util;

import android.content.Context;
import android.content.DialogInterface;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

/**
 * @author The one
 * @date 2018/11/20 0020
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SexSelector {

    private String[] sexItems = {"男", "女"};
    private QMUIDialog sexDialog;

    public void show(Context context,int current,DialogInterface.OnClickListener listener) {
        if (null == sexDialog) {
            sexDialog = QMUIDialogUtil.showSingleChoiceDialog(context, sexItems, current, listener);
        } else
            sexDialog.show();
    }

    public int getCurrentCheckIndex(String sex){
        int index = -1;
        for (int i = 0; i < sexItems.length; i++) {
            if(sexItems[i].equals(sex)){
                index = i;
                break;
            }
        }
        return index;
    }

    public String getSexString(int index){
        return sexItems[index];
    }

}
