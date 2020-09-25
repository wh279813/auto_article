package com.hackathon.autoarticle.dao;

import com.hackathon.autoarticle.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {

    List<Article> selectAll();

    int deleteById(@Param("id") long id);
}
