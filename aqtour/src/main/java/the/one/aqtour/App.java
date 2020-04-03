package the.one.aqtour;

import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;

import org.litepal.LitePal;

import the.one.aqtour.ui.activity.Launcher;
import the.one.base.BaseApplication;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager;


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

    private void initGSYVideoPlayer(){
        PlayerFactory.setPlayManager(Exo2PlayerManager.class);//EXO模式
        CacheFactory.setCacheManager(ExoPlayerCacheManager.class);//exo缓存模式，支持m3u8，只支持exo
    }

}
