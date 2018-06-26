package com.xupt.springboot.mapper;

import com.xupt.springboot.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper     //注解@Mapper，将其该类的对象变为SpringIoC容器创建管理的对象
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    /**
     * 更新用户信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 获取用户列表
     * @return
     */
    List<User> getUserList();
}