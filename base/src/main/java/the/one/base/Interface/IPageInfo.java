package the.one.base.Interface;

/**
 * 分页
 */
public interface IPageInfo {

    /**
     * 总页数
     * @return
     */
    int getPageTotalCount();
    /**
     * 当前页数
     * @return
     */
    int getCurrentPage();

    /**
     * 页数内容数量
     * @return
     */
    int getPageSize();

    /**
     * 内容数量
     * @return
     */
    int getTotalCount();

}
