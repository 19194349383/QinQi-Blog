package com.qinqi.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

//    @TableId(type = IdType.ASSIGN_UUID)
    private Long id;
    private long typeId;
    private String title;
    private String description;
    private String text;
    private int loves;
    private int hits;
    private int peruses;
    private String pictureUrl;
    private int comments;

    @TableField(exist = false)
    private MultipartFile picture;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
