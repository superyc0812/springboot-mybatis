package com.xupt.springboot.controller;

import com.xupt.springboot.model.User;
import com.xupt.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SpringBoot整合MyBatis框架的控制器Controller
 */
@RestController
public class MyBatisController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping(value = "/boot/getUserList")
    public Object getUserList(){

        //创建线程，该线程调用底层查询所有学生的方法
        Runnable  runnable = new Runnable() {
            @Override
            public void run() {
                userService.getUserList();
            }
        };

        //创建线程池，多线程测试一下缓存穿透问题
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        for (int i=0;i<100;i++){  //模拟发送10000此请求
            executorService.submit(runnable);
        }

        return userService.getUserList();
    }

    /**
     * 更新用户信息
     * @return
     */
    @GetMapping(value = "/boot/update")
    public Object update(){
        User user = new User();
        user.setUserId(1);
        user.setUserName("张三-updaue");
        user.setGender("女");
        return userService.updateUserInfo(user);
    }
}
