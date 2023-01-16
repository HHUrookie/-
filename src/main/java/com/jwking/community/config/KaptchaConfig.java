package com.jwking.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    public Producer kaptchaProducer() {

        DefaultKaptcha producer = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width","100");
        properties.setProperty("kaptcha.image.height","40");
        properties.setProperty("kaptcha.textproducer.font.size","40");
        properties.setProperty("kaptcha.textproducer.font.color","black");
        properties.setProperty("kaptcha.textproducer.char.length","4");
        properties.setProperty("kaptcha.textproducer.char.string","01234565789ASDFGHJKLQWERTYUIOPZXCVBNMasdfghjklqwertyuiopzxcvbnm");
        //干扰
        //干扰实现类
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
        //干扰颜色
        properties.setProperty("kaptcha.noise.color", "blue");
        //渐变色
        properties.setProperty("kaptcha.background.clear.to", "white");
        properties.setProperty("kaptcha.background.color.from", "lightGray");
        Config config = new Config(properties);
        producer.setConfig(config);
        return producer;
    }
}
