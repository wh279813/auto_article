package com.hackathon.autoarticle.dao;

import com.hackathon.autoarticle.entity.Corpus;

import java.util.List;

/**
 * Created by wanghuan on 2020/9/25.
 */
public interface CorpusDao {

    List<Corpus> selectAll();
}
