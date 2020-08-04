package the.one.gank.bean;

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

/**
 * @author The one
 * @date 2020/8/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class CategoryBean {


    /**
     * _id : 5e59ec146d359d60b476e621
     * coverImageUrl : http://gank.io/images/b9f867a055134a8fa45ef8a321616210
     * desc : Always deliver more than expected.（Larry Page）
     * title : Android
     * type : Android
     */

    @SerializedName("_id")
    private String id;
    @SerializedName("coverImageUrl")
    private String coverImageUrl;
    @SerializedName("desc")
    private String desc;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
