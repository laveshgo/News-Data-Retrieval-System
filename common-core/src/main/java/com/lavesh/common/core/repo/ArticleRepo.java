package com.lavesh.common.core.repo;

import com.lavesh.common.core.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepo extends MongoRepository<ArticleEntity, String> {

    @Query("{'id': ?0}")
    ArticleEntity findArticleById(final String id);

    @Query("{'category': ?0, 'status' : 1}")
    Page<ArticleEntity> findArticleListByCategory(String categoryName, Pageable pageable);

    @Query("{'relevance_score': { $gt: ?0 }, 'status': 1 }")
    Page<ArticleEntity> findArticleByRelevanceThreshold(Double threshold, Pageable pageable);

    @Query("{'source_name': ?0, 'status' : 1}")
    Page<ArticleEntity> findArticleBySource(String source, Pageable pageable);

    Page<ArticleEntity> findByLocationNear(GeoJsonPoint point, Distance distance, Pageable pageable);
}
