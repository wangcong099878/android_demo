package the.one.base.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/15.
 */
public class PageInfoBean implements Parcelable {

    /**
     * pageTotalCount : 0
     * totalCount : 0
     * Page : 1
     * PageSize : 10
     */

    private int pageTotalCount;
    private String totalCount;
    private int Page;
    private int PageSize;

    public PageInfoBean(int pageTotalCount, String totalCount, int page, int pageSize) {
        this.pageTotalCount = pageTotalCount;
        this.totalCount = totalCount;
        Page = page;
        PageSize = pageSize;
    }
    public PageInfoBean(int pageTotalCount, int page) {
        this.pageTotalCount = pageTotalCount;
        Page = page;
    }


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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pageTotalCount);
        dest.writeString(this.totalCount);
        dest.writeInt(this.Page);
        dest.writeInt(this.PageSize);
    }

    protected PageInfoBean(Parcel in) {
        this.pageTotalCount = in.readInt();
        this.totalCount = in.readString();
        this.Page = in.readInt();
        this.PageSize = in.readInt();
    }

    public static final Creator<PageInfoBean> CREATOR = new Creator<PageInfoBean>() {
        @Override
        public PageInfoBean createFromParcel(Parcel source) {
            return new PageInfoBean(source);
        }

        @Override
        public PageInfoBean[] newArray(int size) {
            return new PageInfoBean[size];
        }
    };
}