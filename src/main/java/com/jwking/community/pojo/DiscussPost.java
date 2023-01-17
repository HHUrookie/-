package com.jwking.community.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(type= IdType.AUTO)
    private Integer id;
    private String userId;
    //标题
    private String title;
    //内容
    private String content;
    //类型   0==>普通  1==>置顶
    private Integer type = 0;
    //状态   0==>正常  1==>精华  2==>拉黑
    private Integer status = 0;
    private Date createTime;
    //评论数量
    private Integer commentCount;
    private Double score;

}
