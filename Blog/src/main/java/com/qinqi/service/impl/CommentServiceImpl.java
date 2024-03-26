package com.qinqi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qinqi.dao.CommentDao;
import com.qinqi.domain.Comment;
import com.qinqi.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {


    @Override
    public List<Comment> listTree() {
        List<Comment> list = this.list();
        //映射id -> index
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int index = 0; index < list.size(); index++) {
            map.put(list.get(index).getId(), index);
        }
        // 遍历寻找父节点
        for (Comment comment : list) {
            Integer parentId = comment.getParentId();
            //存在父节点
            if (parentId != null) {
                //获取父节点id
                Integer indexFather = map.get(parentId);
                Comment father = list.get(indexFather);
                if (father.getChildren() == null) {
                    father.setChildren(new ArrayList<>());
                }
                //在父亲节点上添加当前节点
                father.getChildren().add(comment);
            }
        }

        //过滤出一级节点
        List<Comment> collect = list.stream().filter(child -> child.getParentId() == null).collect(Collectors.toList());

        return collect;
    }
}
