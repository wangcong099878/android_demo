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

import com.qmuiteam.qmui.widget.section.QMUISection;

import java.util.List;

/**
 * @author The one
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class HomeItemSection implements QMUISection.Model<HomeItemSection>{

    public String url;
    public String content;
    public List<String> images;
    public String remark;

    public HomeItemSection(String content, List<String> images, String remark,String url) {
        this.content = content;
        this.images = images;
        this.remark = remark;
        this.url = url;
    }

    @Override
    public HomeItemSection cloneForDiff() {
        return new HomeItemSection(content,images,remark,url);
    }

    @Override
    public boolean isSameItem(HomeItemSection other) {
        return content == other.content || (content != null && content.equals(other.content));
    }

    @Override
    public boolean isSameContent(HomeItemSection other) {
        return true;
    }
}
