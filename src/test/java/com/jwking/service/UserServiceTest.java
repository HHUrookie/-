package com.jwking.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwking.community.pojo.User;
import com.jwking.community.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    void queryTest() {

    }
}
