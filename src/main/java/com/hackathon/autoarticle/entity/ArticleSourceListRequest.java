package com.hackathon.autoarticle.entity;

import lombok.Data;

/**
 * @author minlingchao
 * @version V1.0
 * @description:
 * @date 2020/09/25 2:28 下午
 */
@Data
public class ArticleSourceListRequest {

    private String keywordsList;

    private Integer isIncludeQuote=-999;

    private Integer pageIndex;

    private String resourcePlatformGuid;
}
