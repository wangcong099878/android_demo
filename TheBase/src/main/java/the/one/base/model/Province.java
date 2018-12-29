package the.one.base.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author The one
 * @date 2018/10/30 0030
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class Province   {
    /**
     * name : 北京市
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","平谷区","怀柔区","密云县","延庆县"]}]
     */

    @SerializedName("name")
    private String name;
    @SerializedName("city")
    private ArrayList<City> city;
    @SerializedName("area")
    private ArrayList<String> area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<City> getCity() {
        return city;
    }

    public void setCity(ArrayList<City> city) {
        this.city = city;
    }

    public ArrayList<String> getArea() {
        return area;
    }

    public void setArea(ArrayList<String> area) {
        this.area = area;
    }
}
