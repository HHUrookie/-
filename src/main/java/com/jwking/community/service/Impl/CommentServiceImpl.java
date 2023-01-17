package com.jwking.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwking.community.dao.CommentMapper;
import com.jwking.community.pojo.Comment;
import com.jwking.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<Comment> findCommentByEntity(Integer entityType, Integer entityId, Integer begin, Integer numbers) {
        Integer start = begin == 0 ? 0 : (begin - 1) * numbers;
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>()
                .eq("status", 0)
                .eq("entity_type", entityType).eq("entity_id", entityId)
                .orderByAsc("create_time")
                .last("limit "+ start +","+numbers);
        return commentMapper.selectList(wrapper);
    }

    @Override
    public Integer findCommentCount(Integer entityType, Integer entityId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>()
                .eq("status", 0)
                .eq("entity_type", entityType).eq("entity_id", entityId);
        return commentMapper.selectCount(wrapper);
    }
}
