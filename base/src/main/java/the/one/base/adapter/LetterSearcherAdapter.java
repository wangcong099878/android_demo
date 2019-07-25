package the.one.base.adapter;

import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.section.QMUISection;

import java.util.HashMap;

import the.one.base.R;
import the.one.base.model.LetterSearchSection;
import the.one.base.util.ColorUtils;
import the.one.base.widge.CircleTextView;
import the.one.base.widge.SmoothCheckBox;


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
 * @date 2019/7/19 0019
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class LetterSearcherAdapter extends BaseSectionAdapter<LetterSearchSection,LetterSearchSection> {

    private HashMap<Integer, Integer> selects;
    private boolean showCheckBox = false;

    public LetterSearcherAdapter() {
        super(R.layout.item_letter_search_content,R.layout.item_letter_search_head );
    }

    public boolean isShowCheckBox() {
        return showCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
        notifyDataSetChanged();
    }

    public void setSelects(int position) {
        if (selects.containsKey(position))
            selects.remove(position);
        else
            selects.put(position, position);
        notifyItemChanged(position);
    }

    public HashMap<Integer,Integer> getSelects() {
        return selects;
    }

    public void selectAll(boolean all) {

            selects.clear();
        notifyDataSetChanged();
    }

    @Override
    protected void onBindSectionHeader(ViewHolder holder, int position, QMUISection<LetterSearchSection, LetterSearchSection> section) {
        CircleTextView tvSection = holder.itemView.findViewById(R.id.tv_section);
        setCircleTextViewData(tvSection,section.getHeader().getPinYin());
    }

    @Override
    protected void onBindSectionItem(ViewHolder holder, int position, QMUISection<LetterSearchSection, LetterSearchSection> section, int itemIndex) {
        CircleTextView tvName = holder.itemView.findViewById(R.id.tv_name);
        TextView tvContent = holder.itemView.findViewById(R.id.tv_content);
        SmoothCheckBox cbSelect = holder.itemView.findViewById(R.id.check_box);
        LetterSearchSection itemSection = section.getItemAt(itemIndex);

        tvContent.setText(itemSection.name);
        setCircleTextViewData(tvName,itemSection.name.substring(0,1));

        if (showCheckBox) {
            cbSelect.setVisibility(View.VISIBLE);
            cbSelect.setChecked(selects.containsKey(position));
            cbSelect.setEnabled(false);
        } else {
            cbSelect.setVisibility(View.GONE);
        }
    }

    private void setCircleTextViewData(CircleTextView textView,String content){
        textView.setText(content);
        textView.setBackColor(ColorUtils.getBackgroundColorId(content, mContext));
    }
}
