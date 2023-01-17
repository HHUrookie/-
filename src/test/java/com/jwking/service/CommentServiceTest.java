package com.jwking.service;

import com.jwking.community.pojo.Comment;
import com.jwking.community.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Test
    void selectTest() {
        List<Comment> list = commentService.findCommentByEntity(1, 228, 0,5);
        System.out.println(list);
    }
}
