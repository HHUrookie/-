package com.jwking.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jwking.community.pojo.DiscussPost;

import java.util.List;

public interface DiscussPostService extends IService<DiscussPost> {

    List<DiscussPost> listDiscussPost(Integer userId, Integer begin, Integer numbers);

    Integer numbersOfDiscussPost(Integer userId);


}
