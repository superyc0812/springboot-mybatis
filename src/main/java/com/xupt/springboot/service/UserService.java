package com.xupt.springboot.service;

import com.xupt.springboot.model.User;

import java.util.List;

public interface UserService {

    /**
     * 获取用户列表
     * @return
     */
    public List<User> getUserList();

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public Integer updateUserInfo(User user);

    /**
     * 根据用户编号，获取用户信息
     * @param userId
     * @return
     */
    public User getUserInfo(Integer userId);
}
