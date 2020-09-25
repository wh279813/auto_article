package com.hackathon.autoarticle.vo;

import lombok.Data;

/**
 * Created by wanghuan on 2020/9/25.
 */
@Data
public class SubmitInfo {

    // 核心问题
    private String coreProblem;


    // 营销产品名称
    private String productName;
    // 目标人群
    private String target;
    // 切入点名称
    private String cutPointName;
    // 切入点名称
    private String cutPointIntroduction;
    // 营销产品介绍
    private String productIntroduction;
    // 科目
    private String subject;


    // 时间
    private String time;
    // 原价
    private String originPrice;
    // 现价
    private String nowPrice;
    
}
