package com.hackathon.autoarticle.entity;

import lombok.Data;

import java.util.List;

/**
 * @author minlingchao
 * @version V1.0
 * @description:
 * @date 2020/09/25 3:53 下午
 */
@Data
public class ArticleSourceHot {

    private Integer ErrorCode;

    private String ErrorMess;

    private HotData Data;

    @lombok.Data
    public static class HotData{
        private List<HotInfo> New;
    }

    @lombok.Data
    public static class HotInfo{
        private String Note;

        private String RankingListName;

        private String ResourceGuid;

        private String ResourcePlatformName;

        private String Title;

        private String TxtType;
    }
}
