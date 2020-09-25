package com.hackathon.autoarticle.service;

import com.alibaba.fastjson.JSONObject;
import com.hackathon.autoarticle.dao.CategoryDao;
import com.hackathon.autoarticle.entity.Category;
import com.hackathon.autoarticle.vo.SubmitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by wanghuan on 2020/9/25.
 */
@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    private static Category education = new Category(8L, 3, "教育", 2);



    public List<Long> getMatchedCategory(SubmitInfo submitInfo) {
        JSONObject entityInfo = convertSubmitInfo(submitInfo);
        List<Category> allCategories = categoryDao.selectAllCategories();
        Set<Long> idSet = new HashSet<>();
        idSet.add(education.getId());

        for (String key : entityInfo.keySet()) {
            if (entityInfo.get(key) == null) {
                continue;
            }
            List<Category> matched = allCategories.stream()
                    .filter(category -> category.getName().contains(entityInfo.getString(key)))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(matched)) {
                for (Category category : matched) {
                    List<Category> categoryPath = getCategoryPath(category, allCategories, new ArrayList<>());
                    if (!CollectionUtils.isEmpty(categoryPath)) {
                        idSet.addAll(categoryPath.stream().map(Category::getId).collect(Collectors.toList()));
                    }
                }
            }
        }

        return new ArrayList<>(idSet);
    }

    public static void main(String[] args) {
        // 待处理字符串
        String wpp = "jdbc:mysql://${wpp1:raw}:${wpp2}/${wpp3}?&useSSL=false&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull";
        //\u0024\u007B\u0028\u002E\u002A\u003F\u0029}
        // 匹配方式
        Pattern p = Pattern.compile("\\$\\{(.*?)}");
        // 匹配】
        Matcher matcher = p.matcher(wpp);
        // 处理匹配到的值
        while (matcher.find()) {
            wpp = wpp.replace(matcher.group(), "raw");
            System.out.println("woo: " + matcher.group());
        }
    }

    /**
     * 递归寻找
     *
     * @param
     * @param all
     * @return
     */
    public List<Category> getCategoryPath(Category node, List<Category> all, List<Category> path) {
        if (node != null) {
            getCategoryPath(getCategoryById(node.getParent_id(), all), all, path);
            path.add(node);
        }

        return path;
    }

    private Category getCategoryById(Long id, List<Category> all) {
        Category category = null;
        for (Category node : all) {
            if (node.getId() == id) {
                return node;
            }
        }
        return category;
    }

    /**
     * 需要匹配category的表单信息
     *
     * @param submitInfo
     * @return
     */
    public JSONObject convertSubmitInfo(SubmitInfo submitInfo) {
        JSONObject entity = new JSONObject();

        entity.put("productName", submitInfo.getProduct());
        entity.put("target", submitInfo.getTarget());
        entity.put("cutPointName", submitInfo.getCutPointName());
        entity.put("cutPointIntroduction", submitInfo.getCutPointIntroduction());
        entity.put("productIntroduction", submitInfo.getProductIntro());
        entity.put("subject", submitInfo.getSubject());

        return entity;
    }

    public void test() {
        List<Category> all = new ArrayList<>();

        Category root = new Category(1L, 0, "根节点", 0);
        Category one1 = new Category(2L, 1, "1.1", 1);
        Category one2 = new Category(3L, 1, "1.2", 1);
        Category two1 = new Category(4L, 2, "2.1", 2);
        Category two2 = new Category(5L, 2, "2.2", 2);

        all.add(root);
        all.add(one1);
        all.add(one2);
        all.add(two1);
        all.add(two2);

        List<Category> categoryPath = getCategoryPath(two1, all, new ArrayList<>());
        categoryPath = null;
    }


}
