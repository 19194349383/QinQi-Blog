package com.qinqi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleType implements Serializable {

    private static final long serialVersionUID = 1L;

//    @TableId(type = IdType.ASSIGN_UUID)
    private Long id;
    private String type;

}
