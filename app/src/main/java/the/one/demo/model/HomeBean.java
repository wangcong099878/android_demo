package the.one.demo.model;

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

    @SerializedName("error")
    private boolean error;
    @SerializedName("results")
    private ResultsBean results;
    @SerializedName("category")
    private List<String> category;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public static class ResultsBean {
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
}
