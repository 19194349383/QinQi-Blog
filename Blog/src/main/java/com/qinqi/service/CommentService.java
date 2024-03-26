package com.qinqi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qinqi.domain.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {

    //返回树形评论数据
    List<Comment> listTree();

}
