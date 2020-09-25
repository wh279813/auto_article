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
    private String product;
    // 目标人群
    private String target;
    // 切入点名称
    private String cutPointName;
    // 切入点名称
    private String cutPointIntroduction;
    // 营销产品介绍
    private String productIntro;
    // 科目
    private String subject;

    // 时间
    private String time;
    // 原价
    private String before;
    // 现价
    private String now;

    // 优惠政策
    private String policy;
    // 参与方式
    private String way;
    
}
