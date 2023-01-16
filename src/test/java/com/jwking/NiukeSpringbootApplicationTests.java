package com.jwking;

import com.jwking.community.utils.CommunityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NiukeSpringbootApplicationTests {

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Test
    void contextLoads() {

        System.out.println(domain);
        System.out.println(contextPath);
        System.out.println(uploadPath);
        String md5 = CommunityUtil.getMD5("123456" + "2fe31");
        System.out.println(md5);
    }

}
