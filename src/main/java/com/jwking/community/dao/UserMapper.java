package com.jwking.community.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwking.community.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
