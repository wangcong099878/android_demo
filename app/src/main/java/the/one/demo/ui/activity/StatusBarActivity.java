package the.one.demo.ui.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import the.one.base.base.activity.BaseActivity;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.R;
import the.one.demo.util.QMUIStatusBarHelper;


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
 * @date 2019/6/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class StatusBarActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;
    @BindView(R.id.top_layout)
    QMUITopBarLayout mTopLayout;


    private QMUICommonListItemView mLightMode, mDarkMode;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_status_bar;
    }

    @Override
    protected void initView(View mRootView) {
        mTopLayout.setTitle("StatusBarHelper");

        mLightMode = CreateNormalItemView("设置状态栏黑色字体与图标");
        mDarkMode = CreateNormalItemView("设置状态栏白色字体与图标");

        addToGroup(mLightMode,mDarkMode);
    }

    @Override
    public void onClick(View v) {
        if (v == mLightMode) {
            Log.e(TAG, "onClick: setStatusBarLightMode" +mLightMode.getText().toString());
            boolean success = QMUIStatusBarHelper.setStatusBarLightMode(this);
            mTopLayout.setBackgroundColor(getColorr(R.color.app_color_theme_6));
            Log.e(TAG, "onClick: " + success);
        } else {
            Log.e(TAG, "onClick: setStatusBarDarkMode" +mDarkMode.getText().toString());
            boolean success = QMUIStatusBarHelper.setStatusBarDarkMode(this);
            Log.e(TAG, "onClick: " + success);
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    /**
     * @param items
     */
    protected void addToGroup( QMUICommonListItemView... items) {
        QMUIGroupListView.Section section = QMUIGroupListView.newSection(this);
        for (QMUICommonListItemView itemView : items) {
            section.addItemView(itemView, this);
        }
        section.addTo(mGroupListView);
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

}
