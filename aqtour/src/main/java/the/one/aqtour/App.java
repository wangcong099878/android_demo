package the.one.aqtour;

import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.zhy.http.okhttp.OkHttpUtils;

import org.litepal.LitePal;

import java.io.File;

import rxhttp.HttpSender;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.cahce.CacheMode;
import the.one.aqtour.ui.activity.Launcher;
import the.one.base.BaseApplication;
import the.one.base.BuildConfig;
import the.one.base.util.FileDirectoryUtil;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;


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
 * @date 2019/4/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class App extends BaseApplication {

    @Override
    protected Class getStartActivity() {
        return Launcher.class;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initGSYVideoPlayer();
        // 初始化数据库
        LitePal.initialize(getInstance());
    }

    @Override
    protected CacheMode getRxHttpCacheMode() {
        return CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK;
    }

    private void initGSYVideoPlayer(){
        PlayerFactory.setPlayManager(Exo2PlayerManager.class);//EXO模式
//        CacheFactory.setCacheManager(ExoPlayerCacheManager.class);//exo缓存模式，支持m3u8，只支持exo
    }

}
