package com.jwking.community.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwking.community.pojo.Ticket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginTicketMapper extends BaseMapper<Ticket> {
}
