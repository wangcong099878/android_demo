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

import com.qmuiteam.qmui.skin.IQMUISkinHandlerView;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIColorHelper;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SimpleArrayMap;
import the.one.base.R;

/**
 * @author The one
 * @date 2020/6/3 0003
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SkinPagerTitleView extends SimplePagerTitleView implements IQMUISkinHandlerView {

    private float mChangePercent = 0.5f;
    private boolean isSelect = false;

    public SkinPagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        isSelect = true;
//        updateTextColor();
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        isSelect = false;
//        updateTextColor();
    }

    private void updateTextColor(){
        setTextColor(isSelect?mSelectedColor:mNormalColor);
    }

    @Override
    public void handle(@NonNull QMUISkinManager manager, int skinIndex, @NonNull Resources.Theme theme, @Nullable SimpleArrayMap<String, Integer> attrs) {
        mSelectedColor = QMUISkinHelper.getSkinColor(this, R.attr.qmui_skin_support_tab_selected_color);
        mNormalColor = QMUISkinHelper.getSkinColor(this, R.attr.qmui_skin_support_tab_normal_color);
        updateTextColor();
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        setTextColor(QMUIColorHelper.computeColor(mSelectedColor,mNormalColor,leavePercent));
//        if (leavePercent >= mChangePercent) {
//            setTextColor(mNormalColor);
//        } else {
//            setTextColor(mSelectedColor);
//        }
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        setTextColor(QMUIColorHelper.computeColor(mNormalColor,mSelectedColor,enterPercent));
//        if (enterPercent >= mChangePercent) {
//            setTextColor(mSelectedColor);
//        } else {
//            setTextColor(mNormalColor);
//        }
    }

}
