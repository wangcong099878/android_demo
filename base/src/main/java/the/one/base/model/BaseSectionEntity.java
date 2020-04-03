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

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author The one
 * @date 2020/4/3 0003
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BaseSectionEntity<T> implements SectionEntity {

    public String header;
    public T t;

    public BaseSectionEntity(String header) {
        this.header = header;
    }

    public BaseSectionEntity(T t) {
        this.t = t;
    }

    @Override
    public boolean isHeader() {
        return !TextUtils.isEmpty(header);
    }

    @Override
    public int getItemType() {
        return isHeader() ? HEADER_TYPE : NORMAL_TYPE;
    }
}
