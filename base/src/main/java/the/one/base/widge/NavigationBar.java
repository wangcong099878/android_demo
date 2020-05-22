package the.one.base.widge;

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
 * @date 2020/4/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import the.one.base.R;
import the.one.base.adapter.TabFragmentAdapter;
import the.one.base.constant.Anim;
import the.one.base.model.TabBean;
import the.one.base.util.ViewUtil;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Jue on 2018/6/1.
 */

public class NavigationBar extends QMUIWindowInsetLayout {

    private RelativeLayout AddContainerLayout;

    //Tab数量
    private int tabCount = 0;

    private LinearLayout navigationLayout;
    private RelativeLayout contentView;
    //分割线
    private View lineView;

    //红点集合
    private List<View> hintPointList = new ArrayList<>();

    //消息数量集合
    private List<TextView> msgPointList = new ArrayList<>();

    //底部Image集合
    private List<ImageView> imageViewList = new ArrayList<>();

    //底部Text集合
    private List<TextView> textViewList = new ArrayList<>();

    //底部TabLayout（除中间加号）
    private List<View> tabList = new ArrayList<>();

    //private GestureDetector detector;

    private ViewGroup addViewLayout;


    //fragment集合
    private List<TabBean> tabBeans = new ArrayList<>();
    //fragment集合
    private List<Fragment> fragmentList = new ArrayList<>();

    private FragmentManager fragmentManager;

    //Tab点击动画效果
    private Techniques anim = null;
    //ViewPager切换动画
    private boolean smoothScroll = false;
    //图标大小
    private int iconSize = 20;

    //提示红点大小
    private int hintPointSize = 6;
    //提示红点距Tab图标右侧的距离
    private int hintPointLeft = -3;
    //提示红点距图标顶部的距离
    private int hintPointTop = -3;

    private NavigationBar.OnTabClickListener onTabClickListener;
    //消息红点字体大小
    private int msgPointTextSize = 9;
    //消息红点大小
    private int msgPointSize = 18;
    //消息红点距Tab图标右侧的距离   默认为Tab图标的一半
    private int msgPointLeft = -10;
    //消息红点距图标顶部的距离  默认为Tab图标的一半
    private int msgPointTop = -10;
    //Tab文字距Tab图标的距离
    private int tabTextTop = 2;
    //Tab文字大小
    private int tabTextSize = 12;
    //未选中Tab字体颜色
    private int normalTextColor = Color.parseColor("#666666");
    //选中字体颜色
    private int selectTextColor = Color.parseColor("#333333");
    //分割线高度
    private int lineHeight = 1;
    //分割线颜色
    private int lineColor = Color.parseColor("#f7f7f7");

    private int navigationBackground = Color.parseColor("#ffffff");
    private int navigationHeight = 60;

    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_INSIDE;

    private boolean canScroll;

    private QMUIViewPager mViewPager;

    //Add
    private NavigationBar.OnAddClickListener onAddClickListener;
    private int addIconSize = 36;
    private int addLayoutHeight = navigationHeight;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_ADD = 1;
    public static final int MODE_ADD_VIEW = 2;

    private int addLayoutBottom = 10;

    //RULE_CENTER 居中只需调节addLayoutHeight 默认和navigationHeight相等 此时addLayoutBottom属性无效
    //RULE_BOTTOM addLayoutHeight属性无效、自适应、只需调节addLayoutBottom距底部的距离
    private int addLayoutRule = RULE_BOTTOM;

    public static final int RULE_CENTER = 0;
    public static final int RULE_BOTTOM = 1;

    //true  ViewPager在Navigation上面
    //false  ViewPager和Navigation重叠
    private boolean hasPadding = true;


    //1、普通的Tab 2、中间带按钮（如加号）3、
    private int mode;

    //true 点击加号切换fragment
    //false 点击加号不切换fragment进行其他操作（跳转界面等）
    private boolean addAsFragment = false;
    //自定义加号view
    private View customAddView;
    private int addTextSize;
    //加号文字未选中颜色（默认同其他tab）
    private int addNormalTextColor;
    //加号文字选中颜色（默认同其他tab）
    private int addSelectTextColor;
    //加号文字距离顶部加号的距离
    private int addTextTopMargin = 3;
    //是否和其他tab文字底部对齐
    private boolean addAlignBottom = true;
    private ImageView addImage;
    private View empty_line;


    public NavigationBar(Context context) {
        super(context);

        initViews(context, null);
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        contentView = (RelativeLayout) View.inflate(context, R.layout.navigation_container_layout, null);
        addViewLayout = contentView.findViewById(R.id.add_view_ll);
        AddContainerLayout = contentView.findViewById(R.id.add_rl);
        empty_line = contentView.findViewById(R.id.empty_line);
        navigationLayout = contentView.findViewById(R.id.navigation_ll);
        mViewPager = contentView.findViewById(R.id.mViewPager);
        lineView = contentView.findViewById(R.id.common_horizontal_line);
        lineView.setTag(-100);
        empty_line.setTag(-100);
        navigationLayout.setTag(-100);

        toDp();


        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
        parseStyle(attributes);

        addView(contentView);
    }

