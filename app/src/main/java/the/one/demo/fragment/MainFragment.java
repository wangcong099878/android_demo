package the.one.demo.fragment;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import the.one.base.base.fragment.BaseGroupListFragment;
import the.one.demo.R;
import the.one.demo.activity.CalendarActivity;
import the.one.demo.activity.SimpleActivity;
import the.one.demo.fragment.bottomIndex.SimpleHomeFragment;
import the.one.demo.fragment.titleTabViewPager.SimpleTabFragment;


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
public class MainFragment extends BaseGroupListFragment implements View.OnClickListener {

    QMUICommonListItemView DATA_FRAGMENT, BOTTOM_INDEX, TAB, ACTIVITY,Calendar,Contact;

    @Override
    protected void addGroupListView() {
        mTopLayout.setTitle(getStringg(R.string.app_name)).getPaint().setFakeBoldText(true);
        BOTTOM_INDEX = CreateItemView(TYPE_CHEVRON, "主页形式");
        DATA_FRAGMENT = CreateItemView(TYPE_CHEVRON, "数据加载");
        ACTIVITY = CreateItemView(TYPE_CHEVRON, "Activity");
        TAB = CreateItemView(TYPE_CHEVRON, "TitleBar+TabLayout+ViewPager");

        Calendar = CreateItemView(TYPE_CHEVRON,"日历");
        Contact = CreateItemView(TYPE_CHEVRON,"联系人");

        QMUIGroupListView.newSection(getContext())
                .addItemView(BOTTOM_INDEX, this)
                .addItemView(DATA_FRAGMENT, this)
                .addItemView(TAB, this)
                .addItemView(ACTIVITY, this)
                .addItemView(Calendar, this)
                .addItemView(Contact, this)
                .addTo(mGroupListView);

    }

    @Override
    public void onClick(View view) {
        if (view == BOTTOM_INDEX) {
            startFragment(new SimpleHomeFragment());
        } else if (view == DATA_FRAGMENT) {
            startFragment(SimpleDataFragment.newInstance(2,true));
        } else if (view == TAB) {
            startFragment(new SimpleTabFragment());
        } else if (view == ACTIVITY) {
            startActivity(SimpleActivity.class);
        }else if(view == Calendar){
            startActivity(CalendarActivity.class);
        }else if (view == Contact) {
            startFragment(new LetterSearchFragment());
        }
    }
}
