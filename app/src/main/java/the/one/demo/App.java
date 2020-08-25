package the.one.demo;

import android.app.Activity;

import the.one.base.BaseApplication;
import the.one.base.util.FileDirectoryUtil;
import the.one.demo.skin.SkinManager;
import the.one.demo.ui.activity.LauncherActivity;


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
 * @date 2018/12/28 0028
 * @describe 必须继承BaseApplication
 * @email 625805189@qq.com
 * @remark
 */
public class App extends BaseApplication {

    @Override
    protected Class<? extends Activity> getStartActivity() {
        return LauncherActivity.class;
    }

    @Override
    protected boolean isOpenQMUISkinManger() {
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.install(this);
//        initFilePath();
//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }
    }

    /**
     * 初始化文件目录
     */
    private void initFilePath(){
         FileDirectoryUtil
                .getBuilder()
                .setIndex("Anastasia")
                .setCache("缓存")
                .build();
    }

    /**
     * 自定义StatusLayout资源
     */
//    private void initStatusDrawable(){
//        new StatusLayout.Builder()
//                .setEmptyDrawable(R.drawable.icon_search_result_null)
//                .setFailDrawable(R.drawable.loading_view_loading_fail)
//                .setNetworkErrorDrawable(R.drawable.loading_view_network_error)
//                .build();
//    }

}
