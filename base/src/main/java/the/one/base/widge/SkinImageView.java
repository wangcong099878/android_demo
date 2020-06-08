package the.one.base.widge;

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

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.qmuiteam.qmui.skin.IQMUISkinHandlerView;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.drawable.DrawableCompat;
import the.one.base.R;

/**
 * @author The one
 * @date 2020/6/2 0002
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class SkinImageView extends AppCompatImageView implements IQMUISkinHandlerView {

    private boolean isSelect;
    private Drawable normal;
    private Drawable select;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public Drawable getNormal() {
        return normal;
    }

    public void setNormal(Drawable normal) {
        this.normal = normal;
    }

    public Drawable getSelect() {
        return select;
    }

    public void setSelect(Drawable drawable) {
        Drawable.ConstantState state = drawable.getConstantState();
        Drawable drawable1 = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        drawable1.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        DrawableCompat.setTint(drawable, getSelectColor());
        this.select = drawable;
    }

    public SkinImageView(@NonNull Context context) {
        super(context);
    }


    private int getSelectColor() {
        return QMUISkinHelper.getSkinColor(this, R.attr.qmui_skin_support_tab_selected_color);
    }

    @Override
    public void handle(@NonNull QMUISkinManager manager, int skinIndex, @NonNull Resources.Theme theme, @Nullable SimpleArrayMap<String, Integer> attrs) {
        setImageDrawable(isSelect ? select : normal);
    }

}
