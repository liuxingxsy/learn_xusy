package com.xusy.core.dao.impl;

import com.xusy.core.dao.BaseDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import xusy.learn.common.page.PageBean;
import xusy.learn.common.page.PageParam;

import java.util.List;
import java.util.Map;

/**
 * 数据访问层基础公共实现类
 * Created by siyong.xu on 2017-07-03.
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
    protected static final  Logger log = LogManager.getLogger(BaseDaoImpl.class);

    /**
     * 新增一条记录
     *
     * @param insertObj
     * @return
     */
    @Override
    public long insert(T insertObj) {
        return 0;
    }

    /**
     * 批量保存记录
     *
     * @param objList
     * @return
     */
    @Override
    public long insertList(List<T> objList) {
        return 0;
    }

    /**
     * 更新记录
     *
     * @param updateObj
     * @return
     */
    @Override
    public long update(T updateObj) {
        return 0;
    }

    /**
     * 批量更新记录
     *
     * @param objList
     * @return
     */
    @Override
    public long updateList(T objList) {
        return 0;
    }

    /**
     * 根据id删除记录
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        return 0;
    }

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     */
    @Override
    public T getById(Long id) {
        return null;
    }

    /**
     * 根据条件查询 getByMap
     *
     * @param paramMap
     * @return
     */
    @Override
    public T getByMap(Map<String, Object> paramMap) {
        return null;
    }

    /**
     * 根据条件查询 listByMap
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<T> listByMap(Map<String, Object> paramMap) {
        return null;
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

    @Override
    public SqlSessionTemplate getSqlSessionTemplate() {
        return null;
    }

    @Override
    public SqlSession getSqlSession() {
        return null;
    }
}
