package com.hackathon.autoarticle.entity;

import lombok.Data;

import java.util.Objects;

/**
 * Created by wanghuan on 2020/9/25.
 */
@Data
public class Corpus {

    private Long id;

    private String content;

    private String structure;

    private String categories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corpus corpus = (Corpus) o;
        return Objects.equals(id, corpus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
