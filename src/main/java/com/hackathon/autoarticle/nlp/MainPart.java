package com.hackathon.autoarticle.nlp;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author minlingchao
 * @version V1.0
 * @description:
 * @date 2020/09/26 11:45 上午
 */
@Data
public class MainPart {

    /**
     * 主语
     */
    public List<String> subject = Lists.newArrayList();
    /**
     * 谓语
     */
    public List<String> predicate = Lists.newArrayList();
    /**
     * 宾语
     */
    public List<String> object = Lists.newArrayList();
}
