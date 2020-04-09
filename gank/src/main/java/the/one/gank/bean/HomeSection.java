package the.one.gank.bean;

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

import the.one.base.model.BaseSectionEntity;

/**
 * @author The one
 * @date 2020/4/9 0009
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class HomeSection extends BaseSectionEntity<GankBean> {

    public HomeSection(String header) {
        super(header);
    }

    public HomeSection(String header,GankBean gankBean) {
        super(header,gankBean);
    }
}
