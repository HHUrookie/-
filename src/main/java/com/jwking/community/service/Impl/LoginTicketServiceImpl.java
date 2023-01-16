package com.jwking.community.service.Impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwking.community.dao.LoginTicketMapper;
import com.jwking.community.dao.UserMapper;
import com.jwking.community.pojo.Ticket;
import com.jwking.community.pojo.User;
import com.jwking.community.service.LoginTicketService;
import com.jwking.community.utils.CommunityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginTicketServiceImpl extends ServiceImpl<LoginTicketMapper,Ticket> implements LoginTicketService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Override
    public Map<String, Object> login(String username, String password, Integer expiredSeconds) {
        Map<String,Object> map = new HashMap<>();

        if (StringUtils.isBlank(username)) {
            map.put("usernameError", "账号不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordError", "密码不能为空！");
            return map;
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            map.put("usernameError", "账号不存在！");
            return map;
        }
        if (user.getStatus() == 0) {
            map.put("usernameError", "账号未激活！");
            return map;
        }
        password = CommunityUtil.getMD5(password+user.getSalt());

        if (!user.getPassword().equals(password)) {
            map.put("passwordError", "密码不正确！");
            return map;
        }

        Date date = new Date(System.currentTimeMillis() + expiredSeconds * 1000);
        Ticket ticket = new Ticket(null,user.getId(),CommunityUtil.getRandomString(),0,date);
        loginTicketMapper.insert(ticket);
        map.put("ticket", ticket.getTicket());
        return map;
    }

    @Override
    public void logout(String ticket) {
        loginTicketMapper.update(null,new UpdateWrapper<Ticket>().eq("ticket", ticket).set("status", 1));
    }

    @Override
    public Ticket findLoginTicket(String ticket) {
        return loginTicketMapper.selectOne(new QueryWrapper<Ticket>().eq("ticket", ticket));
    }
}
