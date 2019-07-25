package the.one.base.base.fragment;

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

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import the.one.base.R;
import the.one.base.adapter.LetterSearcherAdapter;
import the.one.base.model.LetterSearchSection;
import the.one.base.widge.SideLetterBar;

/**
 * @author The one
 * @date 2019/1/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseLetterSearchFragment<T extends LetterSearchSection> extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemChildLongClickListener{

    protected RecyclerView recyclerView;
    protected QMUIStickySectionLayout mSectionLayout;
    protected TextView tvLetterOverlay;
    protected SideLetterBar sideLetterBar;

//    protected QMUIStickySectionAdapter<LetterSearchSection, LetterSearchSection, QMUIStickySectionAdapter.ViewHolder> mAdapter;
    public LetterSearcherAdapter mAdapter;
    public List<T> datas;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_letter_search;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recycle_view);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
//        mAdapter.setOnItemChildClickListener(this);
//        mAdapter.setOnItemChildLongClickListener(this);
        sideLetterBar.setOverlay(tvLetterOverlay);
        sideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                if(letter.equals(SideLetterBar.b[0])){
                    recyclerView.scrollToPosition(0);
                }else{
//                    int selection = mAdapter.getLetterPosition(letter);
//                    if (selection != -1)
//                        recyclerView.scrollToPosition(selection);
                }

            }
        });
        if(mTopLayout.getVisibility()!=View.VISIBLE){
            setMargins(mStatusLayout,0, 0,0,0);
        }
        goneView(sideLetterBar);
    }

    public void notifyData(List<T> datas, String emptyTitle, String btnString, View.OnClickListener listener) {
        if (null == datas || datas.size() == 0) {
            showEmptyPage(emptyTitle, btnString, listener);
        } else {
            Collections.sort(datas, new NameComparator());
            this.datas = datas;
            mAdapter.setData(parseSectionList(datas));
            showView(sideLetterBar);
            showContentPage();
        }
    }

    private List<QMUISection<LetterSearchSection, LetterSearchSection>> parseSectionList(List<T> datas){
        List<QMUISection<LetterSearchSection, LetterSearchSection>> sections = new ArrayList<>();
        String head = "";
        List<T> items = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            T data = datas.get(i);
            if(!data.name.equals(head)&&items.size()>0){
                sections.add(parseSection(items,head));
                items.clear();
                head = data.name;
            }
            items.add(data);
        }
        return sections;
    }

    private QMUISection<LetterSearchSection, LetterSearchSection> parseSection(List<T> datas, String head) {
        List<LetterSearchSection> itemSections = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            T data = datas.get(i);
            itemSections.add(new LetterSearchSection(data.getName()));
        }
        return new QMUISection<LetterSearchSection, LetterSearchSection>(new LetterSearchSection(head), itemSections);
    }

    public List<T> getDatas() {
        return datas;
    }

    /**
     * a-z排序
     */
    private class NameComparator implements Comparator<T> {
        @Override
        public int compare(T lhs, T rhs) {
            String a = lhs.getPinYin().substring(0, 1);
            String b = rhs.getPinYin().substring(0, 1);
            return a.compareTo(b);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(!isVisibleToUser&&null!=tvLetterOverlay&&tvLetterOverlay.getVisibility() == View.VISIBLE){
            goneView(tvLetterOverlay);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }


}
