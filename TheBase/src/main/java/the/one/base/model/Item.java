package the.one.base.model;

/**
 * @author The one
 * @date 2018/10/10 0010
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class Item {

    private String title;
    private int normal;
    private int select;

    public Item(String title, int normal, int select) {
        this.title = title;
        this.normal = normal;
        this.select = select;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }
}
