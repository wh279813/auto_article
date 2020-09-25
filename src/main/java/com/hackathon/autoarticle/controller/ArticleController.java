package com.hackathon.autoarticle.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hackathon.autoarticle.dao.CategoryDao;
import com.hackathon.autoarticle.service.ArticleService;
import com.hackathon.autoarticle.vo.ArticleVo;
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

    /**
     * 获取文章列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONArray getArticleList() {
        JSONArray jsonArray = new JSONArray();
        JSONObject article = new JSONObject();
        article.put("id", 1);
        article.put("title", "标题");
        article.put("industry", "行业");
        article.put("template", "模版");
        article.put("createTime", System.currentTimeMillis());

        jsonArray.add(article);
        return jsonArray;
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
    public ArticleVo generateArticle(@RequestBody SubmitInfo submitInfo) {
        return articleService.generateArticle(submitInfo);
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
