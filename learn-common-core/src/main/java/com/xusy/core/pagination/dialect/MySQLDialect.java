package com.xusy.core.pagination.dialect;

import com.xusy.core.pagination.bean.PageBounds;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * Created by siyong.xu on 2017-07-11.
 */
public class MySQLDialect extends Dialect {
    public MySQLDialect(MappedStatement mappedStatement, Object parameterObject, PageBounds pageBounds) {
        super(mappedStatement, parameterObject, pageBounds);
    }

    protected String getLimitString(String sql, String offsetName,int offset, String limitName, int limit,String orders) {
        StringBuffer buffer = new StringBuffer( sql.length()+20 ).append(sql);
        if(StringUtils.isNotEmpty(orders)){
            buffer.append(" order by ");
            buffer.append(orders);
        }

        if (offset > 0) {
            buffer.append(" limit ?, ?");
            setPageParameter(offsetName, offset, Integer.class);
            setPageParameter(limitName, limit, Integer.class);
        } else {
            buffer.append(" limit ?");
            setPageParameter(limitName, limit, Integer.class);
        }
        return buffer.toString();
    }
}
