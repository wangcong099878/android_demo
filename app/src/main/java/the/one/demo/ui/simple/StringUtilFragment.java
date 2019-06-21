package the.one.demo.ui.simple;

import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.base.fragment.BaseGroupListFragment;
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
            UnderlineSpan,SuperscriptSpan,SubscriptSpan,BOLD,ITALIC,BOLD_ITALIC;

    private TextView tvImageSpan;

    @Override
    protected int getTopLayout() {
        return R.layout.custom_string_util;
    }

    @Override
    protected void addGroupListView() {
        initFragmentBack("StringUtil");

        ForegroundColorSpan = CreateView(StringUtils.Type.ForegroundColorSpan,"前景色");
        BackgroundColorSpan = CreateView(StringUtils.Type.BackgroundColorSpan,"背景色");
        StrikeThroughSpan = CreateView(StringUtils.Type.StrikeThroughSpan,"删除线");
        UnderlineSpan = CreateView(StringUtils.Type.UnderlineSpan,"下划线");
        SuperscriptSpan = CreateView(StringUtils.Type.SuperscriptSpan,"上标");
        SubscriptSpan = CreateView(StringUtils.Type.SubscriptSpan,"下标");
        BOLD = CreateView(StringUtils.Type.BOLD,"粗体");
        ITALIC = CreateView(StringUtils.Type.ITALIC,"斜体");
        BOLD_ITALIC = CreateView(StringUtils.Type.BOLD_ITALIC,"粗体加斜体");

        addToGroup(ForegroundColorSpan,BackgroundColorSpan,StrikeThroughSpan,
                UnderlineSpan,SuperscriptSpan,SubscriptSpan,BOLD,ITALIC,BOLD_ITALIC);
        initImageSpanView();
    }

    private QMUICommonListItemView CreateView(StringUtils.Type type,String title){
        String content = "设置"+title;
       return  CreateNormalItemView(StringUtils.SpannableString(type,content,title,getColorr(R.color.qmui_config_color_blue)));
    }

    private void initImageSpanView(){
        tvImageSpan = flTopLayout.findViewById(R.id.tv_name);
        tvImageSpan.setText(StringUtils.ImageSpanString(getStringg(R.string.long_fruit_name),getDrawablee(R.drawable.ic_food_status_up)));
    }

    @Override
    public void onClick(View v) {

    }
}
