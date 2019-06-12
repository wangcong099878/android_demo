package the.one.base.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.layout.QMUIFrameLayout;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.layout.QMUIRelativeLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.List;


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

    public BaseRadiusShadowAdapter(Context context, int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        initRadioShadow(context);
    }

    public BaseRadiusShadowAdapter(Context context, @Nullable List<T> data) {
        super(data);
        initRadioShadow(context);
    }

    public BaseRadiusShadowAdapter(Context context, int layoutResId) {
        super(layoutResId);
        initRadioShadow(context);
    }

    private void initRadioShadow(Context context){
        mContext = context;
        mRadius = QMUIDisplayHelper.dp2px(context,mRadius);
        mShadow = QMUIDisplayHelper.dp2px(context,mShadow);
    }

    protected void setRadiusAndShadow(QMUILinearLayout linearLayout){
        linearLayout.setRadiusAndShadow(mRadius,
                mShadow,
                mShadowAlpha);
    }

    protected void setRadiusAndShadow(QMUIFrameLayout frameLayout){
        frameLayout.setRadiusAndShadow(mRadius,
                mShadow,
                mShadowAlpha);
    }

    protected void setRadiusAndShadow(QMUIRelativeLayout relativeLayout){
        relativeLayout.setRadiusAndShadow(mRadius,
                mShadow,
                mShadowAlpha);
    }

}
