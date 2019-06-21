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

import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.qmuiteam.qmui.widget.QMUIObservableScrollView;
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

    protected QMUIGroupListView mGroupListView;
    protected SlidingLayout slidingLayout;
    protected QMUIObservableScrollView mScrollView;

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
        mScrollView= view.findViewById(R.id.scrollView);
        slidingLayout.setSlidingOffset(0.1f);
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

    protected void addToGroup(QMUICommonListItemView... items) {
        addToGroup("", "", items);
    }

    protected void addToGroup(String title,QMUICommonListItemView... items) {
        addToGroup(title, "", items);
    }

    /**
     *
     * @param drawable 左边图片资源
     * @param title 标题
     * @param content 内容
     * @param position 内容位置 true 右边 false 下边
     * @param type 类型
     * @param view 右边自定义View 如果不为空 chevron设置无效
     * @return
     */
    public QMUICommonListItemView CreateItemView(int drawable,CharSequence title,CharSequence content,boolean position,int type,View view) {
        QMUICommonListItemView itemView;
        itemView = mGroupListView.createItemView(title);
        if(!TextUtils.isEmpty(content)){
            itemView.setDetailText(content);
            itemView.setOrientation(position?QMUICommonListItemView.HORIZONTAL:QMUICommonListItemView.VERTICAL);
        }
        if(null != view) {
            itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
            itemView.addAccessoryCustomView(view);
        }else{
            itemView.setAccessoryType(type);
        }
        if (drawable != -1)
            itemView.setImageDrawable(getDrawablee(drawable));
        return itemView;
    }

    /**
     * 普通标题+箭头
     * @param drawable 左边图片资源
     * @param title 标题
     * @return
     */
    public QMUICommonListItemView CreateNormalItemView(int drawable,String title){
        return CreateItemView(drawable,title,null,true,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON,null);
    }

    /**
     * 普通标题+箭头
     * @param title 标题
     * @return
     */
    public QMUICommonListItemView CreateNormalItemView(CharSequence title){
        return CreateItemView(-1,title,null,true,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON,null);
    }

    /**
     * 创建标题+内容格式
     * @param drawable 左边图片资源
     * @param title 标题
     * @param detail 内容
     * @param position 内容方向 true 右边 false 下边
     * @param needChevron 是否需要箭头
     * @return
     */
    public QMUICommonListItemView CreateDetailItemView(int drawable,String title,String detail,boolean position,boolean needChevron){
        return CreateItemView(drawable,title,detail,position,needChevron?QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON:QMUICommonListItemView.ACCESSORY_TYPE_NONE,null);
    }

    /**
     * 创建标题+内容格式
     * @param drawable 左边图片资源
     * @param title 标题
     * @param detail 内容
     * @param needChevron 是否需要箭头
     * @return
     */
    public QMUICommonListItemView CreateDetailItemView(int drawable,String title,String detail,boolean needChevron){
        return CreateItemView(drawable,title,detail,true,needChevron?QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON:QMUICommonListItemView.ACCESSORY_TYPE_NONE,null);
    }

    /**
     * 创建标题+内容格式
     * @param drawable 左边图片资源
     * @param title 标题
     * @param detail 内容
     * @return
     */
    public QMUICommonListItemView CreateDetailItemView(int drawable,String title,String detail){
        return CreateItemView(drawable,title,detail,true,QMUICommonListItemView.ACCESSORY_TYPE_NONE,null);
    }
    /**
     * 创建标题+内容格式
     * @param title 标题
     * @param detail 内容
     * @return
     */
    public QMUICommonListItemView CreateDetailItemView(CharSequence title,CharSequence detail){
        return CreateItemView(-1,title,detail,true,QMUICommonListItemView.ACCESSORY_TYPE_NONE,null);
    }

    /**
     * 标题+内容+switch
     * @param drawable 图片资源
     * @param title 标题
     * @param detail 内容
     * @param listener 监听
     * @return
     */
    public QMUICommonListItemView CreateSwitchItemView(int drawable,CharSequence title,CharSequence detail,CompoundButton.OnCheckedChangeListener listener){
        QMUICommonListItemView itemView = CreateItemView(drawable,title,detail,false,QMUICommonListItemView.ACCESSORY_TYPE_SWITCH,null);
        itemView.getSwitch().setOnCheckedChangeListener(listener);
        return itemView;
    }

    /**
     * 标题+switch
     * @param drawable 图片资源
     * @param title 标题
     * @param listener 监听
     * @return
     */
    public QMUICommonListItemView CreateSwitchItemView(int drawable,String title,CompoundButton.OnCheckedChangeListener listener){
        QMUICommonListItemView itemView = CreateItemView(drawable,title,"",false,QMUICommonListItemView.ACCESSORY_TYPE_SWITCH,null);
        itemView.getSwitch().setOnCheckedChangeListener(listener);
        return itemView;
    }

    /**
     * 标题+内容+switch
     * @param title 标题
     * @param detail 内容
     * @param listener 监听
     * @return
     */
    public QMUICommonListItemView CreateSwitchItemView(String title,String detail,CompoundButton.OnCheckedChangeListener listener){
        QMUICommonListItemView itemView = CreateItemView(-1,title,detail,false,QMUICommonListItemView.ACCESSORY_TYPE_SWITCH,null);
        itemView.getSwitch().setOnCheckedChangeListener(listener);
        return itemView;
    }

    /**
     * 标题+switch
     * @param title 标题
     * @param listener 监听
     * @return
     */
    public QMUICommonListItemView CreateSwitchItemView(String title,CompoundButton.OnCheckedChangeListener listener){
        QMUICommonListItemView itemView = CreateItemView(-1,title,"",false,QMUICommonListItemView.ACCESSORY_TYPE_SWITCH,null);
        itemView.getSwitch().setOnCheckedChangeListener(listener);
        return itemView;
    }

    /**
     * 标题+详情+右边自定义View
     * @param drawable 图片资源
     * @param title 标题
     * @param detail 详情
     * @param view 自定义View
     * @return
     */
    public QMUICommonListItemView CreateCustomView(int drawable,String title,String detail,View view){
        return CreateItemView(drawable,title,detail,false,-1,view);
    }

    /**
     * 标题+详情+右边自定义View
     * @param title 标题
     * @param detail 详情
     * @param view 自定义View
     * @return
     */
    public QMUICommonListItemView CreateCustomView(String title,String detail,View view){
        return CreateItemView(-1,title,detail,false,-1,view);
    }

    /**
     * 标题+右边自定义View
     * @param title 标题
     * @param view 自定义View
     * @return
     */
    public QMUICommonListItemView CreateCustomView(String title,View view){
        return CreateItemView(-1,title,"",false,-1,view);
    }

    /**
     * 标题+详情+右边自定义View
     * @param drawable 图片资源
     * @param title 标题
     * @param view 自定义View
     * @return
     */
    public QMUICommonListItemView CreateCustomView(int drawable,String title,View view){
        return CreateItemView(drawable,title,"",false,-1,view);
    }

}
