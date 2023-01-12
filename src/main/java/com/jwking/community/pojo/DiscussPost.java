package com.jwking.community.pojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("discuss_post")
public class DiscussPost {

    private Integer id;
    private String userId;
    private String title;
    private String content;
    private Integer type;
    private Integer status;
    private Date createTime;
    private Integer commentCount;
    private Double score;

}
