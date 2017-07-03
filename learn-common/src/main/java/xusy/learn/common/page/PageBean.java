package xusy.learn.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体
 * Created by siyong.xu on 2017-07-03.
 */
public class PageBean<T> implements Serializable {
    private static final long serialVersionUID = -6307958831098525556L;
    /**
     * 当前页
     */
    private int pageIdex;
    /**
     * 每页条数
     */
    private int pageSize;
    /**
     * 总记录数
     */
    private int totalCount;
    /**
     * 当前页记录列表
     */
    private List<T> recordList;

    /**
     * 总页数
     */
    private int pageCount;
    /**
     * 页面开始索引
     */
    private int pageStartIndex;
    /**
     * 页面结束索引
     */
    private int pageEndIndex;

    public PageBean(List<T> recordList, int pageIdex, int pageSize, int totalCount) {
        this.recordList = recordList;
        this.pageIdex = pageIdex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.pageCount = getTotalPage(pageSize, totalCount);
        setSartAndEndIdex();
    }

    /**
     * 计算总页数
     *
     * @param pageSize
     * @param totalCount
     * @return
     */
    public int getTotalPage(int pageSize, int totalCount) {
        return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
    }

    /**
     * 设置页面展示开始结束页码
     */
    public void setSartAndEndIdex() {
        //总页数不多于10页，则全部显示
        if (this.pageCount < 10) {
            this.pageStartIndex = 1;
            this.pageEndIndex = pageCount;
        } else {
            // 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
            this.pageStartIndex = pageIdex - 4;
            this.pageEndIndex = pageIdex + 5;
            // 当前面的页码不足4个时，则显示前10个页码
            if (pageStartIndex < 1) {
                pageStartIndex = 1;
                pageEndIndex = 10;
            }
            // 当后面的页码不足5个时，则显示后10个页码
            if (pageEndIndex > pageCount) {
                pageEndIndex = pageCount;
                pageStartIndex = pageCount - 10 + 1;
            }
        }
    }

}
