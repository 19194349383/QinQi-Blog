package com.qinqi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qinqi.dao.ArticleTypeDao;
import com.qinqi.domain.ArticleType;
import com.qinqi.service.ArticleTypeService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTypeServiceImpl extends ServiceImpl<ArticleTypeDao, ArticleType> implements ArticleTypeService {
    @Override
    public ArticleType getByType(String type) {
        QueryWrapper<ArticleType> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        wrapper.last("limit 1");
        return this.getOne(wrapper);
    }
}
