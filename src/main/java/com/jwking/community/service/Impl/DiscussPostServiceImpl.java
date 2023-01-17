package com.jwking.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwking.community.dao.DiscussPostMapper;
import com.jwking.community.pojo.DiscussPost;
import com.jwking.community.service.DiscussPostService;
import com.jwking.community.utils.CommunityUtil;
import com.jwking.community.utils.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
@Service
public class DiscussPostServiceImpl extends ServiceImpl<DiscussPostMapper, DiscussPost> implements DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private SensitiveFilter sensitiveFilter;


    @Override
    public List<DiscussPost> listDiscussPost(Integer userId, Integer begin, Integer numbers) {
        QueryWrapper<DiscussPost> wrapper = new QueryWrapper<>();
        wrapper.ne("status",2).eq(userId != 0, "user_id", userId);
        wrapper.orderByDesc("type","create_time");
        wrapper.last("limit "+ (begin-1) * numbers + ","+numbers);
        return discussPostMapper.selectList(wrapper);
    }

    @Override
    public Integer numbersOfDiscussPost(Integer userId) {

        QueryWrapper<DiscussPost> wrapper = new QueryWrapper<>();
        wrapper.ne("status",2).eq(userId != 0, "user_id", userId);
        return discussPostMapper.selectCount(wrapper);
    }

    @Override
    public Integer addDiscussPost(DiscussPost discussPost) {
        if (discussPost == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        //转义html标记
        //假如title中存在<script>***</script>这种，将他们标识为普通文字
        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));
        //过滤敏感词
        discussPost.setTitle(sensitiveFilter.getResult(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.getResult(discussPost.getContent()));

        return discussPostMapper.insert(discussPost);
    }

    @Override
    public DiscussPost selectById(Integer id) {
        return discussPostMapper.selectById(id);
    }


}
