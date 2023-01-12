package com.jwking.service;

import com.jwking.community.pojo.DiscussPost;
import com.jwking.community.service.DiscussPostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DiscussPostServiceTest {

    @Autowired
    private DiscussPostService discussPostService;
    @Test
    void test() {
        List<DiscussPost> list = discussPostService.listDiscussPost(103, 0, 5);
        list.forEach(System.out::println);
    }
}
