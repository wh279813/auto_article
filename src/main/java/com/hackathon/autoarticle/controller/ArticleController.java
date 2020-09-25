package com.hackathon.autoarticle.controller;

import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wanghuan on 2020/9/25.
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    /**
     * 获取文章列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONArray getArticleList() {
        return new JSONArray();
    }

    /**
     * 删除文章
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteArticle(@RequestParam(value = "id") Long id) {

    }

}
