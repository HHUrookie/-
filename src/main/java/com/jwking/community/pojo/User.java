package com.jwking.community.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Documented;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")

public class User {
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String email;
    /**
     * 用户类型，0：普通用户 1：超级用户 2：版主====>一版之主的意思，主要负责管理论坛的某些版块
     */
    private Integer type;
    /**
     * 状态，0：未激活 1：已激活
     */
    private Integer status;
    /**
     * 激活码
     */
    private String activationCode;
    /**
     * 头像地址
     */
    private String headerUrl;
    /**
     * 创建时间
     */
    private Date createTime;

}