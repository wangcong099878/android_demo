package the.one.base.model;

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
 * @date 2019/2/15 0015
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public  abstract class BaseSectionHead implements QMUISection.Model<BaseSectionHead>{

    protected final String text;

    public BaseSectionHead(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }


    @Override
    public boolean isSameItem(BaseSectionHead other) {
        return text == other.text || (text != null && text.equals(other.text));
    }

    @Override
    public boolean isSameContent(BaseSectionHead other) {
        return true;
    }

}
