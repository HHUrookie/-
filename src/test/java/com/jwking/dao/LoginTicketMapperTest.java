package com.jwking.dao;

import com.jwking.community.dao.LoginTicketMapper;
import com.jwking.community.pojo.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class LoginTicketMapperTest {
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    void insertTest() {

        Ticket ticket = new Ticket(null,103,"abc",0,new Date());
        loginTicketMapper.insert(ticket);
    }
}
