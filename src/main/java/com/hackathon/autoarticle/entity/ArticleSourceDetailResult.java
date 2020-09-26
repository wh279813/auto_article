package com.hackathon.autoarticle.entity;

import lombok.Data;

/**
 * @author minlingchao
 * @version V1.0
 * @description:
 * @date 2020/09/25 2:43 下午
 */
@Data
public class ArticleSourceDetailResult {
    private Integer errorCode;

    private String errorMess;

    private ArticleDetail data;

    @Data
    public static class ArticleDetail{
        private String addTime;

        private String articleContent;

        private String catalogName;

        private String chTitle;

        private Integer charsCount;

        private Integer commentCount;

        private String coverImage;

        private String enTitle;

        private String forwardCount;

        private String guid;

        private String headImage;

        private String intro;

        private String intro2;

        private Integer isKOL;

        private Integer isOriginal;

        private String issueTime;

        private Integer likeCount;

        private String name;

        private Integer readCount;

        private String resourcePlatformName;

        private String title;

        private String url;
    }
}
