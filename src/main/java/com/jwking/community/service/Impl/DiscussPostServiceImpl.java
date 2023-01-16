package com.jwking.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwking.community.dao.DiscussPostMapper;
import com.jwking.community.pojo.DiscussPost;
import com.jwking.community.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class DiscussPostServiceImpl extends ServiceImpl<DiscussPostMapper, DiscussPost> implements DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;


    @Override
    public List<DiscussPost> listDiscussPost(Integer userId, Integer begin, Integer numbers) {
        QueryWrapper<DiscussPost> wrapper = new QueryWrapper<>();
        wrapper.ne("status",2).eq(userId != 0, "user_id", userId);
        wrapper.orderByDesc("type");
        wrapper.last("limit "+begin+","+numbers);
        return discussPostMapper.selectList(wrapper);
    }

    @Override
    public Integer numbersOfDiscussPost(Integer userId) {

        QueryWrapper<DiscussPost> wrapper = new QueryWrapper<>();
        wrapper.ne("status",2).eq(userId != 0, "user_id", userId);
        return discussPostMapper.selectCount(wrapper);
    }
}
