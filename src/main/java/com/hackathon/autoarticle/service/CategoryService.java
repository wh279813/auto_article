package com.hackathon.autoarticle.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hackathon.autoarticle.dao.CategoryDao;
import com.hackathon.autoarticle.entity.Category;
import com.hackathon.autoarticle.entity.EntityEnum;
import com.hackathon.autoarticle.vo.SubmitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wanghuan on 2020/9/25.
 */
@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    public List<Category> getMatchedCategory(SubmitInfo submitInfo) {
        JSONObject entityInfo = convertSubmitInfoToEntity(submitInfo);
        List<Category> allCategories = categoryDao.selectAllCategories();
        Set<Long> categoryIds = new HashSet<>();

        for (String key : entityInfo.keySet()) {
            List<Category> matched = allCategories.stream()
                    .filter(category -> category.getName().contains(entityInfo.getString(key)))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(matched)) {
                for (Category category : matched) {
                    List<Category> categoryPath = getCategoryPath(category, allCategories, new ArrayList<>());
                    categoryIds.addAll(categoryPath.stream().map(Category::getId).collect(Collectors.toList()));
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

    public JSONObject convertSubmitInfoToEntity(SubmitInfo submitInfo) {
        JSONObject entity = new JSONObject();
        entity.put("who", "孩子");
        entity.put("how", "写");
        entity.put("what", "作业");

        JSONObject submitObject = JSON.parseObject(JSON.toJSONString(submitInfo));
        for (String key : submitObject.keySet()) {
            if (EntityEnum.contains(key)) {
                entity.put(key, submitObject.getString(key));
            }
        }

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
