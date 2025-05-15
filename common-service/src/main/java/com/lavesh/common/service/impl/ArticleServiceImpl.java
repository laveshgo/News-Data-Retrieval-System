package com.lavesh.common.service.impl;

import com.lavesh.common.core.bo.ArticleBo;
import com.lavesh.common.core.entity.ArticleEntity;
import com.lavesh.common.core.mapper.ArticleBoEntityMapper;
import com.lavesh.common.core.repo.ArticleRepo;
import com.lavesh.common.core.repo.DynamicQueryRepo;
import com.lavesh.common.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    @Qualifier("ndrsMongoTemplate")
    MongoTemplate ndrsTemplate;

    @Autowired
    DynamicQueryRepo dynamicQueryRepo;

    @Override
    public ArticleBo save(ArticleBo ArticleBo) {
        return ArticleBoEntityMapper.INSTANCE.entityToBo(
                ndrsTemplate.save(ArticleBoEntityMapper.INSTANCE.boToEntity(ArticleBo)));
    }

    @Override
    public ArticleBo findArticleById(String id) {
        return ArticleBoEntityMapper.INSTANCE.entityToBo(articleRepo.findArticleById(id));
    }

    @Override
    public List<ArticleBo> findArticleListByCategory(String categoryName, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("publication_date").descending());
        Page<ArticleEntity> articleEntityPage = articleRepo.findArticleListByCategory(categoryName, pageable);
        return ArticleBoEntityMapper.INSTANCE.entityToBoList(articleEntityPage.getContent());
    }

    @Override
    public List<ArticleBo> findArticleByRelevanceThreshold(Double threshold, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("relevance_score").descending());
        Page<ArticleEntity> articleEntityPage = articleRepo.findArticleByRelevanceThreshold(threshold, pageable);
        return ArticleBoEntityMapper.INSTANCE.entityToBoList(articleEntityPage.getContent());
    }

    @Override
    public List<ArticleBo> findArticleBySource(String source, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("publication_date").descending());
        Page<ArticleEntity> articleEntityPage = articleRepo.findArticleBySource(source, pageable);
        return ArticleBoEntityMapper.INSTANCE.entityToBoList(articleEntityPage.getContent());
    }

    @Override
    public List<ArticleBo> findArticlesNearLocation(Double latitude, Double longitude, Double radius, Integer pageNumber, Integer pageSize) {
        GeoJsonPoint point = new GeoJsonPoint(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ArticleEntity> page = articleRepo.findByLocationNear(point, distance, pageable);
        return ArticleBoEntityMapper.INSTANCE.entityToBoList(page.getContent());
    }

    @Override
    public List<ArticleBo> findArticleByFilter(String searchText, Integer pageNumber, Integer pageSize) {
        return ArticleBoEntityMapper.INSTANCE.entityToBoList(dynamicQueryRepo.findArticleByFilter(searchText, pageNumber, pageSize));
    }
}
