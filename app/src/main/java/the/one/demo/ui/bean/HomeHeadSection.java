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

/**
 * @author The one
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class HomeHeadSection implements QMUISection.Model<HomeHeadSection> {

    public String title;

    public HomeHeadSection(String title) {
        this.title = title;
    }

    @Override
    public HomeHeadSection cloneForDiff() {
        return new HomeHeadSection(title);
    }

    @Override
    public boolean isSameItem(HomeHeadSection other) {
        return title == other.title || (title != null && title.equals(other.title));
    }

    @Override
    public boolean isSameContent(HomeHeadSection other) {
        return true;
    }
}
