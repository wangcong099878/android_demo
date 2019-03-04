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
import the.one.base.base.activity.BaseWebViewActivity;
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
                    parseData(homeBean.getResults());
                }
            }
        });
    }

    private void parseData(HomeBean.ResultsBean resultsBean){
        List<QMUISection<HomeHeadSection,HomeItemSection>> sections = new ArrayList<>();
        sections.add(parseSection(resultsBean.Android,"Android"));
        sections.add(parseSection(resultsBean.iOS,"IOS"));
        sections.add(parseSection(resultsBean.App,"App"));
        sections.add(parseSection(resultsBean.relax,"休息视频"));
        sections.add(parseSection(resultsBean.front,"前端"));
        sections.add(parseSection(resultsBean.extension,"拓展资源"));
        sections.add(parseSection(resultsBean.recommend,"瞎推荐"));
        sections.add(parseSection(resultsBean.welfare,"福利"));
        mAdapter.setData(sections);
    }

    private QMUISection<HomeHeadSection, HomeItemSection> parseSection(List<GankBean> gankBeans,String head){
        List<HomeItemSection> itemSections = new ArrayList<>();
        for (int i = 0; i < gankBeans.size(); i++) {
            GankBean gankBean = gankBeans.get(i);
            itemSections.add(new HomeItemSection(gankBean.getDesc(),gankBean.getImages(),gankBean.getWho(),gankBean.getUrl()));
        }
        return new QMUISection<HomeHeadSection, HomeItemSection>(new HomeHeadSection(head),itemSections);
    }

    @Override
    public void loadMore(QMUISection section, boolean loadMoreBefore) {

    }

    @Override
    public void onItemClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
        HomeItemSection itemSection = (HomeItemSection) mAdapter.getSectionItem(position);
        BaseWebViewActivity.startThisActivity(_mActivity,itemSection.content,itemSection.url);

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
