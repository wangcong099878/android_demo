package the.one.net.callback;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import the.one.net.entity.PageInfoBean;

public abstract class Callback<T> {

    public Type getType() {
        return type;
    }

    private Type type;

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public Callback()
    {
        type= getSuperclassTypeParameter(getClass());
    }

    /**
     * 请求成功
     * @param response
     */
    abstract public void onSuccess(T response,String msg,PageInfoBean pageInfoBean) ;

    /**
     * 请求失败
     * @param resultCode result的状态码，如果没有返回0
     * @param errorMsg 错误信息
     */
    abstract public void onFailure(int resultCode,String errorMsg);

    /**
     * 请求完成
     */
    public void onFinish(){

    }
}
