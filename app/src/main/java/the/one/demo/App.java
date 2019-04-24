package the.one.demo;

import the.one.base.BaseApplication;
import the.one.base.util.FileDirectoryUtil;


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
    public void onCreate() {
        super.onCreate();
//        initFilePath();
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
     * 是否打印日志
     *
     * @return
     */
    @Override
    protected boolean isDebug() {
        return false;
    }
}
