package the.one.base.Interface;

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

import the.one.base.model.Area;
import the.one.base.model.City;
import the.one.base.model.Province;

/**
 * @author The one
 * @date 2020/5/21 0021
 * @describe 地址选择后的回调
 * @email 625805189@qq.com
 * @remark
 */
public interface OnCitySelectListener {

    void onCitySelect(Province province, City city, Area area,String address);

}
