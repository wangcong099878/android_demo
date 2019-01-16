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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import the.one.base.Interface.IContacts;
import the.one.base.R;
import the.one.base.adapter.LetterSearchAdapter;
import the.one.base.widge.SideLetterBar;

/**
 * @author The one
 * @date 2019/1/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseLetterSearchFragment<T extends IContacts> extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemChildLongClickListener{

    protected FrameLayout topLayout;
    protected RecyclerView recyclerView;
    protected TextView tvLetterOverlay;
    protected SideLetterBar sideLetterBar;

    public LetterSearchAdapter adapter;

    public List<T> datas;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_letter_search;
    }

    @Override
    protected void initView(View rootView) {
        topLayout = rootView.findViewById(R.id.top_layout);
        recyclerView = rootView.findViewById(R.id.recycle_view);
        tvLetterOverlay = rootView.findViewById(R.id.tv_letter_overlay);
        sideLetterBar = rootView.findViewById(R.id.side_letter_bar);
        adapter = new LetterSearchAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);
        sideLetterBar.setOverlay(tvLetterOverlay);
        sideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                if(letter.equals(SideLetterBar.b[0])){
                    recyclerView.scrollToPosition(0);
                }else{
                    int selection = adapter.getLetterPosition(letter);
                    if (selection != -1)
                        recyclerView.scrollToPosition(selection);
                }

            }
        });
    }

    public void notifyData(List<T> strings, String emptyTitle, String btnString, View.OnClickListener listener) {
        if (null == strings || strings.size() == 0) {
            showEmptyPage(emptyTitle, btnString, listener);
        } else {
            datas = strings;
            Collections.sort(datas, new NameComparator());
            adapter.setData(datas);
            showContentPage();
        }
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

}
