package com.hackathon.autoarticle.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hackathon.autoarticle.dao.ArticleDao;
import com.hackathon.autoarticle.dao.CorpusDao;
import com.hackathon.autoarticle.entity.Article;
import com.hackathon.autoarticle.entity.Category;
import com.hackathon.autoarticle.entity.Corpus;
import com.hackathon.autoarticle.entity.Structure;
import com.hackathon.autoarticle.vo.ArticleVo;
import com.hackathon.autoarticle.vo.SubmitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wanghuan on 2020/9/25.
 */
@Service
public class ArticleService {

    @Autowired
    ArticleDao articleDao;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CorpusDao corpusDao;

    public List<Article> selectAll() {
        return articleDao.selectAll();
    }

    public int deleteById(long id) {
        return articleDao.deleteById(id);
    }

    public int insert(Article article) {
        return articleDao.insert(article);
    }

    public ArticleVo generateArticle(SubmitInfo submitInfo) {

        // step1. 根据提交信息匹配标签
        List<Long> categoryIds = categoryService.getMatchedCategory(submitInfo);

        // step2. 由标签找最匹配文章结构
        List<Corpus> corpuses = getAllCorpuses();
        // 标题
        String title = matchStructure(categoryIds, corpuses, Structure.TITLE);
        // 热点
        String hot = matchStructure(categoryIds, corpuses, Structure.HOT);
        // 背景
        String background = matchStructure(categoryIds, corpuses, Structure.BACKGROUND);
        // 观点
        String view = matchStructure(categoryIds, corpuses, Structure.VIEW);
        // 背书
        String endorse = matchStructure(categoryIds, corpuses, Structure.ENDORSE);
        // 推广
        String promotion = matchStructure(categoryIds, corpuses, Structure.PROMOTION);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setTitle(title);
        articleVo.setHot(hot);
        articleVo.setBackground(background);
        articleVo.setView(view);
        articleVo.setEndorse(endorse);
        articleVo.setPromotion(promotion);

        return articleVo;
    }

    private List<Corpus> getAllCorpuses() {
        return corpusDao.selectAll();
    }


    private String matchStructure(List<Long> categories, List<Corpus> corpuses, Structure structure) {
        for (Corpus corpus : corpuses) {
            if (corpus.getStructure().equalsIgnoreCase(structure.name())) {
                List<String> categoryIds = Arrays.asList(corpus.getCategories().split(","));
                for (Long categoryId : categories) {
                    if (categoryIds.contains(Long.toString(categoryId))) {
                        return corpus.getContent();
                    }
                }
            }
        }
        return "未匹配到";
    }

    private String replaceEntities(SubmitInfo submitInfo, String corpus) {
        JSONObject info = JSON.parseObject(JSON.toJSONString(submitInfo));
        for (String key : info.keySet()) {
            corpus = corpus.replace("${" + key + "}", info.getString(key));
        }
        return corpus;
    }

}
