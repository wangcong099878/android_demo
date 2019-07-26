package the.one.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qmuiteam.qmui.widget.section.QMUIDefaultStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUISectionDiffCallback;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


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

    public static final int ITEM_INDEX_LIST_HEADER = -1;
    public static final int ITEM_INDEX_LIST_FOOTER = -2;
    public static final int ITEM_INDEX_SECTION_TIP_START = -3;
    public static final int ITEM_INDEX_SECTION_TIP_END = -4;

    public static final int ITEM_TYPE_LIST_HEADER = 1;
    public static final int ITEM_TYPE_LIST_FOOTER = 2;
    public static final int ITEM_TYPE_SECTION_TIP_START = 3;
    public static final int ITEM_TYPE_SECTION_TIP_END = 4;

    private int itemLayoutId;
    private int itemHeaderLayoutId;
    protected Context mContext;

    //header footer
    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;

    public BaseSectionAdapter(int itemHeaderLayoutId, int itemLayoutId) {
        this.itemHeaderLayoutId = itemHeaderLayoutId;
        this.itemLayoutId = itemLayoutId;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateCustomItemViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View view;
        mContext = viewGroup.getContext();
        if (type == ITEM_TYPE_LIST_HEADER&&null != mHeaderLayout) {
            return new ViewHolder(mHeaderLayout);
        } else if (type == ITEM_TYPE_LIST_FOOTER &&null != mFooterLayout) {
                return new ViewHolder(mFooterLayout);
        } else {
            view = new View(viewGroup.getContext());
        }
        return new ViewHolder(view);
    }

    @Override
    protected int getCustomItemViewType(int itemIndex, int position) {
        if (itemIndex == ITEM_INDEX_LIST_HEADER) {
            return ITEM_TYPE_LIST_HEADER;
        } else if (itemIndex == ITEM_INDEX_LIST_FOOTER) {
            return ITEM_TYPE_LIST_FOOTER;
        } else if (itemIndex == ITEM_INDEX_SECTION_TIP_START) {
            return ITEM_TYPE_SECTION_TIP_START;
        } else if (itemIndex == ITEM_INDEX_SECTION_TIP_END) {
            return ITEM_TYPE_SECTION_TIP_END;
        }
        return super.getCustomItemViewType(itemIndex, position);
    }

    public int addHeaderView(View header) {
        return addHeaderView(header, -1);
    }

    public int addHeaderView(View header, int index) {
        return addHeaderView(header, index, LinearLayout.VERTICAL);
    }

    public int addHeaderView(View header, final int index, int orientation) {
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayout(header.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayout.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mHeaderLayout.setOrientation(LinearLayout.HORIZONTAL);
                mHeaderLayout.setLayoutParams(new LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        final int childCount = mHeaderLayout.getChildCount();
        int mIndex = index;
        if (index < 0 || index > childCount) {
            mIndex = childCount;
        }
        mHeaderLayout.addView(header, mIndex);
        if (mHeaderLayout.getChildCount() == 1) {
            int position = 0;
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return mIndex;
    }

    /**
     * Append footer to the rear of the mFooterLayout.
     *
     * @param footer
     */
    public int addFooterView(View footer) {
        return addFooterView(footer, -1, LinearLayout.VERTICAL);
    }

    public int addFooterView(View footer, int index) {
        return addFooterView(footer, index, LinearLayout.VERTICAL);
    }

    /**
     * Add footer view to mFooterLayout and set footer view position in mFooterLayout.
     * When index = -1 or index >= child count in mFooterLayout,
     * the effect of this method is the same as that of {@link #addFooterView(View)}.
     *
     * @param footer
     * @param index  the position in mFooterLayout of this footer.
     *               When index = -1 or index >= child count in mFooterLayout,
     *               the effect of this method is the same as that of {@link #addFooterView(View)}.
     */
    public int addFooterView(View footer, int index, int orientation) {
        if (mFooterLayout == null) {
            mFooterLayout = new LinearLayout(footer.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mFooterLayout.setOrientation(LinearLayout.VERTICAL);
                mFooterLayout.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mFooterLayout.setOrientation(LinearLayout.HORIZONTAL);
                mFooterLayout.setLayoutParams(new LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        final int childCount = mFooterLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mFooterLayout.addView(footer, index);
        if (mFooterLayout.getChildCount() == 1) {
            int position = 0;
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return index;
    }


    @Override
    protected QMUISectionDiffCallback<H, T> createDiffCallback(
            List<QMUISection<H, T>> lastData,
            List<QMUISection<H, T>> currentData) {
        return new QMUISectionDiffCallback<H, T>(lastData, currentData) {

            @Override
            protected void onGenerateCustomIndexBeforeSectionList(IndexGenerationInfo generationInfo, List<QMUISection<H, T>> list) {
                generationInfo.appendWholeListCustomIndex(ITEM_INDEX_LIST_HEADER);
            }

            @Override
            protected void onGenerateCustomIndexAfterSectionList(IndexGenerationInfo generationInfo, List<QMUISection<H, T>> list) {
                generationInfo.appendWholeListCustomIndex(ITEM_INDEX_LIST_FOOTER);
            }

            @Override
            protected void onGenerateCustomIndexBeforeItemList(IndexGenerationInfo generationInfo,
                                                               QMUISection<H, T> section,
                                                               int sectionIndex) {
                if (!section.isExistBeforeDataToLoad()) {
                    generationInfo.appendCustomIndex(sectionIndex, ITEM_INDEX_SECTION_TIP_START);
                }
            }

            @Override
            protected void onGenerateCustomIndexAfterItemList(IndexGenerationInfo generationInfo,
                                                              QMUISection<H, T> section,
                                                              int sectionIndex) {
                if (!section.isExistAfterDataToLoad()) {
                    generationInfo.appendCustomIndex(sectionIndex, ITEM_INDEX_SECTION_TIP_END);
                }
            }

            @Override
            protected boolean areCustomContentsTheSame(@Nullable QMUISection<H, T> oldSection, int oldItemIndex, @Nullable QMUISection<H, T> newSection, int newItemIndex) {
                return true;
            }
        };
    }

    @NonNull
    @Override
    protected ViewHolder onCreateSectionHeaderViewHolder(@NonNull ViewGroup viewGroup) {
        mContext = viewGroup.getContext();
        return new ViewHolder(View.inflate(mContext, itemHeaderLayoutId, null));
    }

    @NonNull
    @Override
    protected ViewHolder onCreateSectionItemViewHolder(@NonNull ViewGroup viewGroup) {
        return new ViewHolder(View.inflate(viewGroup.getContext(), itemLayoutId, null));
    }

}
