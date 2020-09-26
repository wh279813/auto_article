package com.hackathon.autoarticle.entity;

import lombok.Data;

import java.util.List;

/**
 * @author minlingchao
 * @version V1.0
 * @description:
 * @date 2020/09/25 2:19 下午
 */
@Data
public class ArticleSourceListResult {

    private Integer errorCode;

    private String errorMess;

    private ArticleData data;

    @Data
    public static class ArticleData{

        public List<ArticleSourceInfo> dataSource;
    }
}
