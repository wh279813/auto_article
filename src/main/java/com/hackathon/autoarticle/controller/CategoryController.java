package com.hackathon.autoarticle.controller;

import com.hackathon.autoarticle.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wanghuan on 2020/9/25.
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 获取文章列表
     *
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        categoryService.test();
    }

}
