package com.xusy.core.service.impl;

import com.xusy.core.dao.BaseDao;
import com.xusy.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xusy.learn.common.page.PageBean;
import xusy.learn.common.page.PageParam;

import java.util.List;
import java.util.Map;

/**
 * Created by siyong.xu on 2017-07-05.
 */
@Service
public class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    BaseDao<T> baseDao;

    /**
     * 新增一条记录
     *
     * @param insertObj
     * @return
     */
    @Override
    public void insert(T insertObj) {
        baseDao.insert(insertObj);
    }

    /**
     * 批量保存记录
     *
     * @param objList
     * @return
     */
    @Override
    public void batchInsert(List<T> objList) {
        baseDao.batchInsert(objList);
    }

    /**
     * 更新记录
     *
     * @param updateObj
     * @return
     */
    @Override
    public void update(T updateObj) {
        baseDao.update(updateObj);
    }

    /**
     * 批量更新记录
     *
     * @param objList
     * @return
     */
    @Override
    public void batchUpdate(List<T> objList) {
        baseDao.batchUpdate(objList);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     * @return
     */
    @Override
    public void deleteById(Long id) {
        baseDao.deleteById(id);
    }

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     */
    @Override
    public T getById(Long id) {
        return baseDao.getById(id);
    }

    /**
     * 根据条件查询 getByMap
     *
     * @param paramMap
     * @return
     */
    @Override
    public T getByMap(Map<String, Object> paramMap) {
        return baseDao.getByMap(paramMap);
    }

    /**
     * 根据条件查询 listByMap
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<T> listByMap(Map<String, Object> paramMap) {
        return baseDao.listByMap(paramMap);
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
        return baseDao.listPage(pageParam,paramMap);
    }
}
