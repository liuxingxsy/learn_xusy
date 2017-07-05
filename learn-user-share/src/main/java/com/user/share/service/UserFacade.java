package com.user.share.service;

import com.user.share.bean.User;

import java.util.List;
import java.util.Map;

/**
 * Created by siyong.xu on 2017-07-05.
 */
public interface UserFacade {
    /**
     * 新增一条记录
     *
     * @param insertObj
     * @return
     */
    void insert(User insertObj) ;

    /**
     * 批量保存记录
     *
     * @param objList
     * @return
     */
    void batchInsert(List<User> objList) ;

    /**
     * 更新记录
     *
     * @param updateObj
     * @return
     */
    void update(User updateObj);

    /**
     * 批量更新记录
     *
     * @param objList
     * @return
     */
    void batchUpdate(List<User> objList) ;

    /**
     * 根据id删除记录
     *
     * @param id
     * @return
     */
    void deleteById(Long id);

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     */
    User getById(Long id) ;

    /**
     * 根据条件查询 getByMap
     *
     * @param paramMap
     * @return
     */
    User getByMap(Map<String, Object> paramMap);

    /**
     * 根据条件查询 listByMap
     *
     * @param paramMap
     * @return
     */
    List<User> listByMap(Map<String, Object> paramMap);

}
