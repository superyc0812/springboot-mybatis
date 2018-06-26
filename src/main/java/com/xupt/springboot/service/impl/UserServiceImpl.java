package com.xupt.springboot.service.impl;

import com.xupt.springboot.mapper.UserMapper;
import com.xupt.springboot.model.User;
import com.xupt.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注入SpringBoot自动配置好的RedisTemplate
     */
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Transactional      //本身使用Spring的事务注解处理
    @Override
    public Integer updateUserInfo(User user) {
        Integer count = userMapper.updateByPrimaryKeySelective(user);
        System.out.println("更新结果:" + count);        //结果是 1

        //除数不能为0，所以此处会抛出一个运行时异常
        int a = 10 / 0;

        return count;
    }

    @Override
    public List<User> getUserList() {
        //字符串序列化器(将其key序列化表示为字符串形式，便于阅读)
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        //高并发情况下，此处有点问题：缓存穿透
        //使用Redis中的KV键值对查缓存
        List<User> userList = (List<User>)redisTemplate.opsForValue().get("userList");

        //缓存中有，不需要获取锁，直接返回数据，效率增加（双重检测锁）
        if(userList == null) {
            synchronized (this) {        //防止多个人同时访问userList为空，访问数据库，数据库复核严重
                //从redis获取一下
                userList = (List<User>) redisTemplate.opsForValue().get("userList");

                if (userList == null) {     //防止多个用户同时查询数据库
                    System.out.println("查询数据库......");
                    //缓存为空，查询一遍数据库
                    userList = userMapper.getUserList();
                    //将其数据库中的数据存入到Redis缓存中
                    redisTemplate.opsForValue().set("userList", userList);
                }else{
                    System.out.println("查询缓存......");
                }
            }
        }else{
            System.out.println("查询缓存......");
        }


        /*
        //有缓存穿透（多次查询数据库问题）
        if(userList == null) {
            System.out.println("查询数据库......");
            //缓存为空，查询一遍数据库
            userList = userMapper.getUserList();
            //将其数据库中的数据存入到Redis缓存中
            redisTemplate.opsForValue().set("userList", userList);
        }else{
            System.out.println("查询缓存......");
        }
        */

        return userList;
    }

    @Override
    public User getUserInfo(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
