package com.jwking.community.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("login_ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String ticket;
    private Integer status;
    //到期的日期
    private Date expired;
}
