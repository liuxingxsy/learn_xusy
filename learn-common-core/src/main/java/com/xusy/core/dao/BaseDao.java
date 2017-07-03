package com.xusy.core.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import xusy.learn.common.page.PageBean;
import xusy.learn.common.page.PageParam;

import java.util.List;
import java.util.Map;

/**
 * 数据访问层基础公共接口.
 * Created by siyong.xu on 2017-07-03.
 */
public interface BaseDao<T> {
    /**
     * 新增一条记录
     *
     * @param insertObj
     * @return
     */
    long insert(T insertObj);

    /**
     * 批量保存记录
     *
     * @param objList
     * @return
     */
    long insertList(List<T> objList);

    /**
     * 更新记录
     *
     * @param updateObj
     * @return
     */
    long update(T updateObj);

    /**
     * 批量更新记录
     *
     * @param objList
     * @return
     */
    long updateList(T objList);

    /**
     * 根据id删除记录
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 根据条件查询 getByMap
     *
     * @param paramMap
     * @return
     */
    T getByMap(Map<String, Object> paramMap);

    /**
     * 根据条件查询 listByMap
     *
     * @param paramMap
     * @return
     */
    List<T> listByMap(Map<String, Object> paramMap);

    /**
     * 分页查询
     *
     * @param pageParam
     * @param paramMap
     * @return
     */
    PageBean<T> listPage(PageParam pageParam, Map<String, Object> paramMap);

    SqlSessionTemplate getSqlSessionTemplate();

    SqlSession getSqlSession();

}
