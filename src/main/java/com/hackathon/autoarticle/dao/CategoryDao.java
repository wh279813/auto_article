package com.hackathon.autoarticle.dao;

import com.hackathon.autoarticle.entity.Category;

public interface CategoryDao {


    Category selectById(long id);
}
