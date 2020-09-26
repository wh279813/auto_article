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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        List<Corpus> ultimateCandidate = corpusDao.selectAll();

        // 匹配标签
        JSONObject infoObj = JSON.parseObject(JSON.toJSONString(submitInfo));
        for (CategoryEnum categoryEnum : CategoryEnum.values()) {
            if (!StringUtils.isEmpty(infoObj.getString(categoryEnum.toString()))) {
                List<Corpus> Corpuses = corpusService.getCandidateCorpus(
                        categoryService.findAllSons(categoryEnum.getRelatedCategory()), submitInfo.getIndustry()
                );

                ultimateCandidate.retainAll(Corpuses);
            }
        }

        // 标题
        String title = matchStructure(submitInfo, ultimateCandidate, Structure.TITLE);
        // 热点
        String hot = matchStructure(submitInfo, ultimateCandidate, Structure.HOT);
        // 背景
        String background = matchStructure(submitInfo, ultimateCandidate, Structure.BACKGROUND);
        // 观点
        String view = matchStructure(submitInfo, ultimateCandidate, Structure.VIEW);
        // 背书
        String endorse = matchStructure(submitInfo, ultimateCandidate, Structure.ENDORSE);
        // 推广
        String promotion = matchStructure(submitInfo, ultimateCandidate, Structure.PROMOTION);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setTitle(title);
        articleVo.setHot(hot);
        articleVo.setBackground(background);
        articleVo.setView(view);
        articleVo.setEndorse(endorse);
        articleVo.setPromotion(promotion);

        return articleVo;
    }

    private String matchStructure(SubmitInfo submitInfo, List<Corpus> allCandidate, Structure structure) {
        List<Corpus> candidate = new ArrayList<>();
        for (Corpus corpus : allCandidate) {
            if (corpus.getStructure().equalsIgnoreCase(structure.name())) {
                candidate.add(corpus);
            }
        }
        System.out.println("candidate corpus is: " + JSON.toJSONString(candidate));
        if (candidate.size() > 0) {
            Random random = new Random();
            String corpus = candidate.get(random.nextInt(candidate.size())).getContent();
            return replaceEntities(submitInfo, corpus);
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
            List<String> keyValuePair = Arrays.asList(matcher.group().substring(2, matcher.group().length() - 1).split(":"));
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
