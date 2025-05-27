package com.lavesh.common.core.repo;

import com.lavesh.common.core.entity.ArticleEntity;
import com.lavesh.common.core.enums.CategoryEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class DynamicQueryRepo {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<ArticleEntity> findArticleByFilter(String searchText, Integer pageNumber, Integer pageSize) {

        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(searchText);
        AggregationExpression textScoreMeta = context -> new Document("$meta", "textScore");

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(textCriteria),
                Aggregation.project("title", "description", "url", "publication_date", "source_name", "categoryList", "relevance_score")
                        .and(textScoreMeta).as("textScore"),
                Aggregation.addFields()
                        .addField("combinedScore")
                        .withValue(ArithmeticOperators.Add.valueOf("relevance_score").add("textScore"))
                        .build(),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "combinedScore")),
                Aggregation.skip((long) pageNumber * pageSize),
                Aggregation.limit(pageSize)
        );

        AggregationResults<ArticleEntity> results = mongoTemplate.aggregate(agg, "article", ArticleEntity.class);
        return results.getMappedResults();
    }

    public List<ArticleEntity> findArticlesByIntents(
            List<GeoJsonPoint> nearby,
            List<String> sources,
            List<CategoryEnum> categories,
            List<String> generalTerms,
            Integer pageNumber,
            Integer pageSize
    ) {
        List<Criteria> orCriteria = new ArrayList<>();

        if (ObjectUtils.isNotEmpty(sources)) {
            orCriteria.add(Criteria.where("source_name").in(sources));
        }

        if (ObjectUtils.isNotEmpty(categories)) {
            orCriteria.add(Criteria.where("categoryList").in(
                    categories.stream()
                            .map(CategoryEnum::name)
                            .collect(Collectors.toList())
            ));
        }

        if (ObjectUtils.isNotEmpty(generalTerms)) {
            List<Criteria> generalOr = new ArrayList<>();
            for (String term : generalTerms) {
                generalOr.add(Criteria.where("title").regex(term, "i"));
                generalOr.add(Criteria.where("description").regex(term, "i"));
            }
            orCriteria.add(new Criteria().orOperator(generalOr.toArray(new Criteria[0])));
        }

        Query query = new Query();


        // no nearby, but source/category/generalTerms combined with OR
        query.addCriteria(new Criteria().orOperator(orCriteria.toArray(new Criteria[0])));
        int skip = pageNumber * pageSize;
        query.skip(skip).limit(pageSize);
        log.info("MongoDB query: {}", query.getQueryObject().toJson());

        return mongoTemplate.find(query, ArticleEntity.class);
    }

}
