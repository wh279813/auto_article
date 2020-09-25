package com.hackathon.autoarticle.controller;

import com.alibaba.fastjson.JSONArray;
import com.hackathon.autoarticle.dao.CategoryDao;
import com.hackathon.autoarticle.entity.Category;
import com.hackathon.autoarticle.service.ArticleService;
import com.hackathon.autoarticle.vo.SubmitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wanghuan on 2020/9/25.
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryDao categoryDao;

    /**
     * 获取文章列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONArray getArticleList() {

        Category category = categoryDao.selectById(1L);

        System.out.printf(category.toString());



        return new JSONArray();
    }

    /**
     * 删除文章
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteArticle(@RequestParam(value = "id") Long id) {

        return "Success";
    }

    /**
     * 生成文章
     *
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String deleteArticle(@RequestBody SubmitInfo submitInfo) {
        return "Success";
    }

    /**
     * hello world
     *
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloWorld() {
        return "hello world";
    }

}
