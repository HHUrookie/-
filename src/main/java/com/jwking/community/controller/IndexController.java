package com.jwking.community.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwking.community.pojo.DiscussPost;
import com.jwking.community.pojo.User;
import com.jwking.community.service.DiscussPostService;
import com.jwking.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;
    @GetMapping
    public void getIndexPage(Model model) {
        getIndexPage(model,1);
    }
    @GetMapping("/{current}")
    public String getIndexPage(Model model ,@PathVariable Integer current) {
        IPage<DiscussPost> page = new Page<>(current<=0?1:current,10);
        IPage<DiscussPost> usePage = discussPostService.page(page);
        List<DiscussPost> list = discussPostService.listDiscussPost(0, (int)usePage.getCurrent(),10);
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost discussPost : list) {
                Map<String,Object> map = new HashMap<>();
                map.put("post",discussPost);
                User user = userService.getById(discussPost.getUserId());
                map.put("user" ,user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts );
        model.addAttribute("page",usePage );
        return "index";
    }

}
