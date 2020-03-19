package the.one.base.model;

import the.one.base.Interface.IPageInfo;

public class SamplePageInfoBean implements IPageInfo {

    private int totalCount;
    private int page;
    private int pageSize;

    public SamplePageInfoBean(int totalCount, int page) {
        this.totalCount = totalCount;
        this.page = page;
    }

    public SamplePageInfoBean(int totalCount, int page, int pageSize) {
        this.totalCount = totalCount;
        this.page = page;
        this.pageSize = pageSize;
    }

    @Override
    public int getPageTotalCount() {
        return totalCount;
    }

    @Override
    public int getCurrentPage() {
        return page;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
