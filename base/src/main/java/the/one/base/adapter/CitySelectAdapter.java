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

import org.jetbrains.annotations.NotNull;

import the.one.base.Interface.ICitySelector;
import the.one.base.R;

/**
 * @author The one
 * @date 2020/5/21 0021
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class CitySelectAdapter<T extends ICitySelector> extends TheBaseQuickAdapter<T> {

    public CitySelectAdapter() {
        super(R.layout.item_city_selector);
    }

    @Override
    protected void convert(@NotNull TheBaseViewHolder viewHolder, T t) {
        viewHolder.setText(R.id.name,t.getName());
    }
}
