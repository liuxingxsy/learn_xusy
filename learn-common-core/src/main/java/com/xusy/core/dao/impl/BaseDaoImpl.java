package com.xusy.core.dao.impl;

import com.xusy.core.dao.BaseDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import xusy.learn.common.enums.ErrorEnum;
import xusy.learn.common.exceptions.BizException;
import xusy.learn.common.page.PageBean;
import xusy.learn.common.page.PageParam;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 数据访问层基础公共实现类
 * Created by siyong.xu on 2017-07-03.
 */
public class BaseDaoImpl<T> extends SqlSessionDaoSupport implements BaseDao<T> {
    protected static final Logger log = LogManager.getLogger(BaseDaoImpl.class);

    private static final String SQL_INSERT = "insert";
    private static final String SQL_BATCH_INSERT = "batchInsert";
    private static final String SQL_UPDATE = "update";
    private static final String SQL_BATCH_UPDATE = "batchUpdate";
    private static final String SQL_GET_BY_ID = "getById";
    private static final String SQL_DELETE_BY_ID = "deleteById";
    private static final String SQL_LIST_PAGE = "listPage";
    private static final String SQL_BY_MAP = "getByMap";
    private static final String SQL_LIST_BY = "listByMap";

    @Resource(name = "sqlSessionFactory")
    private void setSupperSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }


    /**
     * 获取默认SqlMapping命名空间
     *
     * @return
     */
    protected String getNamespace(String sqlId) {
        //反射取父类
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) genericSuperclass;
        Class<T> clazz = (Class<T>) p.getActualTypeArguments()[0];
        StringBuffer sb = new StringBuffer(50);
        sb.append(clazz.getName()).append(".").append(sqlId);
        return sb.toString();
    }


    /**
     * 新增一条记录
     *
     * @param insertObj
     * @return
     */
    @Override
    public void insert(T insertObj) {
        int result = getSqlSession().insert(getNamespace(SQL_INSERT), insertObj);
        if (result < 1) {
            throw new BizException(ErrorEnum.DB_INSERT_RESULT_0.getCode(), "{%s}." + ErrorEnum.DB_INSERT_RESULT_0.getMsg(), getNamespace(SQL_INSERT));
        }
    }

    /**
     * 批量保存记录
     *
     * @param objList
     * @return
     */
    @Override
    public void batchInsert(List<T> objList)  {
        int result = getSqlSession().insert(getNamespace(SQL_BATCH_INSERT), objList);
        if (result < 1) {
            throw new BizException(ErrorEnum.DB_INSERT_RESULT_0.getCode(), "{%s}." + ErrorEnum.DB_INSERT_RESULT_0.getMsg(), getNamespace(SQL_INSERT));
        }
    }

    /**
     * 更新记录
     *
     * @param updateObj
     * @return
     */
    @Override
    public void update(T updateObj){
        int result = getSqlSession().update(getNamespace(SQL_UPDATE), updateObj);
        if (result < 1) {
            throw new BizException(ErrorEnum.DB_UPDATE_RESULT_0.getCode(), "{%s}." + ErrorEnum.DB_UPDATE_RESULT_0.getMsg(), getNamespace(SQL_UPDATE));
        }
    }

    /**
     * 批量更新记录
     *
     * @param objList
     * @return
     */
    @Override
    public void batchUpdate(List<T> objList) {
        int result = getSqlSession().update(getNamespace(SQL_UPDATE), objList);
        if (result < 1) {
            throw new BizException(ErrorEnum.DB_UPDATE_RESULT_0.getCode(), "{%s}." + ErrorEnum.DB_UPDATE_RESULT_0.getMsg(), getNamespace(SQL_UPDATE));
        }
    }

    /**
     * 根据id删除记录
     *
     * @param id
     * @return
     */
    @Override
    public void deleteById(Long id) {
        int result = getSqlSession().delete(getNamespace(SQL_DELETE_BY_ID), id);
        if (result < 1) {
            throw new BizException(ErrorEnum.DB_DELETE_RESULT_0.getCode(), "{%s}." + ErrorEnum.DB_DELETE_RESULT_0.getMsg(), getNamespace(SQL_DELETE_BY_ID));
        }
    }

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     */
    @Override
    public T getById(Long id)  {
        Object result = getSqlSession().selectOne(getNamespace(SQL_GET_BY_ID), id);
        if (result == null) {
            throw new BizException(ErrorEnum.DB_SELECTONE_IS_NULL.getCode(), "{%s}." + ErrorEnum.DB_SELECTONE_IS_NULL.getMsg(), getNamespace(SQL_GET_BY_ID));
        }
        return (T) result;
    }

    /**
     * 根据条件查询 getByMap
     *
     * @param paramMap
     * @return
     */
    @Override
    public T getByMap(Map<String, Object> paramMap) {
        return  (T)getSqlSession().selectOne(getNamespace(SQL_BY_MAP), paramMap);
    }

    /**
     * 根据条件查询 listByMap
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<T> listByMap(Map<String, Object> paramMap) {

        return  (List<T>)getSqlSession().selectList(getNamespace(SQL_LIST_BY),paramMap);
    }

    /**
     * 分页查询
     *
     * @param pageParam
     * @param paramMap
     * @return
     */
    @Override
    public PageBean<T> listPage(PageParam pageParam, Map<String, Object> paramMap) {
        return null;
    }

}
