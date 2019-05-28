package the.one.base.base.fragment;

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

import android.view.View;

import the.one.base.R;
import the.one.base.widge.PopupLayout;

/**
 * @author The one
 * @date 2019/5/15 0015
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BasePopupLayoutFragment extends BaseDialogFragment {

    private PopupLayout mPopupLayout;

    @Override
    protected int getLayout() {
        return R.layout.fragment_popup_layout;
    }

    @Override
    protected void initView(View view) {
        mPopupLayout = view.findViewById(R.id.popup_layout);
        mPopupLayout.show();
    }
}
