package com.xusy.core.service;

import xusy.learn.common.page.PageBean;
import xusy.learn.common.page.PageParam;

import java.util.List;
import java.util.Map;

/**
 * Created by siyong.xu on 2017-07-05.
 */
public interface BaseService<T> {
    /**
     * 新增一条记录
     *
     * @param insertObj
     * @return
     */
    void insert(T insertObj);

    /**
     * 批量保存记录
     *
     * @param objList
     * @return
     */
    void batchInsert(List<T> objList) ;

    /**
     * 更新记录
     *
     * @param updateObj
     * @return
     */
    void update(T updateObj);

    /**
     * 批量更新记录
     *
     * @param objList
     * @return
     */
    void batchUpdate(List<T> objList);

    /**
     * 根据id删除记录
     *
     * @param id
     * @return
     */
    void deleteById(Long id) ;

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
}
