package the.one.demo.ui.sample;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.ArrayList;
import java.util.List;

import the.one.base.base.fragment.BaseLetterSearchFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.util.QMUIDialogUtil;
import the.one.demo.R;
import the.one.demo.ui.bean.LsMans;


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
 * @date 2019/7/26 0026
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class LetterSearchFragment extends BaseLetterSearchFragment<LsMans> {

    private TextView tvTotal;

    @Override
    protected int getTopLayout() {
        return R.layout.custom_letter_search_top_search_layout;
    }

    @Override
    protected String getTitleString() {
        return "梁山好汉";
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mAdapter.addHeaderView(getView(R.layout.custom_letter_search_headr_layout));
        tvTotal = (TextView) getView(R.layout.custom_letter_search_footer_layout);
        mAdapter.addFooterView(tvTotal);
    }

    @Override
    protected void onItemClick(LsMans lsMans) {
        showSuccessTips(lsMans.name);
    }

    @Override
    protected void onConfirmSelect(List<LsMans> selects) {
        QMUIDialogUtil.showLongMessageDialog(_mActivity, "已选择", selects.toString(), "确定", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onLazyInit() {
        String[] mans = _mActivity.getResources().getStringArray(R.array.LS_mans);
        Log.e(TAG, "onLazyInit: "+mans.length );
        List<LsMans> lsMans = new ArrayList<>();
        for (String man :mans){
            lsMans.add(new LsMans(man));
        }
        tvTotal.setText(lsMans.size()+"位好汉");
        notifyData(lsMans, "没有好汉", "上梁山", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QMUIDialogUtil.FailTipsDialog(_mActivity, "溜了溜了", new QMUIDialogUtil.OnTipsDialogDismissListener() {
                    @Override
                    public void onDismiss() {
                        popBackStack();
                    }
                });
            }
        });
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
