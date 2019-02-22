package the.one.anastasia.base;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CompoundButton;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import the.one.anastasia.R;


public abstract class BaseGroupListViewActivity extends BaseActivity {

    public int TYPE_NORMAL = 1;// 普通样式
    public int TYPE_CHEVRON = 2;// 右边带角
    public int TYPE_RIGHT_DETAIL = 3;// 右边详情
    public int TYPE_BOTTOM_DETAIL = 4;// 下边详情

    public QMUIGroupListView mGroupListView;

    protected abstract void addGroupListView();

    @Override
    protected boolean isShowNoNet() {
        return false;
    }

    @Override
    protected int getBodyLayout() {
        return R.layout.activity_base_group_list_view;
    }

    @Override
    protected void initView() {
        mGroupListView = findViewById(R.id.groupListView);
        addGroupListView();
    }

    @Override
    protected void initData() {
//        QMUIGroupListView.newSection(this)
//                .setTitle("Section 1: 默认提供的样式")
//                .setDescription("Section 1 的描述")
//                .addItemView(normalItem, onClickListener)
//                .addItemView(itemWithDetail, onClickListener)
//                .addItemView(itemWithDetailBelow, onClickListener)
//                .addItemView(itemWithChevron, onClickListener)
//                .addItemView(itemWithSwitch, onClickListener)
//                .addTo(mGroupListView);
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

    public QMUICommonListItemView CreateItemView(int type, String content, Drawable drawable) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(content);
        itemView.setImageDrawable(drawable);
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

    public QMUICommonListItemView CreateItemView(int type, String content, String detail, Drawable drawable) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(content);
        itemView.setImageDrawable(drawable);
        if (type == TYPE_RIGHT_DETAIL) {
            itemView.setDetailText(detail);
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


    public QMUICommonListItemView CreateItemView(String title, CompoundButton.OnCheckedChangeListener listener) {
       return CreateItemView(title, null, listener);
    }

    public QMUICommonListItemView CreateItemView(String title, String detailText, CompoundButton.OnCheckedChangeListener listener) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(title);
        itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        if (null != detailText) {
            itemView.setOrientation(QMUICommonListItemView.VERTICAL);
            itemView.setDetailText(detailText);
        }
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
