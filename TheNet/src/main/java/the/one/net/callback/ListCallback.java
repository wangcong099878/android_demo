package the.one.net.callback;

/**
 * Created by Administrator on 2016/7/20.
 */
public abstract class ListCallback<T> extends Callback<T>{

    /**
     * page : 1
     * pageSize : 10
     * pages : 0
     * total : 0
     */

    public int page;
    public int pageSize;
    public int pages;
    public int total;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 默认从第1页开始查询
     * @return
     */
    public int getSkip()
    {
        return 1;
    }

    /**
     * 默认每页的条目为10条
     * @return
     */
    public int getRows()
    {
        return 10;
    }

    public String getKey()
    {
        return "list";
    }
}
