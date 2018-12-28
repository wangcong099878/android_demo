package the.one.demo;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import the.one.base.base.BaseGroupListFragment;


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
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class IndexFragment extends BaseGroupListFragment implements View.OnClickListener {

    QMUICommonListItemView SIMPLE_DATA_FRAGMENT;

    @Override
    protected void addGroupListView() {
        mTopLayout.setTitle(getStringg(R.string.app_name));
        SIMPLE_DATA_FRAGMENT = CreateItemView(TYPE_CHEVRON,"普通数据加载Fragment");

        QMUIGroupListView.newSection(getContext())
                .addItemView(SIMPLE_DATA_FRAGMENT,this)
                .addTo(mGroupListView);

    }

    @Override
    public void onClick(View view) {

    }
}
