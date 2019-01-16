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

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;

import the.one.base.Interface.IContacts;
import the.one.base.R;

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

    public LetterSearchAdapter() {
        super(R.layout.item_fast_search);

    }

    public void setData(List<T> datas) {
        int size = datas.size();
        letterIndexes = new HashMap<>();
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
        Log.e(TAG, "LetterSearchAdapter: " + letterIndexes.toString());
        setNewData(datas);
    }


    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer+getHeaderLayoutCount();
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        helper.setText(R.id.tv_item_city_listview_name, item.getName())
                .addOnClickListener(R.id.tv_item_city_listview_name)
                .addOnLongClickListener(R.id.tv_item_city_listview_name);
        TextView letter = helper.getView(R.id.tv_item_city_listview_letter);
        letter.setText(item.getPinYin());
        // 判断此开头拼音在map中的位置，如果和（当前位置-头部数量）相同则显示
        letter.setVisibility(letterIndexes.get(item.getPinYin()) == (helper.getLayoutPosition()-getHeaderLayoutCount()) ? View.VISIBLE : View.GONE);
    }

}
