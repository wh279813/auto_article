package com.hackathon.autoarticle.service;

import com.hackathon.autoarticle.dao.CategoryDao;
import com.hackathon.autoarticle.dao.CorpusDao;
import com.hackathon.autoarticle.entity.Category;
import com.hackathon.autoarticle.entity.Corpus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CorpusService {
    @Autowired
    CorpusDao corpusDao;
    @Autowired
    CategoryDao categoryDao;

    public List<Corpus> getCandidateCorpus(List<Category> categories, String value) {
        List<Corpus> back = new ArrayList<>();
        List<Corpus> allCorpus = corpusDao.selectAll();
        for (Corpus corpus : allCorpus) {
            List<String> categoryIds = Arrays.asList(corpus.getCategories().split(","));
            boolean include = false;
            for (Category category : categories) {
                if (categoryIds.contains(Long.toString(category.getId()))) {
                    include = true;
                    break;
                }
            }
            if (include) {
                for (String categoryId : categoryIds) {
                    Category category = categoryDao.selectById(Long.parseLong(categoryId));
                    if (category.getName().equalsIgnoreCase(value)) {
                        back.add(corpus);
                        break;
                    }
                }
            } else {
                back.add(corpus);
            }
        }
        return back;
    }
}
