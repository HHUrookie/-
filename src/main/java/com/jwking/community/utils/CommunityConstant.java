package com.jwking.community.utils;

/**
 * 定义一些常量
 */
public interface CommunityConstant {

    /**
     * 激活成功
     */
    Integer ACTIVATION_SUCCESS = 0;
    /**
     * 重复激活
     */
    Integer ACTIVATION_REPEAT = 1;
    /**
     * 激活失败
     */
    Integer ACTIVATION_FAIL = 2;

    /**
     * 默认状态下的登录凭证超时时间 ==12h
     */
    Integer DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * 记住我 下的超时时间   ==   50天
     */
     Integer REMEMBER_EXPIRED_SECONDS = 3600 * 12 * 100;
}
