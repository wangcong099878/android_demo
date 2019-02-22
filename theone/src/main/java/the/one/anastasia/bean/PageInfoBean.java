package the.one.anastasia.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/15.
 */
public class PageInfoBean  {

    /**
     * pageTotalCount : 0
     * totalCount : 0
     * Page : 1
     * PageSize : 10
     */

    @SerializedName("pageTotalCount")
    private int pageTotalCount;
    @SerializedName("totalCount")
    private String totalCount;
    @SerializedName("Page")
    private int Page;
    @SerializedName("PageSize")
    private int PageSize;

    public int getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(int pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public int getPage() {
        return Page;
    }

    public void setPage(int Page) {
        this.Page = Page;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int PageSize) {
        this.PageSize = PageSize;
    }

    @Override
    public String toString() {
        return "PageInfoBean{" +
                "pageTotalCount=" + pageTotalCount +
                ", totalCount='" + totalCount + '\'' +
                ", Page=" + Page +
                ", PageSize=" + PageSize +
                '}';
    }
}