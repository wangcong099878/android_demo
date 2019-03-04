package the.one.demo.fragment;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import the.one.base.base.fragment.BaseSectionLayoutFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.util.GlideUtil;
import the.one.base.util.JsonUtil;
import the.one.demo.Constant;
import the.one.demo.R;
import the.one.demo.adapter.HomeAdapter;
import the.one.demo.model.GankBean;
import the.one.demo.model.HomeBean;
import the.one.demo.model.HomeHeadSection;
import the.one.demo.model.HomeItemSection;
import the.one.demo.util.HomeUtil;


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
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class HomeFragment extends BaseSectionLayoutFragment {

    @BindView(R.id.topbar)
    QMUITopBar topbar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout collapsingTopbarLayout;


    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mSectionLayout = rootView.findViewById(the.one.base.R.id.section_layout);
        collapsingTopbarLayout.setTitle("今日最新干货");
        collapsingTopbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(_mActivity,R.color.we_chat_black));
        initStickyLayout();
        initData();
    }

    @Override
    protected QMUIStickySectionAdapter createAdapter() {
        return new HomeAdapter();
    }

    @Override
    protected void requestServer() {
        OkHttpUtils.get().url(Constant.GANK_BASE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                GlideUtil.load(_mActivity,new HomeUtil().getHomeImage(response),ivHead);
            }
        });
        OkHttpUtils.get().url(Constant.TODAY)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                HomeBean homeBean = JsonUtil.fromJson(response,HomeBean.class);
                if(!homeBean.isError()){
                    parseData(homeBean);
                }
            }
        });
    }

    private void parseData(HomeBean homeBean){
        List<String> category = homeBean.getCategory();
        List<QMUISection<HomeHeadSection,HomeItemSection>> sections = new ArrayList<>();
        ArrayList<HomeHeadSection> headSections = new ArrayList<>();
        for (int i = 0; i < category.size(); i++) {
            HomeHeadSection headSection = new HomeHeadSection(category.get(i));
            headSections.add(headSection);
        }
        List<HomeItemSection> itemSections = new ArrayList<>();
        List<GankBean> android = homeBean.getResults().Android;
        for (int i = 0; i < android.size(); i++) {
            GankBean gankBean = android.get(i);
            itemSections.add(new HomeItemSection(gankBean.getDesc(),gankBean.getImages(),gankBean.getWho()));
        }
        sections.add(new QMUISection<HomeHeadSection, HomeItemSection>(headSections.get(0),itemSections));
        mAdapter.setData(sections);
    }

    @Override
    public void loadMore(QMUISection section, boolean loadMoreBefore) {

    }

    @Override
    public void onItemClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {

    }

    @Override
    public boolean onItemLongClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
        return false;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
