package the.one.gank.net;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;

@Parser(name = "ResponseOld",wrappers = {List.class})
public class ResponseOldParser<T> extends AbstractParser<T> {

    public ResponseOldParser(@NotNull Type type) {
        super(type);
    }

    @Override
    public T onParse(okhttp3.Response response) throws IOException {
        final Type type = ParameterizedTypeImpl.get(ResponseOld.class, mType); //获取泛型类型
        ResponseOld<T> data = convert(response, type);
        T t = data.getResults(); //获取data字段
        if (data.isError() ) {//code不等于0，说明数据不正确，抛出异常
            throw new ParseException("","请求失败", response);
        }
        return t;
    }
}