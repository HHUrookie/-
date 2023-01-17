package com.jwking.community.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comment")
public class Comment {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    //被评论的类型
    private Integer entityType;
    //被评论的类型的id
    private Integer entityId;
    //给某一个人进行回复，那个人的id
    private Integer targetId;
    //状态  0 正常  1 屏蔽
    private Integer status;
    private String content;
    private Date createTime;

}
