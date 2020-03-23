package the.one.gank.net;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;

@Parser(name = "Response")
public class ResponseParser<T> extends AbstractParser<T> {

    public ResponseParser() {
    }

    public ResponseParser(@NotNull Type type) {
        super(type);
    }

    @Override
    public T onParse(okhttp3.Response response) throws IOException {
        final Type type = ParameterizedTypeImpl.get(Response.class, mType); //获取泛型类型
        Response<T> data = convert(response, type);
        T t = data.getData(); //获取data字段
        if (data.getStatus() != 100 ) {//code不等于0，说明数据不正确，抛出异常
            throw new ParseException( String.valueOf(data.getStatus()),data.getMsg(), response);
        }
        return t;
    }
}