package com.lavesh.google.cloud.service;

import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.lavesh.common.core.enums.CategoryEnum;
import com.lavesh.common.core.enums.LLMClientEnum;
import com.lavesh.common.core.model.IntentEntities;
import com.lavesh.common.core.util.CategoryUtil;
import com.lavesh.common.service.client.ILLMClient;
import com.lavesh.google.cloud.core.config.GcloudConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GoogleCloudClient implements ILLMClient {

    @Autowired
    private GcloudConfiguration gcloudConfiguration;

    static GeoApiContext context;

    @PostConstruct
    public void initGoogleCloudClientContext() {
        context = new GeoApiContext.Builder()
                .apiKey(gcloudConfiguration.getApiKey())
                .build();
    }

    public static LatLng getLatLng(String location) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, location).await();
            if (results.length > 0) {
                return results[0].geometry.location;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LLMClientEnum getLLMClientEnum() {
        return LLMClientEnum.GOOGLE_CLOUD;
    }

    @Override
    public List<String> extractEntitiesFromQuery(String query) {
        Document doc = Document.newBuilder().setContent(query).setType(Document.Type.PLAIN_TEXT).build();
        List<Entity> entities = new ArrayList<>();
        try {
            LanguageServiceClient language = LanguageServiceClient.create();
            AnalyzeEntitiesResponse response = language.analyzeEntities(doc);
            entities = response.getEntitiesList();
        } catch (IOException e) {
            log.error("exception occurred {}", e.getMessage());
        }
        List<String> entityNameList = new ArrayList<>();
        for (Entity entity : entities) {
            entityNameList.add(entity.getName());
        }
        return entityNameList;
    }

    @Override
    public IntentEntities extractEntitiesByIntentFromQuery(final String query) {
        Document doc = Document.newBuilder()
                .setContent(query)
                .setType(Document.Type.PLAIN_TEXT)
                .build();
        List<Entity> entities = new ArrayList<>();
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            AnalyzeEntitiesResponse response = language.analyzeEntities(doc);
            entities = response.getEntitiesList();
        } catch (IOException e) {
            log.error("exception occurred {}", e.getMessage());
        }

        IntentEntities intentEntities = new IntentEntities();
        for (Entity entity : entities) {
            String entityName = entity.getName();

            if (entity.getType() == Entity.Type.LOCATION) {
                LatLng coords = getLatLng(entityName);
                if (coords != null) {
                    log.info("Lat: {}, Lng: {}", coords.lat, coords.lng);
                    GeoJsonPoint point = new GeoJsonPoint(coords.lng, coords.lat);
                    intentEntities.getNearby().add(point);
                }
            }
            if (entity.getType() == Entity.Type.ORGANIZATION) {
                intentEntities.getSource().add(entityName);
            }
            if (CategoryUtil.isValidCategory(entityName)) {
                CategoryEnum categoryEnum = CategoryUtil.fromString(entityName);
                intentEntities.getCategory().add(categoryEnum);
            }

            intentEntities.getGeneral().add(entityName);

        }
        return intentEntities;
    }

}
