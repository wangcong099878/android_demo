package the.one.demo.ui.simple;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.base.fragment.BaseGroupListFragment;


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
 * @date 2019/4/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SimpleFragment extends BaseGroupListFragment {

    private QMUICommonListItemView PICTURE_SELECTOR;

    @Override
    protected void addGroupListView() {
        initFragmentBack("使用示例");
        PICTURE_SELECTOR = CreateItemView(TYPE_CHEVRON,"选择图片、视频、音频");
        addToGroup(PICTURE_SELECTOR);
    }

    @Override
    public void onClick(View v) {
        if(v == PICTURE_SELECTOR){
            startFragment(new SelectPictureFragment());
        }
    }
}
