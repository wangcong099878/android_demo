package the.one.demo.ui.fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.ui.fragment.BaseGroupListFragment;
import the.one.base.util.StringUtils;
import the.one.demo.R;


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
 * @date 2019/6/21 0021
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class StringUtilFragment extends BaseGroupListFragment {

    private QMUICommonListItemView ForegroundColorSpan,BackgroundColorSpan,StrikeThroughSpan,
            UnderlineSpan,BOLD,ITALIC,BOLD_ITALIC,SKIN;

    private TextView tvImageSpan;

    private TextView tvSuperscript,tvSubscript;

    @Override
    protected Object getTopLayout() {
        return R.layout.custom_string_util;
    }

    @Override
    protected void addGroupListView() {
        setTitleWithBackBtn("StringUtil");

        tvSuperscript = findViewByTopView(R.id.tv_superscript);
        tvSubscript = findViewByTopView(R.id.tv_subscript);

        tvSuperscript.setText(getSpannableString(StringUtils.Type.SuperscriptSpan,"上标"));
        tvSubscript.setText(getSpannableString(StringUtils.Type.SubscriptSpan,"下标"));

        tvImageSpan = findViewByTopView(R.id.tv_name);
        tvImageSpan.setText(StringUtils.ImageSpanString(getStringg(R.string.long_fruit_name),getDrawablee(R.drawable.ic_food_status_up)));

        ForegroundColorSpan = CreateView(StringUtils.Type.ForegroundColorSpan,"前景色");
        BackgroundColorSpan = CreateView(StringUtils.Type.BackgroundColorSpan,"背景色");
        StrikeThroughSpan = CreateView(StringUtils.Type.StrikeThroughSpan,"删除线");
        UnderlineSpan = CreateView(StringUtils.Type.UnderlineSpan,"下划线");
        BOLD = CreateView(StringUtils.Type.BOLD,"粗体");
        ITALIC = CreateView(StringUtils.Type.ITALIC,"斜体");
        BOLD_ITALIC = CreateView(StringUtils.Type.BOLD_ITALIC,"粗体加斜体");

        SKIN = CreateDetailItemView("","app_skin_string_util_sample_color",false,true);
        SKIN.setText(getSkinItemContent());

        addToGroup(SKIN,ForegroundColorSpan,BackgroundColorSpan,StrikeThroughSpan,
                UnderlineSpan,BOLD,ITALIC,BOLD_ITALIC);
    }

    private SpannableString getSkinItemContent(){
        String content = "这个颜色随着主题而变化";
        String target = "颜色";
        int[] index = StringUtils.parseIndex(content,target);
        SpannableString sp = StringUtils.RelativeSizeSpannableString(content,target,1.3f);
        sp.setSpan(new QMUITouchableSpan(SKIN.getTextView(),
                R.attr.app_skin_string_util_sample_color,
                R.attr.app_skin_string_util_sample_color,
                R.attr.app_skin_background_color_1,
                R.attr.app_skin_background_color_1) {
            @Override
            public void onSpanClick(View widget) {

            }
        }, index[0], index[1], Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return sp;
    }

    private QMUICommonListItemView CreateView(StringUtils.Type type,String title){
       return  CreateNormalItemView(getSpannableString(type,title));
    }

    private SpannableString getSpannableString(StringUtils.Type type,String title){
        String content = "设置"+title;
        if(title.contains("标")){
            title = "①";
            content = content+title;
        }
        return StringUtils.SpannableString(content,title,getColorr(R.color.qmui_config_color_blue),type);
    }

    @Override
    public void onClick(View v) {

    }

}
