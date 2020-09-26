package com.hackathon.autoarticle.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author minlingchao
 * @version V1.0
 * @description:
 * @date 2020/09/25 2:15 下午
 */
@Data
public class ArticleSourceInfo implements Serializable {

    private String guid;

    private String title;

    private String issueTime;

    private Integer charsCount;

    private Integer readCount;

    private Integer likeCount;

    private Integer isKOL;

    private Integer isOriginal;

    private String coverImage;

    private String addTime;

    private String intro;

    private String name;

    private String headImage;

    private String resourcePlatformName;

    private String catalogName;

    private String url;

    private String intro2;

    private Boolean isFavorite;

    private Long id;

    private Integer quoteState;

    private String quoteTime;

}
