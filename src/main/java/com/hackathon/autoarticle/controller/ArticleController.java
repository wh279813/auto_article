package com.hackathon.autoarticle.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghuan on 2020/9/25.
 */
@RestController
public class ArticleController {


    @RequestMapping("/hello")
    @ResponseBody
    public Map<String, Object> showHellWorld() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "helloword");
        return map;
    }


}
