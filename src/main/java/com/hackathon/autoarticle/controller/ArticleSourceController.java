package com.hackathon.autoarticle.controller;

import com.hackathon.autoarticle.entity.ArticleSourceDetailResult;
import com.hackathon.autoarticle.entity.ArticleSourceHot;
import com.hackathon.autoarticle.entity.ArticleSourceListRequest;
import com.hackathon.autoarticle.entity.ArticleSourceListResult;
import com.hackathon.autoarticle.service.ArticleCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author minlingchao
 * @version V1.0
 * @description:
 * @date 2020/09/26 2:03 下午
 */
@RestController
@RequestMapping("api/article")
public class ArticleSourceController {

    @Autowired
    private ArticleCrawlerService crawlerService;

    @RequestMapping("list")
    public Object getArticleList(@RequestBody ArticleSourceListRequest request) {
        ArticleSourceListResult result = crawlerService.getArticleList(request);
        return result;
    }

    @RequestMapping("detail")
    public Object detail(@RequestParam String resourceGuid) {
        ArticleSourceDetailResult result = crawlerService.getArticleDetail(resourceGuid);
        return result;
    }

    @RequestMapping("hot")
    public Object hot(@RequestParam String rankingListGuid) {
        ArticleSourceHot result = crawlerService.hot(rankingListGuid);
        return result;
    }
}
