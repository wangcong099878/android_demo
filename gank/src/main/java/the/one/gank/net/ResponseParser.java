package the.one.gank.net;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;
import rxhttp.wrapper.utils.GsonUtil;

@Parser(name = "Response",wrappers = {List.class})
public class ResponseParser<T> extends AbstractParser<Response<T>> {

    protected ResponseParser() {
    }

    public ResponseParser(@NotNull Type type) {
        super(type);
    }

    @Override
    public Response<T> onParse(okhttp3.Response response) throws IOException {
        String body = response.body().string();
        String msg="";
        T data = null;
        int code = -1;
        Response<T> R = new Response<>();
        try {
            JSONObject object = new JSONObject(body);
            if (object.has(ResponseBuilder.getBuilder().getMsgStr()))
                msg = object.getString(ResponseBuilder.getBuilder().getMsgStr());
            if (object.has(ResponseBuilder.getBuilder().getEventStr())){
                code = object.getInt(ResponseBuilder.getBuilder().getEventStr());
                if (code == ResponseBuilder.getBuilder().getSuccessCode()){
                    if(object.has(ResponseBuilder.getBuilder().getDataStr())){
                        String dataContent = object.getString(ResponseBuilder.getBuilder().getDataStr());
                        if(!TextUtils.isEmpty(dataContent)){
                            data = GsonUtil.fromJson(dataContent, mType);
                        }
                    }
                }else{
                    throw new ParseException(String.valueOf(code),msg, response);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new ParseException( String.valueOf(code),e.getMessage(), response);
        }
        R.setMessage(msg);
        R.setData(data);
        return R;
    }
}