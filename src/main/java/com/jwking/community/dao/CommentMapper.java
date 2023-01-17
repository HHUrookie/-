package com.jwking.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwking.community.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
