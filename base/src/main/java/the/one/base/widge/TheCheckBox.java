package the.one.base.widge;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.qmuiteam.qmui.skin.IQMUISkinHandlerView;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import the.one.base.R;
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
 * @date 2019/7/24 0024
 * @describe 由于CheckBox不能设置Padding，所以用这个代替，用法和CheckBox一样
 * @email 625805189@qq.com
 * @remark
 */

public class TheCheckBox extends AppCompatTextView implements View.OnClickListener, IQMUISkinHandlerView {

    private Drawable isCheck, unCheck, current;
    private OnCheckChangedListener listener;
    private int defaultPadding = 10;

    public TheCheckBox(Context context) {
        super(context, null);
    }

    public TheCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TheCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnCheckChangedListener(OnCheckChangedListener listener) {
        this.listener = listener;
    }

    public void setIsCheckDrawable(int isCheck) {
        this.isCheck = getDrawable(isCheck);
    }

    public void setUnCheckDrawable(int unCheck) {
        this.unCheck = getDrawable(unCheck);
    }

    public Drawable getCurrent() {
        return current;
    }

    public void setCurrent(Drawable current) {
        this.current = current;
    }

    private void init(Context context) {
        isCheck = getDrawable(R.drawable.qmui_icon_checkbox_checked_red);
        unCheck = getDrawable(R.drawable.qmui_icon_checkbox_normal);
//        parseCheckDrawable();
        setImageDrawable(unCheck);
        setBackground(QMUIResHelper.getAttrDrawable(context, R.attr.selectableItemBackground));
        setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        setOnClickListener(this);
        if (!TextUtils.isEmpty(getText()))
            setTextPadding(defaultPadding);
    }

    public void setTextPadding(int padding) {
        setCompoundDrawablePadding(QMUIDisplayHelper.dp2px(getContext(), padding));
    }

    public void setCheck(boolean check) {
        setImageDrawable(check ? isCheck : unCheck);
    }

    public boolean isCheck() {
        return current == isCheck;
    }

    private void setImageDrawable(Drawable drawable) {
        setCurrent(drawable);
        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    private Drawable getDrawable(int drawableId) {
        return ContextCompat.getDrawable(getContext(), drawableId);
    }

    public void setTextWithPadding(CharSequence text) {
        setText(text);
        setTextPadding(defaultPadding);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    @Override
    public void onClick(View v) {
        setCheck(!isCheck());
        if (null != listener) {
            listener.onCheckChanged(isCheck());
        }
    }

    @Override
    public void handle(@NonNull QMUISkinManager manager, int skinIndex, @NonNull Resources.Theme theme, @Nullable SimpleArrayMap<String, Integer> attrs) {
//        parseCheckDrawable();
//        setCheck(isCheck());
    }

    private void parseCheckDrawable() {
        Drawable.ConstantState state = isCheck.getConstantState();
        isCheck = DrawableCompat.wrap(state == null ? isCheck : state.newDrawable()).mutate();
        isCheck.setBounds(0, 0, isCheck.getIntrinsicWidth(), isCheck.getIntrinsicHeight());
        DrawableCompat.setTint(isCheck, getSelectColor());
    }

    private int getSelectColor() {
        return QMUISkinHelper.getSkinColor(this, R.attr.app_skin_primary_color);
    }

    public interface OnCheckChangedListener {
        void onCheckChanged(boolean check);
    }
}
