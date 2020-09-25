package com.hackathon.autoarticle.entity;

/**
 * Created by wanghuan on 2020/9/25.
 */
public enum EntityEnum {

    who,
    how,
    what,
    productName,
    target,
    cutPointName,
    cutPointIntroduction,
    productIntroduction,
    subject;

    public static boolean contains(String key) {
        for (EntityEnum entity : EntityEnum.values()) {
            if (entity.toString().equals(key)) {
                return true;
            }
        }

        return false;
    }
}