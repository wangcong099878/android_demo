package the.one.gank.net;

import the.one.base.model.SamplePageInfoBean;

public class Response<T> {

    private int event;
    private String message;
    private T data;
    private SamplePageInfoBean pageInfo;


    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
