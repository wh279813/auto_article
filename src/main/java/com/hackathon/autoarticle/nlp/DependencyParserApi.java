package com.hackathon.autoarticle.nlp;

import com.google.gson.reflect.TypeToken;
import com.huawei.nlp.client.NlpfClient;
import com.huawei.nlp.client.auth.AuthInfo;
import com.huawei.nlp.client.auth.AuthMode;
import com.huawei.nlp.client.exception.NlpException;
import com.huawei.nlp.restapi.*;
import com.huawei.nlp.restapi.model.KeywordExtractionReq;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;
import lombok.NonNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version V1.0
 * @description:
 * @date 2020/09/26 11:16 上午
 */
public class DependencyParserApi extends NlpfClient {
    private static final String PROJECT_ID = "0a0c3733ed000f5b2f66c0010bc35990";

    public DependencyParserApi(@NonNull AuthMode authMode, @NonNull AuthInfo info) {
        super(authMode, info);
    }

    public ApiResponse<DependencyParserResp> getDependencyParser(KeywordExtractionReq keywordExtractionReq) throws ApiException, NlpException {
        Call call = this.extractDependencyParserValidateBeforeCall(PROJECT_ID, keywordExtractionReq, this.getToken(), (ProgressResponseBody.ProgressListener) null, (ProgressRequestBody.ProgressRequestListener) null);
        Type localVarReturnType = (new TypeToken<DependencyParserResp>() {
        }).getType();
        return this.getNlpApi().getApiClient().execute(call, localVarReturnType);
    }

    private Call extractDependencyParserValidateBeforeCall(String projectId, KeywordExtractionReq textSimilarityReq, String xAuthToken, ProgressResponseBody.ProgressListener progressListener, ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        if (projectId == null) {
            throw new ApiException("Missing the required parameter 'projectId' when calling extractKeyword(Async)");
        } else if (textSimilarityReq == null) {
            throw new ApiException("Missing the required parameter 'textSimilarityReq' when calling extractKeyword(Async)");
        } else {
            Call call = this.extractDependencyParserCall(projectId, textSimilarityReq, xAuthToken, progressListener, progressRequestListener);
            return call;
        }
    }

    public Call extractDependencyParserCall(String projectId, KeywordExtractionReq textSimilarityReq, String xAuthToken, final ProgressResponseBody.ProgressListener progressListener, ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        String localVarPath = "/{project_id}/nlp-fundamental/dependency-parser".replaceAll("\\{project_id\\}", this.getNlpApi().getApiClient().escapeString(projectId.toString()));
        List<Pair> localVarQueryParams = new ArrayList();
        Map<String, String> localVarHeaderParams = new HashMap();
        if (xAuthToken != null) {
            localVarHeaderParams.put("X-Auth-Token", this.getNlpApi().getApiClient().parameterToString(xAuthToken));
        }

        Map<String, Object> localVarFormParams = new HashMap();
        String[] localVarAccepts = new String[]{"application/json"};
        String localVarAccept = this.getNlpApi().getApiClient().selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        String[] localVarContentTypes = new String[0];
        String localVarContentType = this.getNlpApi().getApiClient().selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);
        if (progressListener != null) {
            this.getNlpApi().getApiClient().getHttpClient().networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(), progressListener)).build();
                }
            });
        }

        String[] localVarAuthNames = new String[0];
        return this.getNlpApi().getApiClient().buildCall(localVarPath, "POST", localVarQueryParams, textSimilarityReq, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
}
