package the.one.gank.net;

import java.util.List;

import the.one.base.model.SamplePageInfoBean;

public class ListResponse<T> {

    private List<T> data;
    private SamplePageInfoBean pageInfoBean;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public SamplePageInfoBean getPageInfoBean() {
        return pageInfoBean;
    }

    public void setPageInfoBean(SamplePageInfoBean pageInfoBean) {
        this.pageInfoBean = pageInfoBean;
    }
}
