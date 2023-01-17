package com.jwking.community.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwking.community.dao.CommentMapper;
import com.jwking.community.pojo.Comment;
import com.jwking.community.pojo.DiscussPost;
import com.jwking.community.pojo.User;
import com.jwking.community.service.CommentService;
import com.jwking.community.service.DiscussPostService;
import com.jwking.community.service.Impl.UserServiceImpl;
import com.jwking.community.utils.CommunityConstant;
import com.jwking.community.utils.CommunityUtil;
import com.jwking.community.utils.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@Slf4j
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    @ResponseBody
    public String addPost(@RequestParam("title") String title,@RequestParam("content") String content) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJSONString(403, "你还没有登录，请先去登录");
        }
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId().toString());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);
        //报错的情况统一处理
        return CommunityUtil.getJSONString(0,"发布成功！");
    }

    @GetMapping("/detail/{id}/{current}")
    public String getDetail(@PathVariable("id") Integer id , Model model,@PathVariable("current") Integer current) {
        //帖子
        DiscussPost discussPost = discussPostService.selectById(id);
        model.addAttribute("post", discussPost);
        //作者
        User user = userService.getById(discussPost.getUserId());
        model.addAttribute("user", user);
        //分页
        IPage<Comment> page = new Page<>(current <=0?1:current, 5);
        IPage<Comment> usePage = commentService.page(page,new QueryWrapper<Comment>().eq("status", 0)
                .eq("entity_type", CommunityConstant.ENTITY_TYPE_POST).eq("entity_id", id));
        //评论：给帖子的评论
        //回复：给评论的回复
        //评论列表
        List<Comment> list = commentService.findCommentByEntity(CommunityConstant.ENTITY_TYPE_POST, id, current, 5);
        //评论的回显列表
        List<Map<String ,Object>> commentVolist = new ArrayList<>();
        if (list != null) {
            for (Comment comment : list) {
                Map<String,Object> map = new HashMap<>();
                //评论
                map.put("comment", comment);
                //评论的作者
                map.put("user", userService.getById(comment.getUserId()));

                //回复列表
                List<Comment> resultList = commentService.findCommentByEntity
                        (CommunityConstant.ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
                //回复的回显列表
                List<Map<String ,Object>> resultVoList = new ArrayList<>();
                if (resultList != null) {
                    for (Comment reply : resultList) {
                        Map<String,Object> replyMap = new HashMap<>();
                        replyMap.put("reply", reply);
                        replyMap.put("user", userService.getById(reply.getUserId()));
                        //回复的目标
                        User target = reply.getTargetId() == 0 ? null : userService.getById(reply.getTargetId());
                        replyMap.put("target", target);
                        resultVoList.add(replyMap);
                    }
                }
                map.put("reply", resultVoList);
                //回复数量
                Integer count = commentService.findCommentCount(CommunityConstant.ENTITY_TYPE_COMMENT, comment.getId());
                map.put("replyCount",count);
                commentVolist.add(map);
            }
        }
        model.addAttribute("comments", commentVolist);
        model.addAttribute("page", usePage);
        return "site/discuss-detail";
    }
}
