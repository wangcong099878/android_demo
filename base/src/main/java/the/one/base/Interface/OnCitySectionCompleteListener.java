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

import java.util.List;

import the.one.base.model.CitySection;

/**
 * @author The one
 * @date 2020/5/20 0020
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public interface OnCitySectionCompleteListener {

    void onCitySectionComplete(List<CitySection> provinces);

    void onCitySectionError();

}
