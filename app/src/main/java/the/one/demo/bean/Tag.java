package the.one.demo.bean;

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
 * @date 2019/8/23 0023
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class Tag {

    private String tagTitle;
    private Class tagClass;

    public Tag(String tagTitle, Class tagClass) {
        this.tagTitle = tagTitle;
        this.tagClass = tagClass;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public Class getTagClass() {
        return tagClass;
    }
}
