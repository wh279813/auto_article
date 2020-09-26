package com.hackathon.autoarticle.entity;

/**
 * Created by wanghuan on 2020/9/26.
 */
public enum CategoryEnum {

    industry(3L),
    object(4L),
    age(5L),
    profession(6L),
    subject(7L);

    private Long relatedCategory;

    CategoryEnum(Long relatedCategory) {
        this.relatedCategory = relatedCategory;
    }

    public Long getRelatedCategory() {
        return relatedCategory;
    }
}
