package com.hackathon.autoarticle.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.hackathon.autoarticle.entity.ArticleSourceDetailResult;
import com.hackathon.autoarticle.entity.ArticleSourceHot;
import com.hackathon.autoarticle.entity.ArticleSourceListRequest;
import com.hackathon.autoarticle.entity.ArticleSourceListResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author minlingchao
 * @version V1.0
 * @description:
 * @date 2020/09/25 1:32 下午
 */
@Service
public class ArticleCrawlerService {

    private static final String COOKIE = ".5ce.referer={%22questionParam%22:%22%22%2C%22TParam%22:%22%22%2C%22Referer%22:%22%22}; .5ce.session=442f1f3b-202a-4a96-ac61-80d63f785fe0; UserGift={%22isTake%22:0%2C%22showBox%22:%222020-09-25%22%2C%22isType%22:0}; Hm_lvt_2d56acf42b4056540e59888ab8e03687=1601009123; ASP.NET_SessionId=xom2sjikc30hv3gfeuwil3yw; Hm_lvt_f3b3086e3d9a7a0a65c711de523251a6=1601009125; userBack={%22userTime%22:%222020-09-25%22}; .5118.referer={\"TParam\":\"\",\"QuestionParam\":\"\",\"Referer\":\"https://ai.5118.com/\"}; Sign=50c61c4f-8e06-43f1-adc4-32d9d52caabe; .AspNet.ApplicationCookie=EfIbdr4iXZOf36shYX3ggp6p1TMrxuCrIIKWLobNbpHTR0ZHC3qxL1Pw7kmX4-GwNan4g5nFwjJfPssnideHJjpwpZrDsz_JF5xoN77WqPZ2zyTCLb6CT8l-hd7hfKj3733Sa_8Yp_NyFg5DQRyAlp6zAyttFktuDQHHc5pkSw-8jkbbmPlm_YxLGZtVj3JF1TC1I2Ffelf5y_EuM_gqSqo9796AFm5G5n1yHBzFA1vnwFL69zVH3_mMb8a3zbQ6W3a3LrV0aAEzJJVnqIWb_vjKNsvDKdg7gf1dm9tecehKf8UUngUKnTERBDsGnY6yN0-yQWWLTgzfrke2GIKZBKULaHD3vXC1fyYZWXcg7Lu_kpmSm_GqzirgpzGtN3ITDottpzfUVNMMErroL6Mam_ntBVoBEhgIL28HB-kra7B3JoUwbBHybdbPdCNP9WR6fTOc0G1u8iceY5ppqaL-ALBVDCD7NKN8ZQYLQwrQ2cQ; .AspNetCore.Cookies=CfDJ8KsUrNtB36tHiD80pbv9L4mwrzG5xgVQRYGiyMef8oz6HYExywn2KPL-MFnMuMYyibqV7vgF-sfL1m619mVZxK3-8DAdtbIFsfOrQuXquIARu9ymzwGg99nsfTyQBVhSQSQGwnhXJwska_KrnnFSX223GvsYidNk9P2h6y_MrUkpIL_bydKA_L5o_06yVE0G-vdX1wviu34htsaclaCAJl1Z1WnUCk6-FJS9dICkHIpXGUb32JIeJxOk5_QDJjUc61u0B7CTI3q9s7b-Qt9ZiaGokFcT0yKbzTjPVrcDQM29OC2QdlmNcoeTkd9oqdiwraOKeB21WphUtfy_Z1HOr2FUmtkPjBEAlCurpMqis0klCz883rk9kp5htIBkYze3FQPsNr-GBjCdu-r4LIokC9EY6iqo4TWIGCT3wtRnOJt-2d2wyfcHATgYjXTlNtnw0i9K4BNcNMBAKFk3O-LMMycCQSbHD-GwE9aL7HftKaeu_kyx0oGczhyRwRQX721V6ZzOISn8PCIRAFU1Uq-Rw5hChZi9a7uBCA2cHMlF80J8lK-GgwHef6I-JX2LA1Crmsk_1r322i0aEo6-Iikx2a0CM2ZWZxD_Dhvo7PjVaHhmqCVpVqygRDDz2dbnW4-GwBYDjgOe6g0iTYWvWwD40VRNMB41rDbnwf0y9WeuTmvb45gsc2dGjekkof8uneHABOQ6Q4tb2t97DEjmae9piI__wLXNMiGW3XWCULPB2ZGi; 5ceinfo={%22token%22:%22B26A943944015E1060DE551D68F08403CEB25CA7B399B9ABDEA7986F95F6A1098C894694C8289387%22%2C%22avatar%22:%22https://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLAuyHBh5gFhUXF9ZjncV9MsScVxuib1vTShtW6A6qnf8pqZbj3iaehpR0Lp5BpfUmKK8OD4s20xxibLw/132%22%2C%22level%22:%222%22}; _5118_yx=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1Z3VpZCI6Ijg1Y2E1MzI2LTBhM2EtNDU1NS04YmMwLTgzOTc3NWI1YTY1NiIsInVpZCI6OTQxOTMxLCJhdWQiOiJ3d3cuNTExOC5jb20iLCJpc3MiOiJ3d3cuNTExOC5jb20ifQ.-xaWOCfkpDqLOkO9nX8thDV2L6zLqN1O4V6OXIQqr3c";

