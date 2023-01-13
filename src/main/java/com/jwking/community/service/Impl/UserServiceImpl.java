package com.jwking.community.service.Impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwking.community.dao.UserMapper;
import com.jwking.community.pojo.User;
import com.jwking.community.service.UserService;
import com.jwking.community.utils.CommunityConstant;
import com.jwking.community.utils.CommunityUtil;
import com.jwking.community.utils.MailUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${community.path.domain}")
    //域名
    private String domain;

    @Value("server.servlet.context-path")
    //项目路径
    private String contextPath;

    /**
     * 注册功能
     * @param user 用户
     * @return 返回信息
     */

    public Map<String,Object> register(User user) {
        Map<String,Object> map = new HashMap<>();
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameError", "账号不能为空！");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordError", "密码不能为空！");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailError", "邮箱不能为空！");
            return map;
        }
        //验证账号
        User user1 = userMapper.selectOne(new QueryWrapper<User>().eq("username",user.getUsername()));
        if (user1 != null) {
            map.put("usernameError", "账号已经存在！");
            return map;
        }
        //邮箱的验证
        user1 = userMapper.selectOne(new QueryWrapper<User>().eq("email", user.getEmail()));
        if (user1 != null) {
            map.put("emailError", "邮箱已经存在！");
            return map;
        }
        //注册用户
        user.setSalt(CommunityUtil.getRandomString().substring(0,5));
        user.setPassword(CommunityUtil.getMD5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.getRandomString());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insert(user);
        //发邮件
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        String url = domain+"/community/activation/"+user.getId()+"/"+user.getActivationCode();
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation", context);
        mailUtil.sendMail(user.getEmail(), "激活账号邮件", content);
        return map;
    }

    /**
     * 激活
     */
    public Integer activation(Integer userId,String activationCode) {


        User user = userMapper.selectById(userId);

        if (user.getStatus() == 1) {
            return CommunityConstant.ACTIVATION_REPEAT;
        } else if(activationCode.equals(user.getActivationCode())) {
            userMapper.update(null,new UpdateWrapper<User>().eq("id", user.getId()).set("status", 1));
            return CommunityConstant.ACTIVATION_SUCCESS;
        } else {
            return CommunityConstant.ACTIVATION_FAIL;
        }

    }
}
