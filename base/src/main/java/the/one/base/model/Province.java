package the.one.base.model;

import java.util.List;

import the.one.base.Interface.ICitySelector;

/**
 * @author The one
 * @date 2018/10/30 0030
 * @describe 省
 * @email 625805189@qq.com
 * @remark
 */
public class Province implements ICitySelector {

    private String code;
    private String name;
    private List<City> cityList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

}
