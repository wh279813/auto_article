package com.hackathon.autoarticle.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hackathon.autoarticle.dao.ArticleDao;
import com.hackathon.autoarticle.dao.CategoryDao;
import com.hackathon.autoarticle.dao.CorpusDao;
import com.hackathon.autoarticle.entity.*;
import com.hackathon.autoarticle.vo.ArticleVo;
import com.hackathon.autoarticle.vo.SubmitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;

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
    @Autowired
    CategoryDao categoryDao;

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
        List<Corpus> candidate = new ArrayList<>();
        for (Corpus corpus : corpuses) {
            if (corpus.getStructure().equalsIgnoreCase(structure.name())) {
                String[] categoryIds = corpus.getCategories().split(",");
                List<Category> categoryList = new ArrayList<>();
                for (int i = 0; i < categoryIds.length; i++) {
                   categoryService.getCategoryPath(new Category(Long.parseLong(categoryIds[i])),
                           categoryDao.selectAllCategories(), categoryList);
                }
                List<Long> allCategoryId = new ArrayList<>();
                for (Category category : categoryList) {
                    allCategoryId.add(category.getId());
                }
                if (allCategoryId.containsAll(categories)) {
                    candidate.add(corpus);
                }
            }
        }
        System.out.println("candidate corpus is: " + JSON.toJSONString(candidate));
        if (candidate.size() > 0) {
            Random random = new Random();
            return candidate.get(random.nextInt(candidate.size())).getContent();
        }

        return "未匹配到";
    }

    private static String replaceEntities(SubmitInfo submitInfo, String corpus) {
        JSONObject info = JSON.parseObject(JSON.toJSONString(submitInfo));
        JSONObject entityObject = new JSONObject();
        for (String key : info.keySet()) {
            if (EntityEnum.contains(key)) {
                entityObject.put(key, info.getString(key));
            }
        }

        Pattern p = Pattern.compile("\\$\\{(.*?)}");
        Matcher matcher = p.matcher(corpus);
        // 处理匹配到的值
        while (matcher.find()) {
            List<String> keyValuePair = Arrays.asList(matcher.group().substring(2, matcher.group().length() -1).split(":"));
            String entityKey = keyValuePair.get(0);
            String rawValue = keyValuePair.get(1);

            corpus = corpus.replace(matcher.group(), entityObject.get(entityKey) == null ? rawValue : entityObject.getString(entityKey));
        }

        return corpus;
    }

    public static void main(String[] args) {
        SubmitInfo submitInfo = new SubmitInfo();
        submitInfo.setProduct("产品");


        replaceEntities(submitInfo, "这是个很好的${product:电脑}。");
    }



}
