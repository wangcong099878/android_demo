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

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import lib.homhomlib.design.SlidingLayout;
import the.one.base.R;
import the.one.base.base.presenter.BasePresenter;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseGroupListFragment extends BaseFragment implements View.OnClickListener {

    public int TYPE_NORMAL = 1;// 普通样式
    public int TYPE_CHEVRON = 2;// 右边带角
    public int TYPE_RIGHT_DETAIL = 3;// 右边详情
    public int TYPE_BOTTOM_DETAIL = 4;// 下边详情

    public QMUIGroupListView mGroupListView;
    protected SlidingLayout slidingLayout;

    protected abstract void addGroupListView();

    @Override
    protected int getContentViewId() {
        return R.layout.base_group_list;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(View view) {
        slidingLayout = view.findViewById(R.id.slidingLayout);
        mGroupListView = view.findViewById(R.id.groupListView);
        addGroupListView();
    }

    protected void addToGroup(QMUICommonListItemView... items){
        addToGroup("","",items);
    }

    /**
     *
     * @param title section 标题
     * @param description section 描述
     * @param items
     */
    protected void addToGroup(String title,String description,QMUICommonListItemView... items){
        QMUIGroupListView.Section section  = QMUIGroupListView.newSection(_mActivity);
        if(TextUtils.isEmpty(title)) section.setTitle(title);
        if(TextUtils.isEmpty(description)) section.setDescription(description);
        for (QMUICommonListItemView itemView:items){
            section.addItemView(itemView,this);
        }
        section.addTo(mGroupListView);
    }

    public QMUICommonListItemView CreateItemView(int type, String content) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(content);
        if (type == TYPE_NORMAL) {
            itemView.setOrientation(QMUICommonListItemView.VERTICAL);
        } else if (type == TYPE_CHEVRON) {
            itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        }
        return itemView;
    }

    public QMUICommonListItemView CreateItemView(int type, String content, int drawable) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(content);
        itemView.setImageDrawable(getDrawablee(drawable));
        if (type == TYPE_NORMAL) {
            itemView.setOrientation(QMUICommonListItemView.VERTICAL);
        } else if (type == TYPE_CHEVRON) {
            itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        }
        return itemView;
    }


    public QMUICommonListItemView CreateItemView(int type, String content, String detail) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(content);
        if (type == TYPE_RIGHT_DETAIL) {
            itemView.setDetailText(detail);
        } else if (type == TYPE_BOTTOM_DETAIL) {
            itemView.setOrientation(QMUICommonListItemView.VERTICAL);
            itemView.setDetailText(detail);
        }
        return itemView;
    }

    public QMUICommonListItemView CreateItemView(int type, String content, String detail,int drawable) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(content);
        itemView.setImageDrawable(getDrawablee(drawable));
        if (type == TYPE_RIGHT_DETAIL ||type == TYPE_CHEVRON) {
            itemView.setDetailText(detail);
            if(type == TYPE_CHEVRON){
                itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
            }
        } else if (type == TYPE_BOTTOM_DETAIL) {
            itemView.setOrientation(QMUICommonListItemView.VERTICAL);
            itemView.setDetailText(detail);
        }
        return itemView;
    }

    public QMUICommonListItemView CreateItemView(String content, View view) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(content);
        itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        itemView.addAccessoryCustomView(view);
        return itemView;
    }

    public QMUICommonListItemView CreateItemView(Drawable drawable, String content, View view) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(content);
        itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        itemView.setImageDrawable(drawable);
        itemView.addAccessoryCustomView(view);
        return itemView;
    }

    public QMUICommonListItemView CreateItemView(String content, CompoundButton.OnCheckedChangeListener listener) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(content);
        itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        itemView.getSwitch().setOnCheckedChangeListener(listener);
        return itemView;
    }

    public QMUICommonListItemView CreateItemView(Drawable drawable, String content, CompoundButton.OnCheckedChangeListener listener) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(content);
        itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        itemView.setImageDrawable(drawable);
        itemView.getSwitch().setOnCheckedChangeListener(listener);
        return itemView;
    }
}
