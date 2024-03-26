package com.qinqi.controller;

import com.qinqi.domain.Comment;
import com.qinqi.service.CommentService;
import com.qinqi.utils.R;
import com.qinqi.utils.UUid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //返回树形结构评论数据
    @GetMapping("/list")
    public R<List> list() {
        List<Comment> list = commentService.list();
        return R.success(list);
    }

    //添加评论
    @PostMapping("/save")
    public R<String> save(@RequestBody Comment comment){
        comment.setId(Math.toIntExact(UUid.getUUid()));
        commentService.save(comment);
        return R.success("发表成功");
    }
}