    public ArticleSourceListResult getArticleList(ArticleSourceListRequest requestParams) {
        HttpRequest request = HttpUtil.createPost("https://ai.5118.com/api/ai/acticle");
        request.header("Cache-Control", "no-cache");
        request.header("Accept", "application/json, text/plain, */*");
        request.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("Origin", "https://ai.5118.com");
        request.header("Sec-Fetch-Site", "same-origin");
        request.header("Sec-Fetch-Mode", "cors");
        request.header("Sec-Fetch-Dest", "empty");
        request.header("Referer", "https://ai.5118.com/material/article/%E6%89%BE%E7%82%B9%E7%B4%A0%E6%9D%90");
        request.header("Accept-Encoding", "gzip, deflate, br");
        request.header("Accept-Language", "zh-CN,zh;q=0.9,zh-TW;q=0.8");
        request.header("Cookie", COOKIE);
        request.header("Hm_lpvt_2d56acf42b4056540e59888ab8e03687", "1601011226");
        request.header("Hm_lpvt_f3b3086e3d9a7a0a65c711de523251a6", "1601011226");
        request.form("KeywordsList", requestParams.getKeywordsList());
        if (!StringUtils.isEmpty(requestParams.getResourcePlatformGuid())) {
            request.form("resourcePlatformGuid", requestParams.getResourcePlatformGuid());
        }
        request.form("isIncludeQuote", requestParams.getIsIncludeQuote());
        request.form("PageIndex", requestParams.getPageIndex());
        String resultStr = request.execute().body();
        ArticleSourceListResult result = JSONUtil.toBean(resultStr, ArticleSourceListResult.class);
        return result;
    }

    public ArticleSourceDetailResult getArticleDetail(String resourceGuid) {
        HttpRequest request = HttpUtil.createPost("https://ai.5118.com/api/ai/acticledetail");
        request.header("Cache-Control", "no-cache");
        request.header("Accept", "application/json, text/plain, */*");
        request.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("Origin", "https://ai.5118.com");
        request.header("Sec-Fetch-Site", "same-origin");
        request.header("Sec-Fetch-Mode", "cors");
        request.header("Sec-Fetch-Dest", "empty");
        request.header("Referer", "https://ai.5118.com/material/article/%E6%89%BE%E7%82%B9%E7%B4%A0%E6%9D%90");
        request.header("Accept-Encoding", "gzip, deflate, br");
        request.header("Accept-Language", "zh-CN,zh;q=0.9,zh-TW;q=0.8");
        request.header("Cookie", COOKIE);
        request.header("Hm_lpvt_2d56acf42b4056540e59888ab8e03687", "1601011226");
        request.header("Hm_lpvt_f3b3086e3d9a7a0a65c711de523251a6", "1601011226");
        request.form("resourceGuid", resourceGuid);
        String resultStr = request.execute().body();
        ArticleSourceDetailResult result = JSONUtil.toBean(resultStr, ArticleSourceDetailResult.class);
        return result;
    }

    public static void main(String[] args) {
        System.out.println();
    }

    public ArticleSourceHot hot(String rankingListGuid) {
        HttpRequest request = HttpUtil.createPost("https://ai.5118.com/api/ai/new");
        request.header("Cache-Control", "no-cache");
        request.header("Accept", "application/json, text/plain, */*");
        request.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("Origin", "https://ai.5118.com");
        request.header("Sec-Fetch-Site", "same-origin");
        request.header("Sec-Fetch-Mode", "cors");
        request.header("Sec-Fetch-Dest", "empty");
        request.header("Referer", "https://ai.5118.com/material/article/%E6%89%BE%E7%82%B9%E7%B4%A0%E6%9D%90");
        request.header("Accept-Encoding", "gzip, deflate, br");
        request.header("Accept-Language", "zh-CN,zh;q=0.9,zh-TW;q=0.8");
        request.header("Cookie", COOKIE);
        request.header("Hm_lpvt_2d56acf42b4056540e59888ab8e03687", "1601011226");
        request.header("Hm_lpvt_f3b3086e3d9a7a0a65c711de523251a6", "1601011226");
        request.form("rankingListGuid", rankingListGuid);
        String resultStr = request.execute().body();
        ArticleSourceHot result = JSONUtil.toBean(resultStr, ArticleSourceHot.class);
        return result;
    }
}
