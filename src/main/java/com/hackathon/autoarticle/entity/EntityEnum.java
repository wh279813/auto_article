package com.hackathon.autoarticle.entity;

/**
 * Created by wanghuan on 2020/9/25.
 */
public enum EntityEnum {

    who,
    how,
    what,
    related,
    product,
    productIntro,
    population,
    point,
    pointIntro,
    productIntroduction,
    time,
    before,
    now,
    policy,
    way;

    public static boolean contains(String key) {
        for (EntityEnum entity : EntityEnum.values()) {
            if (entity.toString().equals(key)) {
                return true;
            }
        }

        return false;
    }
}