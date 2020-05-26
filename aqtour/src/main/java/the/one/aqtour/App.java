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

    /**
     * OkHttpUtils适配https
     * RxHttp设置缓存路径
     */
    protected void initHttp(){
        HttpSender.init(getDefaultOkHttpClient(), BuildConfig.DEBUG);
        //设置缓存目录为：Android/data/{app包名目录}/cache/RxHttpCache
        File cacheDir = new File(FileDirectoryUtil.getCachePath(), "RxHttpCache");
        //设置最大缓存为10M，缓存有效时长为一个小时，这里全局不做缓存处理，某些需要缓存的请求单独设置
        RxHttpPlugins.setCache(cacheDir, 10 * 1024 * 1024, CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK,  60 * 1000);
        OkHttpUtils.initClient(getDefaultOkHttpClient());
    }

    private void initGSYVideoPlayer(){
        PlayerFactory.setPlayManager(Exo2PlayerManager.class);//EXO模式
//        CacheFactory.setCacheManager(ExoPlayerCacheManager.class);//exo缓存模式，支持m3u8，只支持exo
    }

}
