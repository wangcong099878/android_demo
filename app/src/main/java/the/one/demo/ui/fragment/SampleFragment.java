package the.one.demo.ui.fragment;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.ui.activity.BaseWebExplorerActivity;
import the.one.base.ui.fragment.BaseGroupListFragment;
import the.one.demo.ui.activity.CameraSamplePermissionActivity;


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
 * @date 2019/4/24 0024
 * @describe 包含的一些内容使用示例
 * @email 625805189@qq.com
 * @remark
 */
public class SampleFragment extends BaseGroupListFragment {

    private QMUICommonListItemView PICTURE_SELECTOR, WEB_VIEW, POPUP_LAYOUT, COLLAPSING_TOP_BAR,
            PULL_EXTEND, STATUS_BAR_HELP, STRING_UTIL, PROGRESS_DIALOG, CAMERA, DATE_PICKER, LETTER_SEARCH,
            ROUND_CHECK_BOX, CITY_SELECT, CRASH,SEARCH_VIEW, POPUP_WINDOW, STICK_LAYOUT,TEST,WHITE_BLACK_THEME,IMAGE_SNAP;

    @Override
    protected boolean isExitFragment() {
        return true;
    }

    @Override
    protected void addGroupListView() {
        mTopLayout.setTitle("使用示例").getPaint().setFakeBoldText(true);
        WEB_VIEW = CreateDetailItemView("BaseWebExplorerActivity", "网页", true);
        CAMERA = CreateDetailItemView("BaseCameraPermissionActivity", "相机权限相关的界面可以继承此Activity", false, true);
        PICTURE_SELECTOR = CreateDetailItemView("BasePictureSelectorFragment", "选择图片、视频、音频", false, true);
        PULL_EXTEND = CreateDetailItemView("BasePullExtendFragment", "下拉菜单", true);
        LETTER_SEARCH = CreateDetailItemView("BaseLetterSearchFragment", "侧边快速选择", true);
        IMAGE_SNAP = CreateDetailItemView("BaseImageSnapFragment", "图片分页显示，可以实现加载更多", false,true);
        ROUND_CHECK_BOX = CreateDetailItemView("TheCheckBox", "普通的CheckBox设置leftPadding无效", false, true);

        STATUS_BAR_HELP = CreateDetailItemView("StatusBarHelper", "状态栏工具 QMUI提供", true);
        STRING_UTIL = CreateDetailItemView("StringUtils", "String的工具类", true);
        DATE_PICKER = CreateDetailItemView("DatePickerUtil", "日期选择的工具类", true);

        COLLAPSING_TOP_BAR =  CreateDetailItemView("CollapsingTopBarFragment","顶部折叠样式", true);
        PROGRESS_DIALOG = CreateDetailItemView("ProgressDialog","");
        POPUP_LAYOUT = CreateDetailItemView("PopupLayout","");

        CITY_SELECT = CreateDetailItemView("LocationSelect", "地理位置选择 城市/地址", true);

        CRASH  = CreateDetailItemView("App Crash","当程序崩溃后...",true);
        SEARCH_VIEW = CreateDetailItemView("TheSearchView","带有删除按键的SearchView",true);
        POPUP_WINDOW = CreateDetailItemView("ThePopupWindow","带有进出动画的PopupWindow",false,true);
        STICK_LAYOUT = CreateDetailItemView("StickLayout","让其任意一个直接子控件滑动时停留在顶部",false,true);

        WHITE_BLACK_THEME = CreateDetailItemView("AppMourningThemeUtil","让整个App黑白化",false,true);

        TEST = CreateNormalItemView("测试");

        showNewTips(true,IMAGE_SNAP);

        addToGroup("UI", PICTURE_SELECTOR, WEB_VIEW, PULL_EXTEND, LETTER_SEARCH, CAMERA, ROUND_CHECK_BOX,
                SEARCH_VIEW, POPUP_WINDOW,COLLAPSING_TOP_BAR,STICK_LAYOUT,IMAGE_SNAP);
        addToGroup("工具", STATUS_BAR_HELP, STRING_UTIL, DATE_PICKER, CITY_SELECT,WHITE_BLACK_THEME,CRASH);

        //addToGroup(TEST);

    }

    @Override
    public void onClick(View v) {
        if (v == PICTURE_SELECTOR) {
            startFragment(new SelectPictureFragment());
        } else if (v == POPUP_LAYOUT) {
            startFragment(new PopupLayoutFragment());
        } else if (v == WEB_VIEW) {
            startBaseWebExplorerActivity();
        } else if (v == COLLAPSING_TOP_BAR) {
            startFragment(new CollapsingTopBarFragment());
        } else if (v == PULL_EXTEND) {
            startFragment(new PullLayoutFragment());
        } else if (v == STATUS_BAR_HELP) {
            startFragment(new StatusBarHelperFragment());
        } else if (v == STRING_UTIL) {
            startFragment(new StringUtilFragment());
        } else if (v == PROGRESS_DIALOG) {
            startFragment(new ProgressDialogFragment());
        } else if (v == CAMERA) {
            startActivity(CameraSamplePermissionActivity.class);
        } else if (v == DATE_PICKER) {
            startFragment(new DatePickerFragment());
        } else if (v == LETTER_SEARCH) {
            startFragment(new LetterSearchFragment());
        } else if (v == ROUND_CHECK_BOX) {
            startFragment(new TheCheckBoxFragment());
        } else if (v == CITY_SELECT) {
            startFragment(new LocationSelectFragment());
        }else if(v == CRASH){
            startFragment(new CrashTestFragment());
        }else if(v == SEARCH_VIEW){
            startFragment(new SearchViewFragment());
        } else if (v == TEST) {
            startFragment(new TestFragment());
        }else if (v == POPUP_WINDOW) {
            startFragment(new ThePopupWindowFragment());
        }else if (v == STICK_LAYOUT) {
           startFragment(new StickLayoutFragment());
        }else if(v == IMAGE_SNAP){
            startFragment(new MzituDetailFragment());
        }else if(v == WHITE_BLACK_THEME){
            startFragment(new BlackWhiteThemeFragment());
        }
    }

    private void startBaseWebExplorerActivity(){
        String title = "每日一笑";
        String url = "http://qt.qq.com/php_cgi/news/php/varcache_mcnarticle.php?id=&doc_type=0&docid=971312304452308411&areaid=18&version=$PROTO_VERSION$";
        BaseWebExplorerActivity.newInstance(_mActivity, title, url, false);
    }
}
