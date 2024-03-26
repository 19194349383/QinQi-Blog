package com.qinqi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qinqi.domain.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleDao extends BaseMapper<Article> {
}
