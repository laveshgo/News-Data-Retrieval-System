package com.lavesh.serving.layer.factory;

import com.lavesh.common.core.constant.AppConstant;
import com.lavesh.common.core.enums.ErrorCodeEnum;
import com.lavesh.common.core.enums.LLMClientEnum;
import com.lavesh.common.core.exception.BaseException;
import com.lavesh.common.core.model.ErrorCode;
import com.lavesh.common.service.client.ILLMClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LLMClientFactory {

    private static final Map<LLMClientEnum, ILLMClient> llmClientCache = new HashMap<>();
    @Autowired
    private List<ILLMClient> llmClientList;

    @PostConstruct
    public void initMyServiceCache() {
        for (ILLMClient llmClient : llmClientList) {
            log.info("initializing llmClient : {}", llmClient);
            llmClientCache.put(llmClient.getLLMClientEnum(), llmClient);
        }
    }

    public ILLMClient getLLMClient(LLMClientEnum llmClientEnum) throws BaseException {
        assert llmClientEnum != null;
        log.info("llm client enum : {}", llmClientEnum);
        ILLMClient llmClient = llmClientCache.get(llmClientEnum);
        log.info("llm client : {}", llmClient);
        if (llmClient != null) {
            return llmClient;
        }

        throw new BaseException(new ErrorCode(ErrorCodeEnum.NO_SUCH_LLM_CLIENT_EXCEPTION,
                AppConstant.NO_SUCH_LLM_CLIENT_EXCEPTION));
    }
}
