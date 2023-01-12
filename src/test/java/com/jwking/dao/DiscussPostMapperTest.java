package com.jwking.dao;


import com.jwking.community.dao.DiscussPostMapper;
import com.jwking.community.pojo.DiscussPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DiscussPostMapperTest {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    void queryTest() {
        DiscussPost discussPost = discussPostMapper.selectById(109);
        System.out.println(discussPost);
    }

    @Test
    void listTest() {
        List<DiscussPost> discussPosts = discussPostMapper.selectList(103, 2, 4);
        System.out.println(discussPosts.size());
    }

    @Test
    void numberTest() {
        Integer numbers = discussPostMapper.selectNumbers(103);
        System.out.println(numbers);
    }
}
