package com.qinqi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinqi.domain.Article;
import com.qinqi.domain.ArticleType;
import com.qinqi.service.ArticleService;
import com.qinqi.service.ArticleTypeService;
import com.qinqi.utils.R;
import com.qinqi.utils.UUid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private ArticleService articleService;

    public ArticleType selectByType(String articleType) {
        LambdaQueryWrapper<ArticleType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleType::getType, articleType);
        return articleTypeService.getOne(wrapper);
    }

    //添加文章类型
    @PostMapping
    public R<String> save(@RequestBody ArticleType articleType) {
        if (selectByType(articleType.getType()) != null) {
            return R.error("添加失败，该类型已存在");
        }
        articleType.setId(UUid.getUUid());
        articleTypeService.save(articleType);
        return R.success("添加成功!");
    }

    //更新文章类型
    @PutMapping
    public R<String> updateById(@RequestBody ArticleType articleType) {
        ArticleType type = articleTypeService.getById(articleType.getId());
        if (type == null) {
            return R.error("修改失败,该信息不存在");
        }
        articleTypeService.updateById(articleType);
        return R.success("修改成功！");
    }

    //分页查询 文章类型
    @GetMapping("/page")
    public R<Page> page(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        Page<ArticleType> pageInfo = new Page<>(page, pageSize);
        articleTypeService.page(pageInfo);
        return R.success(pageInfo);
    }

    @GetMapping
    public R<List<ArticleType>> getAll() {
        List<ArticleType> list = articleTypeService.list();
        return R.success(list);
    }


    //删除文章类型
    @DeleteMapping("/{id}")
    public R<String> deleteById(@PathVariable Long id) {
        List<Article> list = articleService.getByTypeId(id);
        if (!list.isEmpty()) {
            return R.error("删除失败，存在文章包含该类型，请先修改文章设置再操作");
        }
        if (articleTypeService.getById(id) == null) {
            return R.error("删除失败，该对象不存在");
        }

        articleTypeService.removeById(id);
        return R.success("删除成功");
    }

}
