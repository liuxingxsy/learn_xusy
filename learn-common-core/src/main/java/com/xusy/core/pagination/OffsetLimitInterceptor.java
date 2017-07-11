package com.xusy.core.pagination;

import com.xusy.core.pagination.bean.PageBounds;
import com.xusy.core.pagination.dialect.Dialect;
import com.xusy.core.pagination.utils.MybatisPropertiesHelper;
import com.xusy.core.pagination.utils.SQLHelp;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * Mybatis基于方言分页查询插件
 * Created by siyong.xu on 2017-07-11.
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class OffsetLimitInterceptor implements Interceptor {
    private static final Logger logger = LogManager.getLogger(OffsetLimitInterceptor.class);

    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    static int ROWBOUNDS_INDEX = 2;
    static int RESULT_HANDLER_INDEX = 3;

    static ExecutorService Pool;
    String dialectClass;
    boolean asyncTotalCount = false;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        final Executor executor = (Executor) invocation.getTarget();
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];
        final RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];
        final PageBounds pageBounds = new PageBounds(rowBounds);

        if(pageBounds.getOffset() == RowBounds.NO_ROW_OFFSET
                && pageBounds.getLimit() == RowBounds.NO_ROW_LIMIT
                && pageBounds.getOrders().isEmpty()){
            return invocation.proceed();
        }
        final Dialect dialect;
        try {
            Class clazz = Class.forName(dialectClass);
            Constructor constructor = clazz.getConstructor(MappedStatement.class, Object.class, PageBounds.class);
            dialect = (Dialect)constructor.newInstance(new Object[]{ms, parameter, pageBounds});
        } catch (Exception e) {
            throw new ClassNotFoundException("Cannot create dialect instance: "+dialectClass,e);
        }

        final BoundSql boundSql = ms.getBoundSql(parameter);

        queryArgs[MAPPED_STATEMENT_INDEX] = copyFromNewSql(ms,boundSql,dialect.getPageSQL(), dialect.getParameterMappings(), dialect.getParameterObject());
        queryArgs[PARAMETER_INDEX] = dialect.getParameterObject();
        queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET,RowBounds.NO_ROW_LIMIT);

        Boolean async = pageBounds.getAsyncTotalCount() == null ? asyncTotalCount : pageBounds.getAsyncTotalCount();
        Future<List> listFuture = call(new Callable<List>() {
            public List call() throws Exception {
                return (List)invocation.proceed();
            }
        }, async);

        if(pageBounds.isContainsTotalCount()){
            Callable<CurrentPage> countTask = new Callable() {
                public Object call() throws Exception {
                    Integer count;
                    Cache cache = ms.getCache();
                    if(cache != null && ms.isUseCache() && ms.getConfiguration().isCacheEnabled()){
                        CacheKey cacheKey = executor.createCacheKey(ms,parameter,new PageBounds(),copyFromBoundSql(ms,boundSql,dialect.getCountSQL(), boundSql.getParameterMappings(), boundSql.getParameterObject()));
                        count = (Integer)cache.getObject(cacheKey);
                        if(count == null){
                            count = SQLHelp.getCount(ms,parameter,boundSql,dialect);
                            cache.putObject(cacheKey, count);
                        }
                    }else{
                        count = SQLHelp.getCount(ms,parameter,boundSql,dialect);
                    }
                    return new CurrentPage(pageBounds.getPage(), pageBounds.getLimit(), count);
                }
            };
            Future<CurrentPage> countFutrue = call(countTask, async);
            return new PageList(listFuture.get(),countFutrue.get());
        }

        return listFuture.get();
    }
    private <T> Future<T> call(Callable callable, boolean async){
        if(async){
            return Pool.submit(callable);
        }else{
            FutureTask<T> future = new FutureTask(callable);
            future.run();
            return future;
        }
    }
    private MappedStatement copyFromNewSql(MappedStatement ms, BoundSql boundSql,
                                           String sql, List<ParameterMapping> parameterMappings, Object parameter){
        BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, sql, parameterMappings, parameter);
        return copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
    }
    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql,
                                      String sql, List<ParameterMapping> parameterMappings,Object parameter) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, parameterMappings, parameter);
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }
    private MappedStatement copyFromMappedStatement(MappedStatement ms,SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(),ms.getId(),newSqlSource,ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if(ms.getKeyProperties() != null && ms.getKeyProperties().length !=0){
            StringBuffer keyProperties = new StringBuffer();
            for(String keyProperty : ms.getKeyProperties()){
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length()-1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }

        //setStatementTimeout()
        builder.timeout(ms.getTimeout());

        //setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());

        //setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());

        //setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {
        MybatisPropertiesHelper propertiesHelper = new MybatisPropertiesHelper(properties);
        String dialectClass = propertiesHelper.getRequiredString("dialectClass");
        setDialectClass(dialectClass);

        setAsyncTotalCount(propertiesHelper.getBoolean("asyncTotalCount",false));

        setPoolMaxSize(propertiesHelper.getInt("poolMaxSize",0));
    }
    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;
        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    public void setDialectClass(String dialectClass) {
        logger.debug("dialectClass: {} ", dialectClass);
        this.dialectClass = dialectClass;
    }

    public void setAsyncTotalCount(boolean asyncTotalCount) {
        logger.debug("asyncTotalCount: {} ", asyncTotalCount);
        this.asyncTotalCount = asyncTotalCount;
    }

    public void setPoolMaxSize(int poolMaxSize) {

        if(poolMaxSize > 0){
            logger.debug("poolMaxSize: {} ", poolMaxSize);
            Pool = Executors.newFixedThreadPool(poolMaxSize);
        }else{
            Pool = Executors.newCachedThreadPool();
        }


    }
}
