package the.one.base.ui.fragment;

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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.R;
import the.one.base.adapter.LetterSearcherAdapter;
import the.one.base.model.LetterSearchSection;
import the.one.base.widge.CircleTextView;
import the.one.base.widge.SideLetterBar;

/**
 * @author The one
 * @date 2019/1/16 0016
 * @describe 侧边快速查询 - 联系人 城市选择等
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseLetterSearchFragment<T extends LetterSearchSection> extends BaseFragment
        implements LetterSearcherAdapter.onLetterSearchItemClickListener {

    protected QMUIStickySectionLayout mSectionLayout;
    protected CircleTextView tvLetterOverlay;
    protected SideLetterBar sideLetterBar;

    public LetterSearcherAdapter<T> mAdapter;
    protected HashMap<LetterSearchSection, T> mDataMap = new HashMap<>();
    protected HashMap<LetterSearchSection, T> mSelectMap = new HashMap<>();
    protected TextView tvLeft, tvRight;

    protected abstract String getTitleString();

    protected abstract void onItemClick(T t);

    /**
     * 多选时点击确认得到的数据
     *
     * @param selects
     */
    protected abstract void onConfirmSelect(List<T> selects);

    /**
     * 是否需要多选
     *
     * @return
     */
    protected boolean isNeedMultiChoose() {
        return true;
    }

    @Override
    protected boolean isNeedAround() {
        return true;
    }

    @Override
    protected Object getBottomLayout() {
        return R.layout.custom_address_book_bottom_layout;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_letter_search;
    }

    @Override
    protected void initView(View rootView) {
        setTitleWithBackBtn(getTitleString());
        mSectionLayout = rootView.findViewById(R.id.section_layout);
        mSectionLayout.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        tvLetterOverlay = rootView.findViewById(R.id.tv_letter_overlay);
        sideLetterBar = rootView.findViewById(R.id.side_letter_bar);
        mAdapter = new LetterSearcherAdapter();
        mAdapter.setOnLetterSearchItemClickListener(this);
        mSectionLayout.setAdapter(mAdapter, true);
        sideLetterBar.setOverlay(tvLetterOverlay);
        mSectionLayout.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mSectionLayout.getRecyclerView().setOverScrollMode(View.OVER_SCROLL_NEVER);
        mSectionLayout.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                sideLetterBar.setVisibility(newState == 0 ? View.VISIBLE : View.INVISIBLE);
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        sideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                if (letter.equals(SideLetterBar.b[0])) {
                    mSectionLayout.scrollToPosition(0, true, true);
                } else if (mBarIndex.containsKey(letter)) {
                    mAdapter.scrollToSectionHeader(mBarIndex.get(letter), true);
                }
            }
        });

        tvLeft = findViewByBottomView(R.id.tv_left);
        tvRight = findViewByBottomView(R.id.tv_right);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirmSelect(mAdapter.getSelects());
            }
        });
        goneView(tvLeft, flBottomLayout, sideLetterBar, flTopLayout);
    }

    protected void notifyData(List<T> datas, String emptyTitle, String btnString, View.OnClickListener listener) {
        notifyData(datas, null, emptyTitle, btnString, listener);
    }

    protected void notifyData(List<T> datas, List<T> selects, String emptyTitle, String btnString, View.OnClickListener listener) {
        if (null == datas || datas.size() == 0) {
            showEmptyPage(emptyTitle, btnString, listener);
        } else {
            // 如果数据比较多，下面这个排序会比较耗时
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Collections.sort(datas, new NameComparator());
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setData(parseSectionList(datas, selects));
                            mAdapter.setSelectsMap(mDataMap);
                            setSelect(selects);
                            showView(sideLetterBar, flTopLayout);
                            showContentPage();
                        }
                    });
                }
            }).start();
        }
    }

    private void setSelect(List<T> list) {
        if (null == list) return;
        startSelect();
        mAdapter.setSelects(mSelectMap);
        updateSelectSum();
    }

    private Map<String, QMUISection<LetterSearchSection, LetterSearchSection>> mBarIndex = new HashMap<>();

    private List<QMUISection<LetterSearchSection, LetterSearchSection>> parseSectionList(List<T> datas, List<T> selects) {
        List<QMUISection<LetterSearchSection, LetterSearchSection>> sections = new ArrayList<>();
        String head = datas.get(0).getFirstPinYin();
        List<T> items = new ArrayList<>();
        int size = datas.size();
        for (int i = 0; i < size; i++) {
            T data = datas.get(i);
            if (!data.getFirstPinYin().equals(head) && items.size() > 0) {
                QMUISection<LetterSearchSection, LetterSearchSection> section = parseSection(items, selects, head);
                sections.add(section);
                mBarIndex.put(head, section);
                items.clear();
                head = data.getFirstPinYin();
            }
            items.add(data);
            // 如果最后一个head还有内容就不会再走上面的判断
            if (i == size - 1 && items.size() > 1) {
                sections.add(parseSection(items, selects, head));
                items.clear();
                mBarIndex.put(head, sections.get(sections.size() - 1));
            }
        }
        for (Map.Entry<String, QMUISection<LetterSearchSection, LetterSearchSection>> entry : mBarIndex.entrySet()) {
            String mapKey = entry.getKey();
            QMUISection<LetterSearchSection, LetterSearchSection> mapValue = entry.getValue();
        }
        return sections;
    }

    private QMUISection<LetterSearchSection, LetterSearchSection> parseSection(List<T> datas, List<T> selects, String head) {
        List<LetterSearchSection> itemSections = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            T data = datas.get(i);
            LetterSearchSection section = new LetterSearchSection(data.getName());
            section.position = i;
            itemSections.add(section);
            mDataMap.put(section, data);
            if (null != selects)
                for (T t : selects) {
                    if (t.name.equals(data.name)) {
                        mSelectMap.put(section, data);
                        break;
                    }
                }
        }
        return new QMUISection<LetterSearchSection, LetterSearchSection>(new LetterSearchSection(head), itemSections);
    }

    /**
     * a-z排序
     */
    private class NameComparator implements Comparator<T> {
        @Override
        public int compare(T lhs, T rhs) {
            String a = lhs.getPinYin();
            String b = rhs.getPinYin();
            return a.compareTo(b);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser && null != tvLetterOverlay && tvLetterOverlay.getVisibility() == View.VISIBLE) {
            goneView(tvLetterOverlay);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }


    @Override
    public void onSearchItemClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
        if (mAdapter.isShowCheckBox()) {
            mAdapter.setSelects(position);
            updateSelectSum();
        } else {
            onItemClick(mAdapter.getT(position));
        }
    }

    @Override
    public boolean onSearchItemLongClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
        if (isNeedMultiChoose() && !mAdapter.isShowCheckBox()) {
            startSelect();
            mAdapter.setSelects(position);
            updateSelectSum();
        }
        return isNeedMultiChoose() && !mAdapter.isShowCheckBox();
    }

    protected void startSelect() {
        initSelectTopBar();
        mAdapter.setShowCheckBox(true);
    }

    protected void exitSelect() {
        mAdapter.setShowCheckBox(false);
        mTopLayout.removeAllLeftViews();
        mTopLayout.removeAllRightViews();
        goneView(flBottomLayout);
        setTitleWithBackBtn(getTitleString());
    }

    protected void updateSelectSum() {
        int size = mAdapter.getSelects().size();
        setTitle(size);
        tvRight.setEnabled(size > 0);
        topRightText.setText(mAdapter.isAllSelect() ? "全不选" : "全选");
    }

    protected Button topRightText;

    private void initSelectTopBar() {
        showView(flBottomLayout);
        mTopLayout.removeAllLeftViews();
        mTopLayout.addLeftTextButton("取消", R.id.topbar_left_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitSelect();
            }
        });
        topRightText = mTopLayout.addRightTextButton("全选", R.id.topbar_right_text);
        topRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdapter.isAllSelect()) {
                    // 全选状态，点击后变成全不选
                    topRightText.setText("全选");
                    mAdapter.selectAll(false);
                    tvRight.setEnabled(false);
                } else {
                    // 全选状态，点击后变成全不选
                    topRightText.setText("全不选");
                    mAdapter.selectAll(true);
                    tvRight.setEnabled(true);
                }
                setTitle(mAdapter.getSelects().size());
            }
        });
    }

    protected void setTitle(int size) {
        mTopLayout.setTitle(size == 0 ? getTitleString() : "已选择" + size + "项");
    }

    @Override
    protected void onBackPressed() {
        if (mAdapter.isShowCheckBox()) {
            exitSelect();
            return;
        }
        super.onBackPressed();
    }
}
