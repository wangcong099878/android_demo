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

import org.jetbrains.annotations.NotNull;

/**
 * @author The one
 * @date 2019/8/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class CitySection extends LetterSearchSection {

    public Province province;
    public City city;
    public Area area;

    public CitySection(@NotNull Province province, City city, Area area) {
        super(null == area?province.getName():area.getName());
        this.province = province;
        this.city = city;
        this.area = area;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getFullLocationName() {
        return province.getName() + city.getName() + name;
    }

}
