package com.hackathon.autoarticle.entity;

import lombok.Data;

@Data
public class Article {


    private long id;
    private String title;
    private String industry;
    private String template;
    private long createTime;

}
