package the.one.demo.ui.sample.fragment;

import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.base.fragment.BaseGroupListFragment;
import the.one.base.util.QMUIDialogUtil;
import the.one.demo.R;


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
 * @date 2019/8/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class WhiteBgFragment extends BaseGroupListFragment {

    private String[] items = new String[32];

    private QMUICommonListItemView MULTI;
    private int[] selects ;


    @Override
    protected void initView(View view) {
        mTopLayout.setBackgroundColor(getColorr(R.color.qmui_config_color_white));
        super.initView(view);
    }

    @Override
    protected void addGroupListView() {
        initFragmentBack("WhiteBgFragment");
        for (int i = 0; i < items.length; i++) {
            items[i] = "第"+i+"项";
        }

        MULTI = CreateNormalItemView("MULTI");
        addToGroup(MULTI);
    }

    @Override
    public void onClick(View v) {
        showMultiChoiceDialog();
    }

    private void showMultiChoiceDialog(){
        QMUIDialogUtil.showMultiChoiceDialog(_mActivity, items, selects, "确定", new QMUIDialogUtil.OnMultiChoiceConfirmClickListener() {
            @Override
            public void getCheckedItemIndexes(QMUIDialog dialog, int[] checkedItems) {
                selects = checkedItems;
            }
        });
    }
}
