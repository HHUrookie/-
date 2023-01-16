package com.jwking.utils;

import com.jwking.community.utils.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
public class MailUtilTest {
    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    void sendMailTest() {
        mailUtil.sendMail("3100535850@qq.com", "hello,mailMessage", "welcome");

    }

    @Test
    void sendHtmlMailTest() {
        Context context = new Context();
        context.setVariable("username","yy老婆");
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);
        mailUtil.sendMail("15268243229@163.com", "爱你", content);
    }

    @Test
    void stringTest() {
        String url = "123456.png";
        String suffix = url.substring(url.lastIndexOf("."));
        System.out.println(suffix);

    }
}
