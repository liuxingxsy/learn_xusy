package com.learn.web.controller;

import com.learn.service.UserService;
import com.user.share.bean.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xusy.learn.common.page.DataGridResult;
import xusy.learn.common.page.PageParam;

import javax.annotation.Resource;


/**
 * Created by 流星xhq on 2017/7/16.
 */
@Controller
public class UserController {
    Logger logger = LogManager.getLogger(UserController.class);
    @Resource
    UserService userService;

    /**
     * 通过id查询用户信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> queryUserById(@PathVariable(value = "id") Long id) {
        try {
            User user = userService.queryUserById(id);
            if (user == null) {
                // 资源不存在，响应404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("通过id获取用户信息异常！", e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 新增用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(User user) {
        try {
            Boolean bool = userService.saveUser(user);
            if (bool) {
                // 新增成功，响应201
                return ResponseEntity.status(HttpStatus.CREATED).body(user);
            }
        } catch (Exception e) {
            logger.error("新增用户信息异常！", e);
        }
        // 新增失败，响应500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(User user) {
        try {
            Boolean bool = userService.updateUser(user);
            if (bool) {
                // 更新成功，响应204
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            logger.error("更新用户信息异常！", e);
        }
        // 新增失败，响应500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@RequestParam(value = "id", defaultValue = "0") Long id) {
        try {
            Boolean bool = userService.deleteUser(id);
            if (bool) {
                // 删除成功，响应204
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            logger.error("删除用户异常！", e);
        }
        // 删除失败，响应500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 查询用户列表信息
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public DataGridResult queryUserList(PageParam pageParam) {
        try {
            DataGridResult dataGridResult = userService.queryPageForUserList(pageParam);
            return dataGridResult;
        } catch (Exception e) {
            logger.error("获取用户列表信息异常！", e);
        }
        return null;
    }

}
