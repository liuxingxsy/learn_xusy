package xusy.learn.common.page;

import java.io.Serializable;

/**
 * 分页传入参数实体
 * Created by siyong.xu on 2017-07-03.
 */
public class PageParam implements Serializable{
    private static final long serialVersionUID = 1850679656346684788L;
    /**
     * 当前第几页
     */
    private  int pageIndex;
    /**
     * 每页记录数
     */
    private  int pageSize;

    public PageParam(int pageIndex, int pageSize) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