    private void parseStyle(TypedArray attributes) {
        if (attributes != null) {
            navigationHeight = attributes.getInt(R.styleable.NavigationBar_Easy_navigationHeight, navigationHeight);
            navigationBackground = attributes.getColor(R.styleable.NavigationBar_Easy_navigationBackground, navigationBackground);


            tabTextSize = attributes.getInt(R.styleable.NavigationBar_Easy_tabTextSize, tabTextSize);
            tabTextTop = attributes.getInt(R.styleable.NavigationBar_Easy_tabTextTop, tabTextTop);
            iconSize = (int) attributes.getInt(R.styleable.NavigationBar_Easy_tabIconSize, iconSize);
            hintPointSize = attributes.getInt(R.styleable.NavigationBar_Easy_hintPointSize, hintPointSize);
            msgPointSize = attributes.getInt(R.styleable.NavigationBar_Easy_msgPointSize, msgPointSize);
            hintPointLeft = attributes.getInt(R.styleable.NavigationBar_Easy_hintPointLeft, hintPointLeft);
            msgPointTop = attributes.getInt(R.styleable.NavigationBar_Easy_msgPointTop, -iconSize / 2);
            hintPointTop = attributes.getInt(R.styleable.NavigationBar_Easy_hintPointTop, hintPointTop);

            msgPointLeft = attributes.getInt(R.styleable.NavigationBar_Easy_msgPointLeft, -iconSize / 2);
            msgPointTextSize = attributes.getInt(R.styleable.NavigationBar_Easy_msgPointTextSize, msgPointTextSize);
            addIconSize = attributes.getInt(R.styleable.NavigationBar_Easy_addIconSize, addIconSize);
            addLayoutBottom = attributes.getInt(R.styleable.NavigationBar_Easy_addLayoutBottom, addLayoutBottom);

            //加号属性
            addSelectTextColor = attributes.getColor(R.styleable.NavigationBar_Easy_addSelectTextColor, addSelectTextColor);
            addNormalTextColor = attributes.getColor(R.styleable.NavigationBar_Easy_addNormalTextColor, addNormalTextColor);
            addTextSize = attributes.getInt(R.styleable.NavigationBar_Easy_addTextSize, addTextSize);
            addTextTopMargin = attributes.getInt(R.styleable.NavigationBar_Easy_addTextTopMargin, addTextTopMargin);
            addAlignBottom = attributes.getBoolean(R.styleable.NavigationBar_Easy_addAlignBottom, addAlignBottom);


            lineHeight = attributes.getInt(R.styleable.NavigationBar_Easy_lineHeight, lineHeight);
            lineColor = attributes.getColor(R.styleable.NavigationBar_Easy_lineColor, lineColor);


            addLayoutHeight = attributes.getInt(R.styleable.NavigationBar_Easy_addLayoutHeight, navigationHeight + lineHeight);

            normalTextColor = attributes.getColor(R.styleable.NavigationBar_Easy_tabNormalColor, normalTextColor);
            selectTextColor = attributes.getColor(R.styleable.NavigationBar_Easy_tabSelectColor, selectTextColor);
            int type = attributes.getInt(R.styleable.NavigationBar_Easy_scaleType, 0);
            if (type == 0) {
                scaleType = ImageView.ScaleType.CENTER_INSIDE;
            } else if (type == 1) {
                scaleType = ImageView.ScaleType.CENTER_CROP;
            } else if (type == 2) {
                scaleType = ImageView.ScaleType.CENTER;
            } else if (type == 3) {
                scaleType = ImageView.ScaleType.FIT_CENTER;
            } else if (type == 4) {
                scaleType = ImageView.ScaleType.FIT_END;
            } else if (type == 5) {
                scaleType = ImageView.ScaleType.FIT_START;
            } else if (type == 6) {
                scaleType = ImageView.ScaleType.FIT_XY;
            } else if (type == 7) {
                scaleType = ImageView.ScaleType.MATRIX;
            }

            addLayoutRule = attributes.getInt(R.styleable.NavigationBar_Easy_addLayoutRule, addLayoutRule);
            hasPadding = attributes.getBoolean(R.styleable.NavigationBar_Easy_hasPadding, hasPadding);

            addAsFragment = attributes.getBoolean(R.styleable.NavigationBar_Easy_addAsFragment, addAsFragment);

            attributes.recycle();
        }
    }

    //将dp、sp转换成px
    private void toDp() {
        navigationHeight = QMUIDisplayHelper.dp2px(getContext(), navigationHeight);
        iconSize = QMUIDisplayHelper.dp2px(getContext(), iconSize);
        hintPointSize = QMUIDisplayHelper.dp2px(getContext(), hintPointSize);
        hintPointTop = QMUIDisplayHelper.dp2px(getContext(), hintPointTop);
        hintPointLeft = QMUIDisplayHelper.dp2px(getContext(), hintPointLeft);

        msgPointLeft = QMUIDisplayHelper.dp2px(getContext(), msgPointLeft);
        msgPointTop = QMUIDisplayHelper.dp2px(getContext(), msgPointTop);
        msgPointSize = QMUIDisplayHelper.dp2px(getContext(), msgPointSize);
        msgPointTextSize = QMUIDisplayHelper.sp2px(getContext(), msgPointTextSize);

        tabTextTop = QMUIDisplayHelper.dp2px(getContext(), tabTextTop);
        tabTextSize = QMUIDisplayHelper.sp2px(getContext(), tabTextSize);


        //Add
        addIconSize = QMUIDisplayHelper.dp2px(getContext(), addIconSize);
        addLayoutHeight = QMUIDisplayHelper.dp2px(getContext(), addLayoutHeight);
        addLayoutBottom = QMUIDisplayHelper.dp2px(getContext(), addLayoutBottom);
        addTextSize = QMUIDisplayHelper.sp2px(getContext(), addTextSize);
        addTextTopMargin = QMUIDisplayHelper.dp2px(getContext(), addTextTopMargin);
    }


