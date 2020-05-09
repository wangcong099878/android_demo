package the.one.base.ui.fragment;

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

import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import the.one.base.R;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.widge.overscroll.OverScrollScrollView;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe QMUIGroupListView 的一些封装
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseGroupListFragment extends BaseFragment implements View.OnClickListener {

    protected int NO_DRAWABLE = -1;

    protected FrameLayout mParentLayout;
    protected QMUIGroupListView mGroupListView;
    /**
     * 有拉弹效果的ScrollView
     * 拉弹的背景颜色和父布局的颜色一样
     * 如果需要拉弹的背景颜色就修改mParentLayout的背景颜色
     */
    protected OverScrollScrollView mScrollView;

    protected abstract void addGroupListView();

    protected int getScrollViewParentBgColor() {
        return R.color.qmui_config_color_background;
    }

    /**
     * 顶部的布局是否使用子类的
     * 这样顶部的布局就被包裹在了OverScrollScrollView里面，可以有拉弹效果
     * @return
     */
    protected boolean isTopLayoutUseChildLayout() {
        return false;
    }

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
        mParentLayout = view.findViewById(R.id.parent);
        mGroupListView = view.findViewById(R.id.groupListView);
        mScrollView = view.findViewById(R.id.scrollView);
        mParentLayout.setBackgroundColor(getColorr(getScrollViewParentBgColor()));
        if(isTopLayoutUseChildLayout()||!isNeedAround()){
            // 当isNeedAround()为true时，在BaseFragment里会先把getTopLayout()放到flTopLayout，所以这里要移除掉
            if(null != flTopLayout){
                flTopLayout.removeAllViews();
            }
            flTopLayout = view.findViewById(R.id.fl_child_top_layout);
            setCustomLayout(flTopLayout,getTopLayout());
        }
        if (!isNeedAround()) {
            flBottomLayout = view.findViewById(R.id.fl_child_bottom_layout);
            setCustomLayout(flBottomLayout,getBottomLayout());
        }
        addGroupListView();
    }

    /**
     * @param title       section 标题
     * @param description section 描述
     * @param items
     */
    protected void addToGroup(String title, String description, QMUICommonListItemView... items) {
        QMUIGroupListView.Section section = QMUIGroupListView.newSection(_mActivity);
        if (!TextUtils.isEmpty(title)) section.setTitle(title);
        if (!TextUtils.isEmpty(description)) section.setDescription(description);
        for (QMUICommonListItemView itemView : items) {
            section.addItemView(itemView, this);
        }
        section.addTo(mGroupListView);
    }

    protected void addToGroup(String title, QMUICommonListItemView... items) {
        addToGroup(title, "", items);
    }

    protected void addToGroup(QMUICommonListItemView... items) {
        addToGroup("", "", items);
    }

    /**
     * @param drawable 左边图片资源
     * @param title    标题
     * @param detail   内容
     * @param position 内容位置 true 右边 false 下边
     * @param type     类型
     * @param view     右边自定义View 如果不为空 chevron设置无效
     * @return
     */
    public QMUICommonListItemView CreateItemView(int drawable, CharSequence title, CharSequence detail, boolean position, int type, View view) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(title);
        if (!TextUtils.isEmpty(detail)) {
            itemView.setDetailText(detail);
            itemView.setOrientation(position ? QMUICommonListItemView.HORIZONTAL : QMUICommonListItemView.VERTICAL);
        }
        if (null != view) {
            itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
            itemView.addAccessoryCustomView(view);
        } else {
            itemView.setAccessoryType(type);
        }
        if (drawable != NO_DRAWABLE)
            itemView.setImageDrawable(getDrawablee(drawable));
        return itemView;
    }

    /**
     * 普通标题+箭头
     *
     * @param drawable 左边图片资源
     * @param title    标题
     * @return
     */
    public QMUICommonListItemView CreateNormalItemView(int drawable, CharSequence title) {
        return CreateItemView(drawable, title, null, true, QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON, null);
    }

    /**
     * 普通标题+箭头
     *
     * @param title 标题
     * @return
     */
    public QMUICommonListItemView CreateNormalItemView(CharSequence title) {
        return CreateNormalItemView(NO_DRAWABLE, title);
    }

    /**
     * 创建标题+内容格式
     *
     * @param drawable    左边图片资源
     * @param title       标题
     * @param detail      内容
     * @param position    内容方向 true 右边 false 下边
     * @param needChevron 是否需要箭头
     * @return
     */
    public QMUICommonListItemView CreateDetailItemView(int drawable, CharSequence title, CharSequence detail, boolean position, boolean needChevron) {
        return CreateItemView(drawable, title, detail, position, needChevron ? QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON : QMUICommonListItemView.ACCESSORY_TYPE_NONE, null);
    }

    /**
     * 创建标题+内容格式
     *
     * @param title       标题
     * @param detail      内容
     * @param position    内容方向 true 右边 false 下边
     * @param needChevron 是否需要箭头
     * @return
     */
    public QMUICommonListItemView CreateDetailItemView(CharSequence title, CharSequence detail, boolean position, boolean needChevron) {
        return CreateDetailItemView(NO_DRAWABLE, title, detail, position, needChevron);
    }

    /**
     * 创建标题+内容格式
     *
     * @param drawable    左边图片资源
     * @param title       标题
     * @param detail      内容
     * @param needChevron 是否需要箭头
     * @return
     */
    public QMUICommonListItemView CreateDetailItemView(int drawable, CharSequence title, CharSequence detail, boolean needChevron) {
        return CreateDetailItemView(drawable, title, detail, true, needChevron);
    }

    /**
     * 创建标题+内容格式
     *
     * @param title       标题
     * @param detail      内容
     * @param needChevron 是否需要箭头
     * @return
     */
    public QMUICommonListItemView CreateDetailItemView(CharSequence title, CharSequence detail, boolean needChevron) {
        return CreateDetailItemView(NO_DRAWABLE, title, detail, true, needChevron);
    }

    /**
     * 创建标题+内容格式
     *
     * @param drawable 左边图片资源
     * @param title    标题
     * @param detail   内容
     * @return
     */
    public QMUICommonListItemView CreateDetailItemView(int drawable, String title, String detail) {
        return CreateItemView(drawable, title, detail, true, QMUICommonListItemView.ACCESSORY_TYPE_NONE, null);
    }

    /**
     * 创建标题+内容格式
     *
     * @param title  标题
     * @param detail 内容
     * @return
     */
    public QMUICommonListItemView CreateDetailItemView(CharSequence title, CharSequence detail) {
        return CreateItemView(NO_DRAWABLE, title, detail, true, QMUICommonListItemView.ACCESSORY_TYPE_NONE, null);
    }

    /**
     * 创建标题+内容格式
     *
     * @param title 标题
     * @return
     */
    public QMUICommonListItemView CreateDetailItemView(CharSequence title) {
        return CreateItemView(NO_DRAWABLE, title, "", true, QMUICommonListItemView.ACCESSORY_TYPE_NONE, null);
    }

    /**
     * 标题+内容+switch
     *
     * @param drawable 图片资源
     * @param title    标题
     * @param detail   内容
     * @param listener 监听
     * @return
     */
    public QMUICommonListItemView CreateSwitchItemView(int drawable, CharSequence title, CharSequence detail, CompoundButton.OnCheckedChangeListener listener) {
        QMUICommonListItemView itemView = CreateItemView(drawable, title, detail, false, QMUICommonListItemView.ACCESSORY_TYPE_SWITCH, null);
        itemView.getSwitch().setOnCheckedChangeListener(listener);
        return itemView;
    }

    /**
     * 标题+switch
     *
     * @param drawable 图片资源
     * @param title    标题
     * @param listener 监听
     * @return
     */
    public QMUICommonListItemView CreateSwitchItemView(int drawable, CharSequence title, CompoundButton.OnCheckedChangeListener listener) {
        return CreateSwitchItemView(drawable, title, "", listener);
    }

    /**
     * 标题+内容+switch
     *
     * @param title    标题
     * @param detail   内容
     * @param listener 监听
     * @return
     */
    public QMUICommonListItemView CreateSwitchItemView(CharSequence title, String detail, CompoundButton.OnCheckedChangeListener listener) {
        return CreateSwitchItemView(NO_DRAWABLE, title, detail, listener);
    }

    /**
     * 标题+switch
     *
     * @param title    标题
     * @param listener 监听
     * @return
     */
    public QMUICommonListItemView CreateSwitchItemView(CharSequence title, CompoundButton.OnCheckedChangeListener listener) {
        return CreateSwitchItemView(title, "", listener);
    }

    /**
     * 标题+详情+右边自定义View
     *
     * @param drawable 图片资源
     * @param title    标题
     * @param detail   详情
     * @param view     自定义View
     * @return
     */
    public QMUICommonListItemView CreateCustomView(int drawable, CharSequence title, CharSequence detail, View view) {
        return CreateItemView(drawable, title, detail, false, -1, view);
    }

    /**
     * 标题+详情+右边自定义View
     *
     * @param title  标题
     * @param detail 详情
     * @param view   自定义View
     * @return
     */
    public QMUICommonListItemView CreateCustomView(CharSequence title, CharSequence detail, View view) {
        return CreateCustomView(NO_DRAWABLE, title, detail, view);
    }

    /**
     * 标题+右边自定义View
     *
     * @param title 标题
     * @param view  自定义View
     * @return
     */
    public QMUICommonListItemView CreateCustomView(CharSequence title, View view) {
        return CreateCustomView(title, "", view);
    }

    /**
     * 标题+详情+右边自定义View
     *
     * @param drawable 图片资源
     * @param title    标题
     * @param view     自定义View
     * @return
     */
    public QMUICommonListItemView CreateCustomView(int drawable, CharSequence title, View view) {
        return CreateCustomView(drawable, title, "", view);
    }

    /**
     * 显示新提示
     * @param isDot true RedDot  false NewTip
     * @param itemViews 需要显示的QMUICommonListItemView
     */
    protected void showNewTips(boolean isDot, QMUICommonListItemView... itemViews){
        showNewTips(QMUICommonListItemView.TIP_POSITION_RIGHT,isDot,itemViews);
    }

    /**
     * 显示新提示
     * @param tipPosition 显示方向
     * @param isDot true RedDot  false NewTip
     * @param itemViews 需要显示的QMUICommonListItemView
     */
    protected void showNewTips(@QMUICommonListItemView.QMUICommonListItemTipPosition int tipPosition, boolean isDot, QMUICommonListItemView... itemViews){
        for (QMUICommonListItemView itemView:itemViews){
            itemView.setTipPosition(tipPosition);
            if(isDot){
                itemView.showRedDot(true);
            }else{
                itemView.showNewTip(true);
            }
        }
    }

}
