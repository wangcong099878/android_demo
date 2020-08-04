package the.one.gank.ui.fragment;

import android.view.View;

import com.rxjava.rxlife.RxLife;

import java.util.ArrayList;
import java.util.List;

import rxhttp.wrapper.param.RxHttp;
import the.one.base.Interface.OnError;
import the.one.base.adapter.TabFragmentAdapter;
import the.one.base.ui.fragment.BaseFragment;
import the.one.gank.bean.CategoryBean;
import the.one.gank.constant.NetUrlConstantV2;


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
public class CategoryV2Fragment extends BaseCategoryFragment {

    private List<CategoryBean> mCategories;

    private String mCategory = NetUrlConstantV2.CATEGORY_ARTICLE;

    @Override
    protected boolean isTabFromNet() {
        return true;
    }

    @Override
    protected void requestServer() {
        showLoadingPage();
        RxHttp.get(NetUrlConstantV2.getCategory(mCategory))
                .setDomainTov2UrlIfAbsent()
                .asResponseList(CategoryBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(response -> {
                    //请求成功
                    mCategories =response.getData();
                    startInit();
                }, (OnError) error -> {
                    //请求失败
                    showErrorPage(error.getErrorMsg(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestServer();
                        }
                    });
                });
    }

    @Override
    protected void initTabAndPager() {
        pageAdapter = new TabFragmentAdapter<>(getChildFragmentManager(), fragments, isDestroyItem());
        mViewPager.setAdapter(pageAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setSwipeable(isViewPagerSwipe());
        initSegment();
        initIndicator();
        showContentPage();
    }

    @Override
    protected void addTabs() {

    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        for (CategoryBean category: mCategories){
            addTab(category.getTitle());
            fragments.add(BaseGankFragment.newInstance(GankV2Fragment.class,category.getType(), mCategory));
        }
        // 直接把妹子分类加进去
        addTab(NetUrlConstantV2.CATEGORY_GIRL);
        fragments.add(BaseGankFragment.newInstance(GankV2Fragment.class,NetUrlConstantV2.CATEGORY_GIRL,NetUrlConstantV2.CATEGORY_GIRL));
    }
}
