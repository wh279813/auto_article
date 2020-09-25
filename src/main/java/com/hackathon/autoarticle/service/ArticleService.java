package com.hackathon.autoarticle.service;

import com.hackathon.autoarticle.dao.ArticleDao;
import com.hackathon.autoarticle.entity.Article;
import com.hackathon.autoarticle.entity.Category;
import com.hackathon.autoarticle.entity.Corpus;
import com.hackathon.autoarticle.vo.ArticleVo;
import com.hackathon.autoarticle.vo.SubmitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghuan on 2020/9/25.
 */
@Service
public class ArticleService {

    @Autowired
    ArticleDao articleDao;

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
        List<Category> categories = getMatchedCategory(submitInfo);


        // step2. 由标签找最匹配文章结构
        List<Corpus> corpuses = getAllCorpuses();
        // 标题
        String title = matchTitle(categories, corpuses);


        ArticleVo articleVo = new ArticleVo();

        return articleVo;
    }

    private List<Category> getMatchedCategory(SubmitInfo submitInfo) {
        // todo
        return new ArrayList<>();
    }

    private List<Corpus> getAllCorpuses() {
        // todo
        return new ArrayList<>();
    }

    private String matchTitle(List<Category> categories, List<Corpus> corpuses) {
        return "";
    }


}
