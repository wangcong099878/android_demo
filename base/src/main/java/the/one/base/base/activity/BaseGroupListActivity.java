package the.one.base.base.activity;

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
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import the.one.base.R;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseGroupListActivity extends BaseActivity {

    public int TYPE_NORMAL = 1;// 普通样式
    public int TYPE_CHEVRON = 2;// 右边带角
    public int TYPE_RIGHT_DETAIL = 3;// 右边详情
    public int TYPE_BOTTOM_DETAIL = 4;// 下边详情

    public QMUIGroupListView mGroupListView;
    public FrameLayout flTopLayout, flBottomLayout;

    protected abstract void addGroupListView();

    @Override
    protected int getContentViewId() {
        return R.layout.base_group_list;
    }


    @Override
    protected void initView(View view) {
        mGroupListView = view.findViewById(R.id.groupListView);
        flTopLayout = view.findViewById(R.id.fl_top_layout);
        flBottomLayout = view.findViewById(R.id.fl_bottom_layout);
        addGroupListView();
    }

//        QMUIGroupListView.newSection(this)
//                .setTitle("Section 1: 默认提供的样式")
//             .setDescription("Section 1 的描述")
//             .addItemView(itemWithSwitch, onClickListener)
//              .addTo(mGroupListView);

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
