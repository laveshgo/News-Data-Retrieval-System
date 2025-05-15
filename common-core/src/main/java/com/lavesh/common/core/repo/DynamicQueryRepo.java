package com.lavesh.common.core.repo;

import com.lavesh.common.core.entity.ArticleEntity;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                Aggregation.project("title", "description", "url", "publication_date", "source_name", "category", "relevance_score")
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
}
