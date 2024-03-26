package com.qinqi.dto;

import com.qinqi.domain.Article;
import com.qinqi.domain.ArticleType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleDto extends Article {

    private String articleType;



}
