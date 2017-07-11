package com.xusy.core.pagination.utils;

import com.xusy.core.pagination.dialect.Dialect;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by siyong.xu on 2017-07-11.
 */
public class SQLHelp {
    private  static  final Logger logger= LogManager.getLogger(SQLHelp.class);
    /**
     * 查询总纪录数
     *
     * @param mappedStatement mapped
     * @param parameterObject 参数
     * @param boundSql        boundSql
     * @param dialect         database dialect
     * @return 总记录数
     * @throws java.sql.SQLException sql查询错误
     */
    public static int getCount(
            final MappedStatement mappedStatement, final Object parameterObject,
            final BoundSql boundSql, Dialect dialect) throws SQLException {
        final String count_sql = dialect.getCountSQL();
        logger.debug("Total count SQL [{}] ", count_sql);
        logger.debug("Total count Parameters: {} ", parameterObject);

        Connection connection = null;
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
            countStmt = connection.prepareStatement(count_sql);
            DefaultParameterHandler handler = new DefaultParameterHandler(mappedStatement,parameterObject,boundSql);
            handler.setParameters(countStmt);

            rs = countStmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            logger.debug("Total count: {}", count);
            return count;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } finally {
                try {
                    if (countStmt != null) {
                        countStmt.close();
                    }
                } finally {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                }
            }
        }
    }
}
