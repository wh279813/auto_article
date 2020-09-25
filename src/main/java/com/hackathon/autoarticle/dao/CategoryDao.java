package com.hackathon.autoarticle.dao;

import com.hackathon.autoarticle.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryDao {


    Category selectById(@Param("id") long id);

    List<Category> selectAllCategories();

    List<Category> selectByKeyword(@Param("keyword") String keyword);

}
