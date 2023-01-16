package com.jwking.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jwking.community.pojo.Ticket;

import java.util.Map;

public interface LoginTicketService extends IService<Ticket> {



    Map<String,Object> login(String username, String password, Integer expiredSeconds);


    void logout(String ticket);

    Ticket findLoginTicket(String ticket);
}
