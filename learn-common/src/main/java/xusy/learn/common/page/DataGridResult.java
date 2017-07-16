package xusy.learn.common.page;

import java.util.List;

/**
 * Created by 流星xhq on 2017/7/16.
 */
public class DataGridResult {
    private Long total;

    private List<?> rows;

    public DataGridResult() {
    }

    public DataGridResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
