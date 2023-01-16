package com.jwking.community.controller.interceptor;

import com.jwking.community.pojo.Ticket;
import com.jwking.community.pojo.User;
import com.jwking.community.service.LoginTicketService;
import com.jwking.community.service.UserService;
import com.jwking.community.utils.CookieUtil;
import com.jwking.community.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor{

    @Autowired
    private LoginTicketService loginTicketService;
    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = CookieUtil.getValue(request, "ticket");
        if (ticket != null) {
            Ticket loginTicket = loginTicketService.findLoginTicket(ticket);
            //凭证必须有效
            if (loginTicket!=null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())){
                //根据凭证来查询用户
                User user = userService.getById(loginTicket.getUserId());
                //在本次请求中持有用户
                hostHolder.setUser(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            modelAndView.addObject("loginUser", user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
