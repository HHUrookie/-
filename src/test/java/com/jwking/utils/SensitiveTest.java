package com.jwking.utils;


import com.jwking.community.utils.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SensitiveTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;


    @Test
    void setSensitiveFilterTest() {

        String text ="一起来❗嫖娼❗，❗吸❗毒❗哈哈哈";
        String result = sensitiveFilter.getResult(text);
        System.out.println(result);
    }


}
