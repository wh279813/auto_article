package com.hackathon.autoarticle.dao;

import com.hackathon.autoarticle.entity.Article;

import java.util.List;

public interface ArticleDao {

    List<Article> selectAll();
}
