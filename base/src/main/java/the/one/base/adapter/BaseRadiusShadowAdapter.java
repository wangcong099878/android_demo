package the.one.base.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.qmuiteam.qmui.layout.IQMUILayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.List;

import the.one.base.BaseApplication;


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
 * @date 2019/6/3 0003
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseRadiusShadowAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    private int mRadius ;
    private int mShadow ;

    protected int getRadius(){
        return 5;
    }

    protected int getShadow(){
        return 5;
    }

    protected float getShadowAlpha(){
        return 0.55f;
    }

    public BaseRadiusShadowAdapter( int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        initRadioShadow();
    }

    public BaseRadiusShadowAdapter( int layoutResId) {
        super(layoutResId);
        initRadioShadow();
    }

    private void initRadioShadow(){
        mRadius = QMUIDisplayHelper.dp2px(BaseApplication.getInstance(),getRadius());
        mShadow = QMUIDisplayHelper.dp2px(BaseApplication.getInstance(),getShadow());
    }

    protected void setRadiusAndShadow(View view){
        if(view instanceof IQMUILayout){
            ((IQMUILayout) view).setRadiusAndShadow(mRadius,
                    mShadow,
                    getShadowAlpha());
        }
    }

    protected void showView(View... views){
        for (View view:views){
            if(null != view && view.getVisibility() != View.VISIBLE){
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void goneView(View... views){
        for (View view:views){
            if(null != view && view.getVisibility() != View.GONE){
                view.setVisibility(View.GONE);
            }
        }
    }

}
