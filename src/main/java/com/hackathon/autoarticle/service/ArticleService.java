package com.hackathon.autoarticle.service;

import com.hackathon.autoarticle.dao.ArticleDao;
import com.hackathon.autoarticle.dao.CorpusDao;
import com.hackathon.autoarticle.entity.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hackathon.autoarticle.dao.CategoryDao;
import com.hackathon.autoarticle.vo.ArticleVo;
import com.hackathon.autoarticle.vo.SubmitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wanghuan on 2020/9/25.
 */
@Service
public class ArticleService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CorpusDao corpusDao;

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
        String title = matchStructure(categories, corpuses, Structure.TITLE);
        // 热点
        String hot = matchStructure(categories, corpuses, Structure.HOT);
        // 背景
        String background = matchStructure(categories, corpuses ,Structure.BACKGROUND);
        // 观点
        String view = matchStructure(categories, corpuses, Structure.VIEW);
        // 背书
        String endorse = matchStructure(categories, corpuses, Structure.ENDORSE);
        // 推广
        String promotion = matchStructure(categories, corpuses, Structure.PROMOTION);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setTitle(title);
        articleVo.setHot(hot);
        articleVo.setBackground(background);
        articleVo.setView(view);
        articleVo.setEndorse(endorse);
        articleVo.setPromotion(promotion);

        return articleVo;
    }

    private List<Category> getMatchedCategory(SubmitInfo submitInfo) {
        JSONObject entityInfo = convertSubmitInfoToEntity(submitInfo);
        List<Category> allCategories = categoryDao.selectAllCategories();
        Set<Long> categoryIds = new HashSet<>();

        for (String key : entityInfo.keySet()) {
            List<Category> matched = allCategories.stream()
                    .filter(category -> category.getName().contains(entityInfo.getString(key)))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(matched)) {
                for (Category category : matched) {

                }
            }
        }

        return new ArrayList<>();
    }

    /**
     * 递归寻找
     *
     * @param
     * @param all
     * @return
     */
    private List<Category> getCategoryPath(Category node, List<Category> all, List<Category> path) {
        Category category = all.stream().filter(c -> c.getId().e
        );

        if (category != null) {
            getCategoryPath(category.getParent_id(), all, path);
            path.add(category);
        }

        return path;
    }



    private List<Corpus> getAllCorpuses() {
        return corpusDao.selectAll();
    }


    private String matchStructure(List<Category> categories, List<Corpus> corpuses, Structure structure) {
        for (Corpus corpus : corpuses) {
            if (corpus.getStructure().equalsIgnoreCase(structure.name())) {
                List<String> categoryIds = Arrays.asList(corpus.getCategories().split(",")) ;
                for (Category category : categories) {
                    if (categoryIds.contains(Long.toString(category.getId()))) {
                        return corpus.getContent();
                    }
                }
            }
        }
        return "未匹配到";
    }

    private String matchTitle(List<Category> categories, List<Corpus> corpuses) {
        return "标题";
    }

    private String matchHot(List<Category> categories, List<Corpus> corpuses) {
        return "热点";
    }

    private String matchBackground(List<Category> categories, List<Corpus> corpuses) {
        return "背景";
    }

    private String matchView(List<Category> categories, List<Corpus> corpuses) {
        return "观点";
    }

    private String matchEndorse(List<Category> categories, List<Corpus> corpuses) {
        return "背书";
    }

    private String matchPromotion(List<Category> categories, List<Corpus> corpuses) {
        return "推广";
    }

    private String replaceEntities(SubmitInfo submitInfo, String corpus) {
        JSONObject info = JSON.parseObject(JSON.toJSONString(submitInfo));
        for (String key : info.keySet()) {
            corpus = corpus.replace("${" + key + "}", info.getString(key));
        }
        return corpus;
    }

    private JSONObject convertSubmitInfoToEntity(SubmitInfo submitInfo) {
        return new JSONObject();
    }

}
