package the.one.gank.net;

import java.util.List;

import the.one.base.model.SamplePageInfoBean;

public class ListResponse<T> {

    private List<T> data;
    private String msg;
    private SamplePageInfoBean pageInfoBean;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SamplePageInfoBean getPageInfoBean() {
        return pageInfoBean;
    }

    public void setPageInfoBean(SamplePageInfoBean pageInfoBean) {
        this.pageInfoBean = pageInfoBean;
    }
}
