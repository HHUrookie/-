package com.jwking.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwking.community.pojo.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface DiscussPostMapper extends BaseMapper<DiscussPost> {

    List<DiscussPost> selectList(@Param("userId") Integer userId,@Param("begin") Integer begin,@Param("numbers") Integer numbers);

    Integer selectNumbers(@Param("userId") Integer userId);
}
