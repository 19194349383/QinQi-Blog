package com.qinqi.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TableName(value ="comment")
@Data
public class Comment implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Long userId;

    private Long replyUserId;

    private int likes;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private String comment;

    private Integer parentId;

    @TableField(exist = false)
    private List<Comment> children;

}
