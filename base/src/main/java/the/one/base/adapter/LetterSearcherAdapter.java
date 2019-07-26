package the.one.base.adapter;

import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.section.QMUISection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import the.one.base.R;
import the.one.base.model.LetterSearchSection;
import the.one.base.util.ColorUtils;
import the.one.base.widge.CircleTextView;
import the.one.base.widge.TheCheckBox;


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
public class LetterSearcherAdapter<T> extends BaseSectionAdapter<LetterSearchSection, LetterSearchSection> {

    private static final String TAG = "LetterSearcherAdapter";

    private Map<LetterSearchSection,T> mDataMap;
    private HashMap<LetterSearchSection, T> selects;
    private boolean showCheckBox = false;

    public LetterSearcherAdapter() {
        super(R.layout.item_letter_search_head,R.layout.item_letter_search_content);
        selects = new HashMap<>();
    }

    public boolean isShowCheckBox() {
        return showCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
        if(!showCheckBox){
            selects.clear();
        }
        notifyDataSetChanged();
    }

    public void setSelects(int position) {
        LetterSearchSection section = getSectionItem(position);
        if (selects.containsKey(section))
            selects.remove(section);
        else{
            selects.put(section, mDataMap.get(section));
        }
        notifyItemChanged(position);
    }

    public void selectAll(boolean all) {
        selects.clear();
        if (all){
            for (LetterSearchSection section:mDataMap.keySet()){
                selects.put(section, mDataMap.get(section));
            }
        }
        notifyDataSetChanged();
    }

    public void setSelectsMap(Map<LetterSearchSection,T> dataMap) {
        mDataMap = dataMap;
    }

    public List<T> getSelects() {
        return (List<T>) new ArrayList<>(selects.values());
    }

    public T getT(int position){
        LetterSearchSection section = getSectionItem(position);
        return mDataMap.get(section);
    }

    public boolean isAllSelect(){
        return selects.size() == mDataMap.size();
    }

    @Override
    protected void onBindSectionHeader(ViewHolder holder, int position, QMUISection<LetterSearchSection, LetterSearchSection> section) {
        CircleTextView tvSection = holder.itemView.findViewById(R.id.tv_section);
        setCircleTextViewData(tvSection, section.getHeader().getFirstPinYin());
    }

    @Override
    protected void onBindSectionItem(ViewHolder holder, int position, QMUISection<LetterSearchSection, LetterSearchSection> section, int itemIndex) {
        CircleTextView tvName = holder.itemView.findViewById(R.id.tv_name);
        TextView tvContent = holder.itemView.findViewById(R.id.tv_content);
        TheCheckBox cbSelect = holder.itemView.findViewById(R.id.check_box);
        LetterSearchSection itemSection = section.getItemAt(itemIndex);

        tvContent.setText(itemSection.name);
        setCircleTextViewData(tvName, itemSection.name.substring(0, 1));

        if (showCheckBox) {
            cbSelect.setVisibility(View.VISIBLE);
            cbSelect.setCheck(selects.containsKey(itemSection));
        } else {
            cbSelect.setVisibility(View.GONE);
        }
    }

    private void setCircleTextViewData(CircleTextView textView, String content) {
        textView.setText(content);
        textView.setBackColor(ColorUtils.getBackgroundColorId(content, mContext));
    }
}
