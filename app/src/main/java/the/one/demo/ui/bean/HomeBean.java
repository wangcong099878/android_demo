package the.one.demo.ui.bean;

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

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author The one
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class HomeBean {

        @SerializedName("Android")
        public List<GankBean> Android;
        @SerializedName("App")
        public List<GankBean> App;
        @SerializedName("iOS")
        public List<GankBean> iOS;
        @SerializedName("休息视频")
        public List<GankBean> relax;
        @SerializedName("前端")
        public List<GankBean> front;
        @SerializedName("拓展资源")
        public List<GankBean> extension;
        @SerializedName("瞎推荐")
        public List<GankBean> recommend;
        @SerializedName("福利")
        public List<GankBean> welfare;
}
