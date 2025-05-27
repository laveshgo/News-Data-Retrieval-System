package com.lavesh.common.service.client;

import com.lavesh.common.core.enums.LLMClientEnum;
import com.lavesh.common.core.model.IntentEntities;

import java.util.List;
import java.util.Map;

public interface ILLMClient {

    LLMClientEnum getLLMClientEnum();

    List<String> extractEntitiesFromQuery(final String query);

    IntentEntities extractEntitiesByIntentFromQuery(final String query);
}
