package the.one.base.widge;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;

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

public class TheCheckBox extends AppCompatTextView implements View.OnClickListener {

    private Drawable isCheck, unCheck,current;
    private OnCheckChangedListener  listener;

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

    public void setIsCheckDrawable(Drawable isCheck) {
        this.isCheck = isCheck;
    }

    public void setUnCheckDrawable(Drawable unCheck) {
        this.unCheck = unCheck;
    }

    private void init(Context context){
        isCheck = ContextCompat.getDrawable(context, R.drawable.ic_checkbox_true);
        unCheck = ContextCompat.getDrawable(context, R.drawable.ic_checkbox_false);
        setImageDrawable(unCheck);
        setBackground(QMUIResHelper.getAttrDrawable(context,R.attr.selectableItemBackground));
        setGravity(Gravity.CENTER);
        setOnClickListener(this);
    }

    public void setTextPadding(int padding){
        setCompoundDrawablePadding(QMUIDisplayHelper.dp2px(getContext(),padding));
    }

    public void setCheck(boolean check){
        setImageDrawable(check?isCheck:unCheck);
    }

    public boolean isCheck(){
        return current == isCheck;
    }

    private void setImageDrawable(Drawable drawable){
        current  = drawable;
        setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
    }

    @Override
    public void onClick(View v) {
        setCheck(!isCheck());
        if(null !=listener){
            listener.onCheckChanged(isCheck());
        }
    }

    public interface OnCheckChangedListener{
        void onCheckChanged(boolean check);
    }
}
