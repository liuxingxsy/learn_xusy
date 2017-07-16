package com.learn.service;

import com.user.share.bean.User;
import xusy.learn.common.page.DataGridResult;
import xusy.learn.common.page.PageParam;

/**
 * Created by siyong.xu on 2017-07-05.
 */
public interface UserService {
    User queryUserById(Long id);
    Boolean saveUser(User user);
    Boolean updateUser(User user);
    Boolean deleteUser(Long id);
    DataGridResult queryPageForUserList(PageParam pageParam);
}