    public void build() {

        if (addLayoutHeight < navigationHeight + lineHeight)
            addLayoutHeight = navigationHeight + lineHeight;

        if (addLayoutRule == RULE_CENTER) {
            RelativeLayout.LayoutParams addLayoutParams = (RelativeLayout.LayoutParams) AddContainerLayout.getLayoutParams();
            addLayoutParams.height = (int) addLayoutHeight;
            AddContainerLayout.setLayoutParams(addLayoutParams);
        } else if (addLayoutRule == RULE_BOTTOM) {
           /* RelativeLayout.LayoutParams addLayoutParams = (RelativeLayout.LayoutParams) AddContainerLayout.getLayoutParams();
            if ((addIconSize + addIconBottom) > (navigationHeight + 1))
                addLayoutParams.height = (int) (addIconSize + addIconBottom);
            else
                addLayoutParams.height = (int) (navigationHeight + 1);
            AddContainerLayout.setLayoutParams(addLayoutParams);*/
        }


        navigationLayout.setBackgroundColor(navigationBackground);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) navigationLayout.getLayoutParams();
        params.height = (int) navigationHeight;
        navigationLayout.setLayoutParams(params);


        if (hasPadding) {
            ViewUtil.setMargins(mViewPager,0,0,0, (int) (navigationHeight + lineHeight));
        }

        RelativeLayout.LayoutParams lineParams = (RelativeLayout.LayoutParams) lineView.getLayoutParams();
        lineParams.height = (int) lineHeight;
        lineView.setBackgroundColor(lineColor);
        lineView.setLayoutParams(lineParams);

        //若没有设置中间添加的文字字体大小、颜色、则同其他Tab一样
        if (addTextSize == 0) {
            addTextSize = tabTextSize;
        }
        if (addNormalTextColor == 0) {
            addNormalTextColor = normalTextColor;
        }
        if (addSelectTextColor == 0) {
            addSelectTextColor = selectTextColor;
        }

