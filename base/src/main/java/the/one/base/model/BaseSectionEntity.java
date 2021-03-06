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

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author The one
 * @date 2020/4/3 0003
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BaseSectionEntity<T> implements SectionEntity {

    public final String header;
    public final T t;

    public BaseSectionEntity(String header) {
        this(header,null);
    }

    public BaseSectionEntity(T t) {
        this(null,t);
    }

    public BaseSectionEntity(String header,T t) {
        this.header = header;
        this.t = t;
    }


    @Override
    public boolean isHeader() {
        return null == t;
    }

    @Override
    public int getItemType() {
        return isHeader() ? HEADER_TYPE : NORMAL_TYPE;
    }
}
