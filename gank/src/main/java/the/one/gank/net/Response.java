package the.one.gank.net;

import the.one.base.model.SamplePageInfoBean;

public class Response<T> {

    private int status;
    private String msg;
    private T data;
    private SamplePageInfoBean pageInfo;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SamplePageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(SamplePageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }
}
