package the.one.base.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.qmuiteam.qmui.layout.IQMUILayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import the.one.base.R;
import the.one.base.util.StringUtils;


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
 * @describe 增加BaseQuickAdapter的一些功能
 * @email 625805189@qq.com
 * @remark
 */
public abstract class TheBaseQuickAdapter<T> extends BaseQuickAdapter<T, TheBaseViewHolder> implements LoadMoreModule {

    protected final String TAG = this.getClass().getSimpleName();

    private int mPriceColor;
    private int mConfigColor;
    private int mRadius;
    private int mShadow;

    protected int getRadius() {
        return 5;
    }

    protected int getShadow() {
        return 5;
    }

    protected float getShadowAlpha() {
        return 0.35f;
    }

    public TheBaseQuickAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public TheBaseQuickAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    public TheBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        initData(parent.getContext());
        return super.onCreateViewHolder(parent, viewType);
    }

    public void initData(Context context){
        mPriceColor = ContextCompat.getColor(context, R.color.price_color);
        mConfigColor = QMUIResHelper.getAttrColor(context, R.attr.config_color);
        mRadius = QMUIDisplayHelper.dp2px(context, getRadius());
        mShadow = QMUIDisplayHelper.dp2px(context, getShadow());
    }


    protected void setRadiusAndShadow(View view) {
        if (view instanceof IQMUILayout) {
            ((IQMUILayout) view).setRadiusAndShadow(mRadius,
                    mShadow,
                    getShadowAlpha());
        }
    }

    protected int getColor(int colorRes){
        return ContextCompat.getColor(getContext(),colorRes);
    }

    protected Drawable getDrawable(int drawableRes){
        return ContextCompat.getDrawable(getContext(),drawableRes);
    }

    protected void showView(View... views) {
        for (View view : views) {
            if (null != view && view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void goneView(View... views) {
        for (View view : views) {
            if (null != view && view.getVisibility() != View.GONE) {
                view.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 转换成显示价格的文本
     *
     * @param price 价格
     * @return
     */
    protected SpannableString parsePriceDefaultColorString(double price) {
        return parsePriceDefaultColorString(price, "");
    }

    /**
     * 转换成显示价格的文本
     *
     * @param price 价格
     * @param left  价格左边的文字
     * @return
     */
    protected SpannableString parsePriceDefaultColorString(double price, String left) {
        return parsePriceDefaultColorString(price, left, "");
    }

    /**
     * 转换成显示价格的文本
     *
     * @param price 价格
     * @param left  价格左边的文字
     * @param right 价格右边的文字
     * @return
     */
    protected SpannableString parsePriceDefaultColorString(double price, String left, String right) {
        return parsePriceDefaultColorString(price, left, right, 1.3f);
    }

    /**
     * 转换成显示价格的文本
     *
     * @param price    价格
     * @param left     价格左边的文字
     * @param right    价格右边的文字
     * @param fontSize 字体倍数大小
     * @return
     */
    protected SpannableString parsePriceDefaultColorString(double price, String left, String right, float fontSize) {
        return parsePriceString(price, left, right, mPriceColor, fontSize);
    }

    /**
     * 转换成显示价格的文本
     *
     * @param price    价格
     * @param left     价格左边的文字
     * @param right    价格右边的文字
     * @param fontSize 字体倍数大小
     * @return
     */
    protected SpannableString parsePriceConfigColorString(double price, String left, String right, float fontSize) {
        return parsePriceString(price, left, right, mConfigColor, fontSize);
    }

    /**
     * 转换成显示价格的文本
     *
     * @param price    价格
     * @param left     价格左边的文字
     * @param right    价格右边的文字
     * @param color    价格颜色
     * @param fontSize 字体倍数大小
     * @return
     */
    protected SpannableString parsePriceString(double price, String left, String right, int color, float fontSize) {
        return StringUtils.PriceStyleString(getContext(), price, left, right, color, fontSize);
    }

}
