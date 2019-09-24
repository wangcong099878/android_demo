package the.one.base.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
public abstract class BaseRadiusShadowAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder> {

    protected int mRadius = 7;
    protected int mShadow = 5;
    protected float mShadowAlpha = 0.75f;

    public BaseRadiusShadowAdapter( int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        initRadioShadow();
    }

    public BaseRadiusShadowAdapter( @Nullable List<T> data) {
        super(data);
        initRadioShadow();
    }

    public BaseRadiusShadowAdapter( int layoutResId) {
        super(layoutResId);
        initRadioShadow();
    }

    private void initRadioShadow(){
        mRadius = QMUIDisplayHelper.dp2px(BaseApplication.getInstance(),mRadius);
        mShadow = QMUIDisplayHelper.dp2px(BaseApplication.getInstance(),mShadow);
    }

    protected void setRadiusAndShadow(View view){
        if(view instanceof IQMUILayout){
            ((IQMUILayout) view).setRadiusAndShadow(mRadius,
                    mShadow,
                    mShadowAlpha);
        }
    }

}
