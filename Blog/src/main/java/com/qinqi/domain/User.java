package com.qinqi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {

    //    @TableId(type = IdType.ASSIGN_UUID)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private int admin;

    @TableField(exist = false)
    private String verCode;
    @TableField(exist = false)
    private String varCodeKey;

}
