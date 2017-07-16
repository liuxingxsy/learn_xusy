package com.learn.service.impl;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.learn.dao.UserDao;
import com.learn.service.UserService;
import com.user.share.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xusy.learn.common.page.DataGridResult;
import xusy.learn.common.page.PageParam;

import java.util.List;

/**
 * Created by siyong.xu on 2017-07-05.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    public User queryUserById(Long id) {
        return this.userDao.selectByPrimaryKey(id);
    }

    public Boolean saveUser(User user) {
        return this.userDao.insert(user) == 1;
    }

    public Boolean updateUser(User user) {
        return this.userDao.updateByPrimaryKeySelective(user) == 1;
    }

    public Boolean deleteUser(Long id) {
        return this.userDao.deleteByPrimaryKey(id) == 1;
    }

    @Override
    public DataGridResult queryPageForUserList(PageParam pageParam) {
        // 设置分页参数
        PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        Example example = new Example(User.class);
        example.setOrderByClause("gmt_created DESC");
        //获取用户列表
        List<User> users = this.userDao.selectByExample(example);
        //获取分页后的信息
        PageInfo<User> userPageInfo = new PageInfo<>(users);

        return new DataGridResult(userPageInfo.getTotal(), userPageInfo.getList());
    }
}
