package the.one.base.adapter;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.qmuiteam.qmui.layout.IQMUILayout;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

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
    private int mPrimaryColor;
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
        initData(parent);
        return super.onCreateViewHolder(parent, viewType);
    }

    public void initData(View view) {
        mPriceColor = ContextCompat.getColor(view.getContext(), R.color.price_color);
        mPrimaryColor = QMUISkinHelper.getSkinColor(view, R.attr.app_skin_primary_color);
        mRadius = QMUIDisplayHelper.dp2px(view.getContext(), getRadius());
        mShadow = QMUIDisplayHelper.dp2px(view.getContext(), getShadow());
    }


    protected void setRadiusAndShadow(View... views) {
        for (View view : views)
            if (view instanceof IQMUILayout) {
                ((IQMUILayout) view).setRadiusAndShadow(mRadius,
                        mShadow,
                        getShadowAlpha());
            }
    }

    protected int getPrimaryColor() {
        return mPrimaryColor;
    }

    protected int getPriceColor() {
        return mPriceColor;
    }

    protected int getColor(int colorRes) {
        return ContextCompat.getColor(getContext(), colorRes);
    }

    protected Drawable getDrawable(int drawableRes) {
        return ContextCompat.getDrawable(getContext(), drawableRes);
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
        return parsePriceString(price, left, right, mPrimaryColor, fontSize);
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


    protected void parseSkinSpannableString(TextView tv, SpannableString sp, String content, String targetString) {
        int start = content.indexOf(targetString);
        int end = start + targetString.length();
        sp.setSpan(new QMUITouchableSpan(tv,
                R.attr.app_skin_background_color_1,
                R.attr.app_skin_primary_color,
                R.attr.app_skin_background_color_1,
                R.attr.app_skin_primary_color) {
            @Override
            public void onSpanClick(View widget) {

            }
        }, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
    }
}
