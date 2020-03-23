package the.one.wallpaper.bean;

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
public class Wallpaper  {

    public int type;
    public String path ;
    public long size;
    public String duration;
    public String thumbPath;
    public long addTime;

    public Wallpaper() {

    }

    public Wallpaper(String path, long size, String duration, String thumbPath, long addTime) {
        this.path = path;
        this.size = size;
        this.duration = duration;
        this.thumbPath = thumbPath;
        this.addTime = addTime;
    }

}
