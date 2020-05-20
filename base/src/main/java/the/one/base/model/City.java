package the.one.base.model;

import java.util.List;

/**
 * @author The one
 * @date 2018/10/30 0030
 * @describe 区，县
 * @email 625805189@qq.com
 * @remark
 */
public class City {

    private String code;
    private String name;
    private List<Area> areaList;

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

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

}
