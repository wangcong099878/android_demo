package the.one.base.adapter;

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

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;

import the.one.base.Interface.IContacts;
import the.one.base.R;
import the.one.base.util.ColorUtils;
import the.one.base.widge.CircleTextView;
import the.one.base.widge.TheCheckBox;

/**
 * @author The one
 * @date 2018/12/20 0020
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class LetterSearchAdapter<T extends IContacts> extends BaseQuickAdapter<T, BaseViewHolder> {

    private static final String TAG = "LetterSearchAdapter";
    private HashMap<String, Integer> letterIndexes;
    private HashMap<Integer, T> selects;
    private boolean showCheckBox = false;

    public LetterSearchAdapter() {
        super(R.layout.item_fast_search);
    }

    public void setData(List<T> datas) {
        int size = datas.size();
        letterIndexes = new HashMap<>();
        selects = new HashMap<>();
        for (int index = 0; index < size; index++) {
            //当前拼音首字母
            String currentLetter = datas.get(index).getPinYin();
            if (index == 0) letterIndexes.put(currentLetter, index);
            else {
                if (!letterIndexes.containsKey(currentLetter)) {
                    letterIndexes.put(currentLetter, index);
                }
            }
        }
        setNewData(datas);
    }

    public HashMap<Integer, T> getSelects() {
        return selects;
    }

    public boolean isShowCheckBox() {
        return showCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
        notifyDataSetChanged();
    }

    public void setSelects(int position, T data) {
        if (selects.containsKey(position))
            selects.remove(position);
        else
            selects.put(position, data);
        notifyItemChanged(position);
    }

    public void selectAll(boolean all) {
        if (all)
            for (int i = 0; i < mData.size(); i++) {
                selects.put(i, mData.get(i));
            }
        else
            selects.clear();
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer + getHeaderLayoutCount();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final T item) {
        helper.setText(R.id.tv_item_city_listview_name, item.getName())
                .addOnClickListener(R.id.ll_contact)
                .addOnLongClickListener(R.id.ll_contact);
        TheCheckBox smoothCheckBox = helper.getView(R.id.check_box);

        if (showCheckBox) {
            smoothCheckBox.setVisibility(View.VISIBLE);
            smoothCheckBox.setCheck(selects.containsKey(helper.getAdapterPosition()));
            smoothCheckBox.setEnabled(false);
        } else {
            smoothCheckBox.setVisibility(View.GONE);
        }

        CircleTextView cName = helper.getView(R.id.tv_name);
        String name = item.getName().substring(0, 1);
        cName.setText(name);
        cName.setBackColor(ColorUtils.getBackgroundColorId(name, mContext));
        CircleTextView letter = helper.getView(R.id.tv_item_city_listview_letter);
        letter.setText(item.getPinYin());
        letter.setBackColor(ColorUtils.getBackgroundColorId(item.getPinYin(), mContext));
        // 判断此开头拼音在map中的位置，如果和（当前位置-头部数量）相同则显示
        letter.setVisibility(letterIndexes.get(item.getPinYin()) == (helper.getLayoutPosition() - getHeaderLayoutCount()) ? View.VISIBLE : View.GONE);
    }

}
