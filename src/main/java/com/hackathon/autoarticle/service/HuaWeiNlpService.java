package com.hackathon.autoarticle.service;

import com.hackathon.autoarticle.constant.DependencyParserConstant;
import com.hackathon.autoarticle.nlp.DependencyParserApi;
import com.hackathon.autoarticle.nlp.DependencyParserResp;
import com.hackathon.autoarticle.nlp.MainPart;
import com.huawei.nlp.client.NlpfClient;
import com.huawei.nlp.client.auth.AuthInfo;
import com.huawei.nlp.client.auth.AuthMode;
import com.huawei.nlp.client.exception.NlpException;
import com.huawei.nlp.restapi.ApiException;
import com.huawei.nlp.restapi.ApiResponse;
import com.huawei.nlp.restapi.model.KeywordExtractionReq;
import com.huawei.nlp.restapi.model.NerReq;
import com.huawei.nlp.restapi.model.NerResp;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author minlingchao
 * @version V1.0
 * @description:
 * @date 2020/09/26 9:39 上午
 */
@Service
public class HuaWeiNlpService implements InitializingBean {
    private NlpfClient nlpfClient;

    private DependencyParserApi dependencyParserApi;

    public MainPart getMainPart(String keyword) throws NlpException, ApiException {
        KeywordExtractionReq keywordExtractionReq = new KeywordExtractionReq();
        keywordExtractionReq.setLang("zh");
        keywordExtractionReq.setText(keyword);
        ApiResponse<DependencyParserResp> respApiResponse = dependencyParserApi.getDependencyParser(keywordExtractionReq);
        MainPart mainPart = new MainPart();
        respApiResponse.getData().getWords().stream().forEach(s -> {
            if (s.getDependencyLabel().equals(DependencyParserConstant.CODT.SUBJ)) {
                if (s.getPos().equals(DependencyParserConstant.POS.NN)) {
                    mainPart.getSubject().add(s.getWord());
                }
                if (s.getPos().equals(DependencyParserConstant.POS.VV)) {
                    mainPart.getPredicate().add(s.getWord());
                }
            }
            if (s.getDependencyLabel().equals(DependencyParserConstant.CODT.OBJ) && !s.getPos().equals(DependencyParserConstant.POS.VA)) {
                mainPart.getObject().add(s.getWord());

            }
        });
        return mainPart;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AuthInfo authInfo = new AuthInfo("VNNANR66EDVXOOLQYRGO", "GRvBOOURlC85cFeJDL9GE4EKvz6SVvYhyO5RIgSU", "cn-north-4", "0a0c3733ed000f5b2f66c0010bc35990");
        nlpfClient = new NlpfClient(AuthMode.AKSK, authInfo);
        dependencyParserApi = new DependencyParserApi(AuthMode.AKSK, authInfo);
    }
}
