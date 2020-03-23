package the.one.gank.net;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;

@Parser(name = "ResponseList")
public class ResponseListParser<T> extends AbstractParser<ListResponse<T>> {


    public ResponseListParser() {
    }

    public ResponseListParser(@NotNull Type type) {
        super(type);
    }

    //省略构造方法

    @Override
    public ListResponse<T> onParse(okhttp3.Response response) throws IOException {
        final Type type = ParameterizedTypeImpl.get(Response.class, List.class, mType); //获取泛型类型
        Response<List<T>> data = convert(response, type);
        List<T> list = data.getData(); //获取data字段
        ListResponse<T> listResponse = new ListResponse<>();
        listResponse.setData(list);
        listResponse.setPageInfoBean(data.getPageInfo());
        if (data.getStatus() != 100) {  //code不等于0，说明数据不正确，抛出异常
            throw new ParseException(String.valueOf(data.getStatus()),data.getMsg(), response);
        }
        return listResponse;
    }
}