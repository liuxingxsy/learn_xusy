package com.learn.facade.impl;

import com.learn.service.UserService;
import com.user.share.bean.User;
import com.user.share.service.UserFacade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by siyong.xu on 2017-07-05.
 */
@Service("userFacade")
public class UserFacadeImpl implements UserFacade {
    @Resource
    UserService userService;

    /**
     * 新增一条记录
     *
     * @param insertObj
     * @return
     */
    @Override
    public void insert(User insertObj) {
        userService.insert(insertObj);
    }

    /**
     * 批量保存记录
     *
     * @param objList
     * @return
     */
    @Override
    public void batchInsert(List<User> objList) {
        userService.batchInsert(objList);
    }

    /**
     * 更新记录
     *
     * @param updateObj
     * @return
     */
    @Override
    public void update(User updateObj) {
        userService.update(updateObj);
    }

    /**
     * 批量更新记录
     *
     * @param objList
     * @return
     */
    @Override
    public void batchUpdate(List<User> objList) {
        userService.batchUpdate(objList);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     * @return
     */
    @Override
    public void deleteById(Long id) {
        userService.deleteById(id);
    }

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     */
    @Override
    public User getById(Long id) {
        return userService.getById(id);
    }

    /**
     * 根据条件查询 getByMap
     *
     * @param paramMap
     * @return
     */
    @Override
    public User getByMap(Map<String, Object> paramMap) {
        return userService.getByMap(paramMap);
    }

    /**
     * 根据条件查询 listByMap
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<User> listByMap(Map<String, Object> paramMap) {
        return userService.listByMap(paramMap);
    }
}
