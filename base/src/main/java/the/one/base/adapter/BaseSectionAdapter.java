package the.one.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.widget.section.QMUIDefaultStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUISection;


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
 * @date 2019/2/18 0018
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseSectionAdapter<H extends QMUISection.Model<H>,
        T extends QMUISection.Model<T>> extends QMUIDefaultStickySectionAdapter<H, T> {

    private int itemLayoutId;
    private int headLayoutId;
    protected Context mContext;

    public BaseSectionAdapter(int itemLayoutId, int headLayoutId) {
        this.itemLayoutId = itemLayoutId;
        this.headLayoutId = headLayoutId;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateSectionHeaderViewHolder(@NonNull ViewGroup viewGroup) {
        mContext = viewGroup.getContext();
        return new ViewHolder(View.inflate(mContext,headLayoutId, null));
    }

    @NonNull
    @Override
    protected ViewHolder onCreateSectionItemViewHolder(@NonNull ViewGroup viewGroup) {
        return new ViewHolder(View.inflate(viewGroup.getContext(),itemLayoutId, null));
    }

}
