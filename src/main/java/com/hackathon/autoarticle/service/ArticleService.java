package com.hackathon.autoarticle.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hackathon.autoarticle.dao.ArticleDao;
import com.hackathon.autoarticle.dao.CategoryDao;
import com.hackathon.autoarticle.dao.CorpusDao;
import com.hackathon.autoarticle.entity.*;
import com.hackathon.autoarticle.vo.ArticleVo;
import com.hackathon.autoarticle.vo.SubmitInfo;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    @Autowired
    CorpusService corpusService;

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

        List<Corpus> ultimateCandidate = new ArrayList<>();
        //industry
        if (! StringUtils.isNullOrEmpty(submitInfo.getIndustry())) {
            List<Category> industryCategories = categoryService.findAllSons(3L);
            ultimateCandidate = corpusService.getCandidateCorpus(industryCategories, submitInfo.getIndustry());
        }
        //object
        if (! StringUtils.isNullOrEmpty(submitInfo.getObject())) {
            List<Category> objectCategories = categoryService.findAllSons(4L);
            List<Corpus> candidate = corpusService.getCandidateCorpus(objectCategories, submitInfo.getObject());
            if (CollectionUtils.isEmpty(ultimateCandidate)) {
                ultimateCandidate = candidate;
            } else{
                ultimateCandidate.retainAll(candidate);
            }
        }
        //age
        if (! StringUtils.isNullOrEmpty(submitInfo.getAge())) {
            List<Category> ageCategories = categoryService.findAllSons(5L);
            List<Corpus> candidate = corpusService.getCandidateCorpus(ageCategories, submitInfo.getAge());
            if (CollectionUtils.isEmpty(ultimateCandidate)) {
                ultimateCandidate = candidate;
            } else{
                ultimateCandidate.retainAll(candidate);
            }
        }
        //profession
        if (! StringUtils.isNullOrEmpty(submitInfo.getProfession())) {
            List<Category> professionCategories = categoryService.findAllSons(6L);
            List<Corpus> candidate = corpusService.getCandidateCorpus(professionCategories, submitInfo.getProfession());
            if (CollectionUtils.isEmpty(ultimateCandidate)) {
                ultimateCandidate = candidate;
            } else{
                ultimateCandidate.retainAll(candidate);
            }
        }
        //subject
        if (! StringUtils.isNullOrEmpty(submitInfo.getSubject())) {
            List<Category> subjectCategories = categoryService.findAllSons(7L);
            List<Corpus> candidate = corpusService.getCandidateCorpus(subjectCategories, submitInfo.getSubject());
            if (CollectionUtils.isEmpty(ultimateCandidate)) {
                ultimateCandidate = candidate;
            } else{
                ultimateCandidate.retainAll(candidate);
            }
        }

        // 标题
        String title = matchStructure(ultimateCandidate, Structure.TITLE);
        title = replaceEntities(submitInfo, title);
        // 热点
        String hot = matchStructure(ultimateCandidate, Structure.HOT);
        hot = replaceEntities(submitInfo, hot);
        // 背景
        String background = matchStructure(ultimateCandidate, Structure.BACKGROUND);
        background = replaceEntities(submitInfo, background);
        // 观点
        String view = matchStructure(ultimateCandidate, Structure.VIEW);
        view = replaceEntities(submitInfo, view);
        // 背书
        String endorse = matchStructure(ultimateCandidate, Structure.ENDORSE);
        endorse = replaceEntities(submitInfo, endorse);
        // 推广
        String promotion = matchStructure(ultimateCandidate, Structure.PROMOTION);
        promotion = replaceEntities(submitInfo, promotion);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setTitle(title);
        articleVo.setHot(hot);
        articleVo.setBackground(background);
        articleVo.setView(view);
        articleVo.setEndorse(endorse);
        articleVo.setPromotion(promotion);

        return articleVo;
    }

    private String matchStructure(List<Corpus> allCandidate, Structure structure) {
        List<Corpus> candidate = new ArrayList<>();
        for (Corpus corpus : allCandidate) {
            if (corpus.getStructure().equalsIgnoreCase(structure.name())) {
                candidate.add(corpus);
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