        if (mode == MODE_NORMAL) {
            buildNavigation();
        } else if (mode == MODE_ADD) {
            buildAddNavigation();
        } else if (mode == MODE_ADD_VIEW) {
            buildAddViewNavigation();
        }
        getmViewPager().setSwipeable(canScroll);
    }

    public void buildNavigation() {
        tabCount = tabBeans.size();

        removeNavigationAllView();

        setViewPagerAdapter();

        for (int i = 0; i < tabCount; i++) {
            View itemView = View.inflate(getContext(), R.layout.navigation_tab_layout, null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.width = QMUIDisplayHelper.getScreenWidth(getContext()) / tabCount;

            itemView.setLayoutParams(params);
            itemView.setId(i);

            TextView text = itemView.findViewById(R.id.tab_text_tv);
            ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
            icon.setScaleType(scaleType);
            ViewGroup.LayoutParams iconParams = (ViewGroup.LayoutParams) icon.getLayoutParams();
            iconParams.width = (int) iconSize;
            iconParams.height = (int) iconSize;
            icon.setLayoutParams(iconParams);

            View hintPoint = itemView.findViewById(R.id.red_point);

            //提示红点
            RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
            hintPointParams.bottomMargin = (int) hintPointTop;
            hintPointParams.width = (int) hintPointSize;
            hintPointParams.height = (int) hintPointSize;
            hintPointParams.leftMargin = (int) hintPointLeft;
            hintPoint.setLayoutParams(hintPointParams);

            //消息红点
            TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
            msgPoint.setTextSize(QMUIDisplayHelper.px2sp(getContext(), msgPointTextSize));
            RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
            msgPointParams.bottomMargin = (int) msgPointTop;
            msgPointParams.width = (int) msgPointSize;
            msgPointParams.height = (int) msgPointSize;
            msgPointParams.leftMargin = (int) msgPointLeft;
            msgPoint.setLayoutParams(msgPointParams);


            hintPointList.add(hintPoint);
            msgPointList.add(msgPoint);

            imageViewList.add(icon);
            textViewList.add(text);

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTabClickListener != null) {
                        if (!onTabClickListener.onTabClickEvent(view, view.getId())) {
                            mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                    } else {
                        mViewPager.setCurrentItem(view.getId(), smoothScroll);
                    }
                }
            });

            LinearLayout.LayoutParams textParams = (LinearLayout.LayoutParams) text.getLayoutParams();
            textParams.topMargin = (int) tabTextTop;
            text.setLayoutParams(textParams);
            text.setText(tabBeans.get(i).getTitle());
            text.setTextSize(QMUIDisplayHelper.px2sp(getContext(), tabTextSize));


            tabList.add(itemView);
            navigationLayout.addView(itemView);
        }
        select(0, false);
    }

    private TabFragmentAdapter adapter;

    private void setViewPagerAdapter() {
        adapter = new TabFragmentAdapter(fragmentManager, fragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(10);
    }

    //构建中间带按钮的navigation
    public void buildAddNavigation() {

        tabCount = tabBeans.size();
        if (tabCount % 2 == 0) {
            Log.e("EasyNavigation", "MODE_ADD模式下请传入奇奇奇奇奇奇奇奇奇奇奇数数量的Tab文字集合、未选中图标集合、选中图标集合");
            return;
        }

        if (addAsFragment) {
            if (fragmentList.size() < tabCount) {
                Log.e("EasyNavigation", "MODE_ADD模式下/addAsFragment=true时Fragment的数量应和传入tab集合数量相等");
                return;
            }
        } else {
            if (fragmentList.size() < tabCount - 1) {
                Log.e("EasyNavigation", "MODE_ADD模式下/addAsFragment=false时Fragment的数量应比传入tab集合数量少一个");
                return;
            }
        }

        removeNavigationAllView();

        setViewPagerAdapter();

        for (int i = 0; i < tabCount; i++) {
            TabBean tabBean = tabBeans.get(i);
            if (i == tabCount / 2) {
                RelativeLayout addItemView = new RelativeLayout(getContext());
                RelativeLayout.LayoutParams addItemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                addItemParams.width = QMUIDisplayHelper.getScreenWidth(getContext()) / tabCount;
                addItemView.setLayoutParams(addItemParams);
                navigationLayout.addView(addItemView);

                final LinearLayout addLinear = new LinearLayout(getContext());
                addLinear.setOrientation(VERTICAL);
                addLinear.setGravity(Gravity.CENTER);
                final RelativeLayout.LayoutParams linearParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                addImage = new ImageView(getContext());
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageParams.width = (int) addIconSize;
                imageParams.height = (int) addIconSize;
                addImage.setLayoutParams(imageParams);

                TextView addText = new TextView(getContext());
                addText.setTextSize(QMUIDisplayHelper.px2sp(getContext(), addTextSize));
                LinearLayout.LayoutParams addTextParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                addTextParams.topMargin = (int) addTextTopMargin;
                if (TextUtils.isEmpty(tabBean.getTitle())) {
                    addText.setVisibility(GONE);
                } else {
                    addText.setVisibility(VISIBLE);
                }
                addText.setLayoutParams(addTextParams);
                addText.setText(tabBean.getTitle());

                if (addLayoutRule == RULE_CENTER) {
                    linearParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                } else if (addLayoutRule == RULE_BOTTOM) {
                    linearParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    linearParams.addRule(RelativeLayout.ABOVE, R.id.empty_line);
                    if (addAlignBottom) {
                        if (textViewList != null && textViewList.size() > 0) {
                            textViewList.get(0).post(new Runnable() {
                                @Override
                                public void run() {
                                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) empty_line.getLayoutParams();
                                    params.height = (int) ((navigationHeight - textViewList.get(0).getHeight() - iconSize - tabTextTop) / 2);
                                    empty_line.setLayoutParams(params);
                                    //linearParams.bottomMargin = (int) ((navigationHeight - textViewList.get(0).getHeight() - iconSize - tabTextTop) / 2);
                                }
                            });

                        }
                    } else {
                        linearParams.bottomMargin = (int) addLayoutBottom;
                    }
                }

                addImage.setId(i);
                addImage.setImageResource(tabBeans.get(i).getSelectDrawable());
                addImage.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != anim)
                            YoYo.with(anim).duration(300).playOn(view);
                        if (onTabClickListener != null) {
                            if (!onTabClickListener.onTabClickEvent(view, view.getId())) {
                                if (addAsFragment)
                                    mViewPager.setCurrentItem(view.getId(), smoothScroll);
                            }
                        } else {
                            if (addAsFragment)
                                mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                        if (onAddClickListener != null)
                            onAddClickListener.OnAddClickEvent(view);
                    }
                });

                imageViewList.add(addImage);
                textViewList.add(addText);


                addLinear.addView(addImage);
                addLinear.addView(addText);

                tabList.add(addLinear);

                AddContainerLayout.addView(addLinear, linearParams);
            } else {
                int index = i;

                View itemView = View.inflate(getContext(), R.layout.navigation_tab_layout, null);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.width = QMUIDisplayHelper.getScreenWidth(getContext()) / tabCount;

                itemView.setLayoutParams(params);
                itemView.setId(index);

                TextView text = itemView.findViewById(R.id.tab_text_tv);
                ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
                icon.setScaleType(scaleType);
                ViewGroup.LayoutParams iconParams = (ViewGroup.LayoutParams) icon.getLayoutParams();
                iconParams.width = (int) iconSize;
                iconParams.height = (int) iconSize;
                icon.setLayoutParams(iconParams);

                imageViewList.add(icon);
                textViewList.add(text);
                itemView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        final int tabPosition = view.getId();
                        if (onTabClickListener != null) {
                            if (!onTabClickListener.onTabClickEvent(view, view.getId())) {
                                if (tabPosition > tabCount / 2 && !addAsFragment) {
                                    mViewPager.setCurrentItem(tabPosition - 1, smoothScroll);
                                } else {
                                    mViewPager.setCurrentItem(view.getId(), smoothScroll);
                                }
                            }
                        } else {
                            if (tabPosition > tabCount / 2 && !addAsFragment) {
                                select(tabPosition - 1, true);
                                mViewPager.setCurrentItem(tabPosition - 1, smoothScroll);
                            } else {
                                select(tabPosition, true);
                                mViewPager.setCurrentItem(view.getId(), smoothScroll);
                            }
                        }
                    }
                });

                LinearLayout.LayoutParams textParams = (LinearLayout.LayoutParams) text.getLayoutParams();
                textParams.topMargin = (int) tabTextTop;
                text.setLayoutParams(textParams);
                text.setText(tabBean.getTitle());
                text.setTextSize(QMUIDisplayHelper.px2sp(getContext(), tabTextSize));


                View hintPoint = itemView.findViewById(R.id.red_point);

                //提示红点
                RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
                hintPointParams.bottomMargin = (int) hintPointTop;
                hintPointParams.width = (int) hintPointSize;
                hintPointParams.height = (int) hintPointSize;
                hintPointParams.leftMargin = (int) hintPointLeft;
                hintPoint.setLayoutParams(hintPointParams);

                //消息红点
                TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
                msgPoint.setTextSize(QMUIDisplayHelper.px2sp(getContext(), msgPointTextSize));
                RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
                msgPointParams.bottomMargin = (int) msgPointTop;
                msgPointParams.width = (int) msgPointSize;
                msgPointParams.height = (int) msgPointSize;
                msgPointParams.leftMargin = (int) msgPointLeft;
                msgPoint.setLayoutParams(msgPointParams);


                hintPointList.add(hintPoint);
                msgPointList.add(msgPoint);


                tabList.add(itemView);
                navigationLayout.addView(itemView);
            }
        }
        select(0, false);
    }


    private void removeNavigationAllView() {

        for (int i = 0; i < AddContainerLayout.getChildCount(); i++) {
            if (AddContainerLayout.getChildAt(i).getTag() == null) {
                AddContainerLayout.removeViewAt(i);
            }
        }

        msgPointList.clear();
        hintPointList.clear();
        imageViewList.clear();
        textViewList.clear();
        tabList.clear();

        navigationLayout.removeAllViews();
    }

    private void addTabView(final int index) {
        View itemView = View.inflate(getContext(), R.layout.navigation_tab_layout, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.width = QMUIDisplayHelper.getScreenWidth(getContext()) / tabCount;

        itemView.setLayoutParams(params);
        itemView.setId(index);

        TextView text = itemView.findViewById(R.id.tab_text_tv);
        ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
        icon.setScaleType(scaleType);
        ViewGroup.LayoutParams iconParams = (ViewGroup.LayoutParams) icon.getLayoutParams();
        iconParams.width = (int) iconSize;
        iconParams.height = (int) iconSize;
        icon.setLayoutParams(iconParams);

        imageViewList.add(icon);
        textViewList.add(text);

        if (mode == MODE_ADD) {
            itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    final int tabPosition = view.getId();
                    if (onTabClickListener != null) {
                        if (!onTabClickListener.onTabClickEvent(view, view.getId())) {

                            if (tabPosition > tabCount / 2 && !addAsFragment) {
                                mViewPager.setCurrentItem(tabPosition - 1, smoothScroll);
                            } else {
                                mViewPager.setCurrentItem(view.getId(), smoothScroll);
                            }
                        }
                    } else {
                        if (tabPosition > tabCount / 2 && !addAsFragment) {
                            mViewPager.setCurrentItem(tabPosition - 1, smoothScroll);
                        } else {
                            mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                    }
                }
            });
        } else if (mode == MODE_ADD_VIEW) {
            itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    final int tabPosition = view.getId();
                    if (onTabClickListener != null) {
                        if (!onTabClickListener.onTabClickEvent(view, index)) {

                            if (index > tabCount / 2 && !addAsFragment) {
                                mViewPager.setCurrentItem(tabPosition, smoothScroll);
                            } else {
                                mViewPager.setCurrentItem(index, smoothScroll);
                            }
                        }
                    } else {
                        if (index > tabCount / 2 && !addAsFragment) {
                            mViewPager.setCurrentItem(tabPosition, smoothScroll);
                        } else {
                            mViewPager.setCurrentItem(index, smoothScroll);
                        }
                    }
                }
            });
        }

        LinearLayout.LayoutParams textParams = (LinearLayout.LayoutParams) text.getLayoutParams();
        textParams.topMargin = (int) tabTextTop;
        text.setLayoutParams(textParams);
        text.setText(tabBeans.get(index).getTitle());
        text.setTextSize(QMUIDisplayHelper.px2sp(getContext(), tabTextSize));


        View hintPoint = itemView.findViewById(R.id.red_point);

        //提示红点
        RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
        hintPointParams.bottomMargin = (int) hintPointTop;
        hintPointParams.width = (int) hintPointSize;
        hintPointParams.height = (int) hintPointSize;
        hintPointParams.leftMargin = (int) hintPointLeft;
        hintPoint.setLayoutParams(hintPointParams);

        //消息红点
        TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
        msgPoint.setTextSize(QMUIDisplayHelper.px2sp(getContext(), msgPointTextSize));
        RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
        msgPointParams.bottomMargin = (int) msgPointTop;
        msgPointParams.width = (int) msgPointSize;
        msgPointParams.height = (int) msgPointSize;
        msgPointParams.leftMargin = (int) msgPointLeft;
        msgPoint.setLayoutParams(msgPointParams);


        hintPointList.add(hintPoint);
        msgPointList.add(msgPoint);


        tabList.add(itemView);
        navigationLayout.addView(itemView);
    }


    //自定义中间按钮
    public void buildAddViewNavigation() {

        tabCount = tabBeans.size() + 1;
        if (tabCount % 2 == 0) {
            Log.e("EasyNavigation", "MODE_ADD_VIEW模式下请传入偶偶偶偶偶偶偶偶偶偶偶偶数数量的Tab文字集合、未选中图标集合、选中图标集合");
            return;
        }
        if (addAsFragment) {
            if (fragmentList.size() < tabCount) {
                Log.e("EasyNavigation", "MODE_ADD_VIEW模式下/addAsFragment=true时Fragment的数量应比传入tab集合数量多一个");
                return;
            }
        } else {
            if (fragmentList.size() < tabCount - 1) {
                Log.e("EasyNavigation", "MODE_ADD_VIEW模式下/addAsFragment=false时,Fragment的数量应和传入tab集合数量相等");
                return;
            }
        }

        removeNavigationAllView();

        setViewPagerAdapter();

        for (int i = 0; i < tabCount; i++) {

            if (i == tabCount / 2) {
                RelativeLayout addItemView = new RelativeLayout(getContext());
                RelativeLayout.LayoutParams addItemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                addItemParams.width = QMUIDisplayHelper.getScreenWidth(getContext()) / tabCount;
                addItemView.setLayoutParams(addItemParams);
                navigationLayout.addView(addItemView);

                final RelativeLayout.LayoutParams linearParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //linearParams.width = QMUIDisplayHelper.getScreenWidth(getContext()) / tabCount;

                if (addLayoutRule == RULE_CENTER) {
                    linearParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                } else if (addLayoutRule == RULE_BOTTOM) {
                    linearParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    if (addAlignBottom) {
                        linearParams.addRule(RelativeLayout.ABOVE, R.id.empty_line);
                        if (textViewList != null && textViewList.size() > 0) {
                            textViewList.get(0).post(new Runnable() {
                                @Override
                                public void run() {
                                    linearParams.bottomMargin = (int) ((navigationHeight - textViewList.get(0).getHeight() - iconSize - tabTextTop) / 2);
                                }
                            });

                        }
                    } else {
                        linearParams.addRule(RelativeLayout.ABOVE, R.id.empty_line);
                        linearParams.bottomMargin = (int) addLayoutBottom;
                    }
                }
                customAddView.setId(i);
                customAddView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onTabClickListener != null) {
                            if (!onTabClickListener.onTabClickEvent(view, view.getId())) {
                                if (addAsFragment)
                                    mViewPager.setCurrentItem(view.getId(), smoothScroll);
                            }
                        } else {
                            if (addAsFragment)
                                mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                        if (onAddClickListener != null)
                            onAddClickListener.OnAddClickEvent(view);
                    }
                });

                AddContainerLayout.addView(customAddView, linearParams);
            } else {
                int index;

                if (i > tabCount / 2) {
                    index = i - 1;
                } else {
                    index = i;
                }

                View itemView = View.inflate(getContext(), R.layout.navigation_tab_layout, null);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.width = QMUIDisplayHelper.getScreenWidth(getContext()) / tabCount;

                itemView.setLayoutParams(params);
                itemView.setId(index);

                TextView text = itemView.findViewById(R.id.tab_text_tv);
                ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
                icon.setScaleType(scaleType);
                LayoutParams iconParams = (LayoutParams) icon.getLayoutParams();
                iconParams.width = (int) iconSize;
                iconParams.height = (int) iconSize;
                icon.setLayoutParams(iconParams);

                imageViewList.add(icon);
                textViewList.add(text);
                final int finalI = i;
                itemView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        final int tabPosition = view.getId();
                        if (onTabClickListener != null) {
                            if (!onTabClickListener.onTabClickEvent(view, finalI)) {

                                if (finalI > tabCount / 2 && !addAsFragment) {
                                    mViewPager.setCurrentItem(tabPosition, smoothScroll);
                                } else {
                                    mViewPager.setCurrentItem(finalI, smoothScroll);
                                }
                            }
                        } else {
                            if (finalI > tabCount / 2 && !addAsFragment) {
                                mViewPager.setCurrentItem(tabPosition, smoothScroll);
                            } else {
                                mViewPager.setCurrentItem(finalI, smoothScroll);
                            }
                        }
                    }
                });

                LayoutParams textParams = (LayoutParams) text.getLayoutParams();
                textParams.topMargin = (int) tabTextTop;
                text.setLayoutParams(textParams);
                text.setText(tabBeans.get(index).getTitle());
                text.setTextSize(QMUIDisplayHelper.px2sp(getContext(), tabTextSize));


                View hintPoint = itemView.findViewById(R.id.red_point);

                //提示红点
                RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
                hintPointParams.bottomMargin = (int) hintPointTop;
                hintPointParams.width = (int) hintPointSize;
                hintPointParams.height = (int) hintPointSize;
                hintPointParams.leftMargin = (int) hintPointLeft;
                hintPoint.setLayoutParams(hintPointParams);

                //消息红点
                TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
                msgPoint.setTextSize(QMUIDisplayHelper.px2sp(getContext(), msgPointTextSize));
                RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
                msgPointParams.bottomMargin = (int) msgPointTop;
                msgPointParams.width = (int) msgPointSize;
                msgPointParams.height = (int) msgPointSize;
                msgPointParams.leftMargin = (int) msgPointLeft;
                msgPoint.setLayoutParams(msgPointParams);


                hintPointList.add(hintPoint);
                msgPointList.add(msgPoint);


                tabList.add(itemView);
                navigationLayout.addView(itemView);

            }
        }

        select(0, false);

    }


    public QMUIViewPager getmViewPager() {
        return mViewPager;
    }


    public void setAddViewLayout(View addViewLayout) {
        FrameLayout.LayoutParams addParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addViewLayout.addView(addViewLayout, addParams);
    }

    public ViewGroup getAddViewLayout() {
        return addViewLayout;
    }

    /**
     * tab图标、文字变换
     *
     * @param position
     */
    private void select(int position, boolean showAnim) {
        if (mode == MODE_NORMAL) {
            for (int i = 0; i < tabCount; i++) {
                setImageDrawable(i, i == position, showAnim);
            }
        } else if (mode == MODE_ADD) {
            if (addAsFragment) {
                for (int i = 0; i < tabCount; i++) {
                    setImageDrawable(i, i == position, showAnim);
                }
            } else {
                if ((position > ((tabCount - 2) / 2))) {
                    position = position + 1;
                }
                for (int i = 0; i < tabCount; i++) {
                    setImageDrawable(i, i == position, showAnim);
                }
            }
        } else if (mode == MODE_ADD_VIEW) {
            int realPosition;
            if (addAsFragment) {
                for (int i = 0; i < tabCount; i++) {
                    if (i == tabCount / 2) {
                        continue;
                    } else if (i > tabCount / 2) {
                        realPosition = i - 1;
                    } else {
                        realPosition = i;
                    }
                    setImageDrawable(realPosition, i == position, showAnim);
                }
            } else {
                for (int i = 0; i < tabCount - 1; i++) {
                    setImageDrawable(i, i == position, showAnim);
                }
            }
        }
    }

    private void setImageDrawable(int index, boolean select, boolean showAnim) {
        TabBean tabBean = tabBeans.get(index);
        if (select && showAnim && anim != null) {
            YoYo.with(anim).duration(300).playOn(tabList.get(index));
        }
        imageViewList.get(index).setImageDrawable(ContextCompat.getDrawable(getContext(), select ? tabBean.getSelectDrawable() : tabBean.getNormalDrawable()));
        if (mode == MODE_ADD && addAsFragment) {

        }
        textViewList.get(index).setTextColor(select ? selectTextColor : normalTextColor);
    }


    public void selectTab(int position) {
        getmViewPager().setCurrentItem(position, smoothScroll);
    }


    /**
     * 设置是否显示小红点
     *
     * @param position 第几个tab
     * @param isShow   是否显示
     */
    public void setHintPoint(int position, boolean isShow) {
        if (hintPointList == null || hintPointList.size() < (position + 1))
            return;
        if (isShow) {
            hintPointList.get(position).setVisibility(VISIBLE);
        } else {
            hintPointList.get(position).setVisibility(GONE);
        }
    }


    /**
     * 设置消息数量
     *
     * @param position 第几个tab
     * @param count    显示的数量  99个以上显示99+  少于1则不显示
     */
    public void setMsgPointCount(int position, int count) {
        if (msgPointList == null || msgPointList.size() < (position + 1))
            return;
        if (count > 99) {
            msgPointList.get(position).setText("99+");
            msgPointList.get(position).setVisibility(VISIBLE);
        } else if (count < 1) {
            msgPointList.get(position).setVisibility(GONE);
        } else {
            msgPointList.get(position).setText(count + "");
            msgPointList.get(position).setVisibility(VISIBLE);
        }
    }

    /**
     * 清除数字消息
     *
     * @param position
     */
    public void clearMsgPoint(int position) {
        if (msgPointList == null || msgPointList.size() < (position + 1))
            return;
        msgPointList.get(position).setVisibility(GONE);
    }

    /**
     * 清除提示红点
     *
     * @param position
     */
    public void clearHintPoint(int position) {
        if (hintPointList == null || hintPointList.size() < (position + 1))
            return;
        hintPointList.get(position).setVisibility(GONE);
    }

    /**
     * 清空所有提示红点
     */
    public void clearAllHintPoint() {
        for (int i = 0; i < hintPointList.size(); i++) {
            hintPointList.get(i).setVisibility(GONE);
        }
    }

    /**
     * 清空所有消息红点
     */
    public void clearAllMsgPoint() {
        for (int i = 0; i < msgPointList.size(); i++) {
            msgPointList.get(i).setVisibility(GONE);
        }
    }

    public interface OnTabClickListener {
        boolean onTabClickEvent(View view, int position);
    }


    public interface OnAddClickListener {
        boolean OnAddClickEvent(View view);
    }


    public NavigationBar addLayoutHeight(int addLayoutHeight) {
        this.addLayoutHeight = QMUIDisplayHelper.dp2px(getContext(), addLayoutHeight);
        return this;
    }

    public NavigationBar scaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }


    public NavigationBar mode(int mode) {
        this.mode = mode;
        return this;
    }

    public NavigationBar hasPadding(boolean hasPadding) {
        this.hasPadding = hasPadding;
        return this;
    }

    public NavigationBar addIconSize(int addIconSize) {
        this.addIconSize = QMUIDisplayHelper.dp2px(getContext(), addIconSize);
        return this;
    }

 /*   public EasyNavigationBar onAddClickListener(EasyNavigationBar.OnAddClickListener onAddClickListener) {
        this.onAddClickListener = onAddClickListener;
        return this;
    }*/


    public NavigationBar navigationBackground(int navigationBackground) {
        this.navigationBackground = navigationBackground;
        return this;
    }

    public NavigationBar navigationHeight(int navigationHeight) {
        this.navigationHeight = QMUIDisplayHelper.dp2px(getContext(), navigationHeight);
        return this;
    }

    public NavigationBar normalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
        return this;
    }

    public NavigationBar selectTextColor(int selectTextColor) {
        this.selectTextColor = selectTextColor;
        return this;
    }

    public NavigationBar lineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public NavigationBar lineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public NavigationBar tabTextSize(int tabTextSize) {
        this.tabTextSize = QMUIDisplayHelper.sp2px(getContext(), tabTextSize);
        return this;
    }

    public NavigationBar tabTextTop(int tabTextTop) {
        this.tabTextTop = QMUIDisplayHelper.dp2px(getContext(), tabTextTop);
        return this;
    }

    public NavigationBar msgPointTextSize(int msgPointTextSize) {
        this.msgPointTextSize = QMUIDisplayHelper.sp2px(getContext(), msgPointTextSize);
        return this;
    }

    public NavigationBar msgPointSize(int msgPointSize) {
        this.msgPointSize = QMUIDisplayHelper.dp2px(getContext(), msgPointSize);
        return this;
    }

    public NavigationBar msgPointLeft(int msgPointLeft) {
        this.msgPointLeft = QMUIDisplayHelper.dp2px(getContext(), msgPointLeft);
        return this;
    }

    public NavigationBar msgPointTop(int msgPointTop) {
        this.msgPointTop = QMUIDisplayHelper.dp2px(getContext(), msgPointTop);
        return this;
    }


    public NavigationBar hintPointSize(int hintPointSize) {
        this.hintPointSize = QMUIDisplayHelper.dp2px(getContext(), hintPointSize);
        return this;
    }

    public NavigationBar hintPointLeft(int hintPointLeft) {
        this.hintPointLeft = QMUIDisplayHelper.dp2px(getContext(), hintPointLeft);
        return this;
    }

    public NavigationBar hintPointTop(int hintPointTop) {
        this.hintPointTop = QMUIDisplayHelper.dp2px(getContext(), hintPointTop);
        return this;
    }


    public NavigationBar setTabBeans(List<TabBean> tabBeans) {
        this.tabBeans = tabBeans;
        return this;
    }


    public NavigationBar fragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
        return this;
    }

    public NavigationBar fragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    public NavigationBar anim(Anim anim) {
        if (anim != null) {
            this.anim = anim.getYoyo();
        } else {
            this.anim = null;
        }
        return this;
    }

    public NavigationBar addLayoutRule(int addLayoutRule) {
        this.addLayoutRule = addLayoutRule;
        return this;
    }

    public NavigationBar canScroll(boolean canScroll) {
        this.canScroll = canScroll;
        return this;
    }

    public NavigationBar smoothScroll(boolean smoothScroll) {
        this.smoothScroll = smoothScroll;
        return this;
    }


    public NavigationBar onTabClickListener(NavigationBar.OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
        return this;
    }


    public NavigationBar iconSize(int iconSize) {
        this.iconSize = QMUIDisplayHelper.dp2px(getContext(), iconSize);
        return this;
    }

    public NavigationBar addLayoutBottom(int addLayoutBottom) {
        this.addLayoutBottom = QMUIDisplayHelper.dp2px(getContext(), addLayoutBottom);
        return this;
    }

    public NavigationBar addAsFragment(boolean addAsFragment) {
        this.addAsFragment = addAsFragment;
        return this;
    }

    public NavigationBar addCustomView(View customAddView) {
        this.customAddView = customAddView;
        return this;
    }

    public NavigationBar addTextSize(int addTextSize) {
        this.addTextSize = QMUIDisplayHelper.sp2px(getContext(), addTextSize);
        return this;
    }

    public NavigationBar addNormalTextColor(int addNormalTextColor) {
        this.addNormalTextColor = addNormalTextColor;
        return this;
    }

    public NavigationBar addSelectTextColor(int addSelectTextColor) {
        this.addSelectTextColor = addSelectTextColor;
        return this;
    }

    public NavigationBar addTextTopMargin(int addTextTopMargin) {
        this.addTextTopMargin = QMUIDisplayHelper.dp2px(getContext(), addTextTopMargin);
        return this;
    }

    public NavigationBar addAlignBottom(boolean addAlignBottom) {
        this.addAlignBottom = addAlignBottom;
        return this;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public Techniques getAnim() {
        return anim;
    }

    public boolean isSmoothScroll() {
        return smoothScroll;
    }

    public NavigationBar.OnTabClickListener getOnTabClickListener() {
        return onTabClickListener;
    }

    public int getIconSize() {
        return iconSize;
    }


    public int getHintPointSize() {
        return hintPointSize;
    }

    public int getHintPointLeft() {
        return hintPointLeft;
    }

    public int getHintPointTop() {
        return hintPointTop;
    }


    public int getMsgPointTextSize() {
        return msgPointTextSize;
    }

    public int getMsgPointSize() {
        return msgPointSize;
    }

    public int getMsgPointLeft() {
        return msgPointLeft;
    }

    public int getMsgPointTop() {
        return msgPointTop;
    }

    public int getTabTextTop() {
        return tabTextTop;
    }

    public int getTabTextSize() {
        return tabTextSize;
    }

    public int getNormalTextColor() {
        return normalTextColor;
    }

    public int getSelectTextColor() {
        return selectTextColor;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public int getLineColor() {
        return lineColor;
    }

    public NavigationBar.OnAddClickListener getOnAddClickListener() {
        return onAddClickListener;
    }

    public NavigationBar setOnAddClickListener(NavigationBar.OnAddClickListener listener) {
        onAddClickListener = listener;
        return this;
    }

    public int getAddIconSize() {
        return addIconSize;
    }

    public int getAddLayoutHeight() {
        return addLayoutHeight;
    }

    public int getNavigationBackground() {
        return navigationBackground;
    }

    public int getNavigationHeight() {
        return navigationHeight;
    }

    public boolean isCanScroll() {
        return canScroll;
    }

//    public ViewPagerAdapter getAdapter() {
//        return adapter;
//    }


    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public int getMode() {
        return mode;
    }

    public LinearLayout getNavigationLayout() {
        return navigationLayout;
    }

    public RelativeLayout getContentView() {
        return contentView;
    }

    public View getLineView() {
        return lineView;
    }

    public ViewGroup getAddLayout() {
        return addViewLayout;
    }

    public int getAddLayoutBottom() {
        return addLayoutBottom;
    }

    public int getAddLayoutRule() {
        return addLayoutRule;
    }

    public RelativeLayout getAddContainerLayout() {
        return AddContainerLayout;
    }

    public boolean isHasPadding() {
        return hasPadding;
    }

    public boolean isAddAsFragment() {
        return addAsFragment;
    }

    public View getCustomAddView() {
        return customAddView;
    }

    public int getAddTextSize() {
        return addTextSize;
    }

    public int getAddNormalTextColor() {
        return addNormalTextColor;
    }

    public int getAddSelectTextColor() {
        return addSelectTextColor;
    }

    public int getAddTextTopMargin() {
        return addTextTopMargin;
    }

    public boolean isAddAlignBottom() {
        return addAlignBottom;
    }

    public ImageView getAddImage() {
        return addImage;
    }

    // --addIconBottom
}