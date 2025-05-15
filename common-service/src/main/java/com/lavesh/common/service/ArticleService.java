package com.lavesh.common.service;

import com.lavesh.common.core.bo.ArticleBo;

import java.util.List;

public interface ArticleService {

    ArticleBo save(ArticleBo articleBo);

    ArticleBo findArticleById(String id);

    List<ArticleBo> findArticleListByCategory(String categoryName, Integer pageNumber, Integer pageSize);

    List<ArticleBo> findArticleByRelevanceThreshold(Double threshold, Integer pageNumber, Integer pageSize);

    List<ArticleBo> findArticleBySource(String source, Integer pageNumber, Integer pageSize);

    List<ArticleBo> findArticlesNearLocation(Double latitude, Double longitude, Double radius, Integer pageNumber, Integer pageSize);

    List<ArticleBo> findArticleByFilter(String searchText, Integer pageNumber, Integer pageSize);

}
