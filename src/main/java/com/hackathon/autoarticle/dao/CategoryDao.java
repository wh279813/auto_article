package com.hackathon.autoarticle.dao;

import com.hackathon.autoarticle.entity.Category;
import org.apache.ibatis.annotations.Param;

public interface CategoryDao {


    Category selectById(@Param("id") long id);
}
