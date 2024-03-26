package com.qinqi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinqi.domain.Article;
import com.qinqi.dto.ArticleDto;
import com.qinqi.service.ArticleService;
import com.qinqi.service.ArticleTypeService;
import com.qinqi.utils.CosUtils;
import com.qinqi.utils.R;
import com.qinqi.utils.UUid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping
    public R<String> save(ArticleDto article) {
        if (article.getPicture() == null) {
            return R.error("文章图片还未上传");
        }
        article.setTypeId(articleTypeService.getByType(article.getArticleType()).getId());
        String url = CosUtils.upLoadFile(article.getPicture(), "ArticlePicture/");
        if (url == null) {
            return R.error("图片上传失败了，请重试");
        }
        article.setId(UUid.getUUid());
        article.setPictureUrl(url);
        articleService.save(article);
        return R.success("添加文章成功");
    }

    @PutMapping
    public R<String> updateById(ArticleDto article) {
        if (article.getPicture() != null) {
            article.setPictureUrl(CosUtils.upLoadFile(article.getPicture(), "ArticlePicture/"));
        }

        if (articleService.getById(article.getId()) == null) {
            return R.error("修改失败，该id对应的对象不存在");
        }
        if (article.getArticleType() != null) {
            article.setTypeId(articleTypeService.getByType(article.getArticleType()).getId());
        }
        articleService.updateById(article);
        return R.success("修改成功");
    }

    @DeleteMapping("/{id}")
    public R<String> deleteById(@PathVariable Long id) {
        Article article = articleService.getById(id);
        if (article == null) {
            return R.error("删除失败，该对象不存在");
        }
        String url = article.getPictureUrl();
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        String s = CosUtils.deleteFile(fileName, "ArticlePicture/");
        articleService.removeById(id);
        if (s.equals("删除失败")) {
            return R.success("文章图片删除失败，请手动清理");
        }
        return R.success("删除成功");
    }

    @GetMapping("/page/{index}")
    public R<Page> page(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("type") String type, @PathVariable int index) {
        Page<Article> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //查询指定字段
        wrapper.select(Article::getId, Article::getTitle, Article::getDescription,
                Article::getPictureUrl, Article::getCreateTime,
                Article::getUpdateTime, Article::getLoves, Article::getHits,
                Article::getPeruses, Article::getComments, Article::getTypeId);

        if (!type.equals("null")) {
            wrapper.eq(Article::getTypeId, articleTypeService.getByType(type).getId());
        }
        switch (index) {
            case 1 -> wrapper.orderByDesc(Article::getCreateTime);
            case 2 -> wrapper.orderByDesc(Article::getHits);
            case 3 -> wrapper.orderByDesc(Article::getPeruses);
            case 4 -> wrapper.orderByDesc(Article::getLoves);
            default -> wrapper.orderByDesc(Article::getCreateTime);
        }
        articleService.page(pageInfo, wrapper);
        return R.success(pageInfo);
    }

    @PostMapping("/{title}")
    public R<List<Article>> likes(@PathVariable String title) {

            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();

            wrapper.like(Article::getTitle,title);
            wrapper.select(Article::getId, Article::getTitle, Article::getDescription,
                    Article::getPictureUrl, Article::getCreateTime,
                    Article::getUpdateTime, Article::getLoves, Article::getHits,
                    Article::getPeruses, Article::getComments, Article::getTypeId);

            List<Article> articles = articleService.list(wrapper);
            return R.success(articles);

    }

    @GetMapping("/{id}")
    public R<String> getTextById(@PathVariable Long id) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getId, id);
        Article article = articleService.getOne(wrapper);
        if (article == null) {
            return R.error("该文章不存在！");
        }
        return R.success(article.getText());
    }

}
