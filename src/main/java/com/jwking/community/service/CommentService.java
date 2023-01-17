package com.jwking.community.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jwking.community.pojo.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {

    List<Comment> findCommentByEntity(Integer entityType,Integer entityId,Integer begin,Integer numbers);

    Integer findCommentCount(Integer entityType,Integer entityId);
}
