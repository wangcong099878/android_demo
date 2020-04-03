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

/**
 * @author The one
 * @date 2020/3/30 0030
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class TabBean {

    private String title;
    private int normalDrawable = -1;
    private int selectDrawable = -1;
    private int normalColor = -1;
    private int selectColor = -1 ;

    public TabBean(String title) {
        this.title = title;
    }

    public TabBean(String title, int normalDrawable, int selectDrawable) {
        this.title = title;
        this.normalDrawable = normalDrawable;
        this.selectDrawable = selectDrawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNormalDrawable() {
        return normalDrawable;
    }

    public void setNormalDrawable(int normalDrawable) {
        this.normalDrawable = normalDrawable;
    }

    public int getSelectDrawable() {
        return selectDrawable;
    }

    public void setSelectDrawable(int selectDrawable) {
        this.selectDrawable = selectDrawable;
    }

    public int getNormalColor() {
        return normalColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public int getSelectColor() {
        return selectColor;
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }
}
