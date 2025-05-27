package com.lavesh.common.core.mapper;

import com.lavesh.common.core.bo.ArticleBo;
import com.lavesh.common.core.entity.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ArticleBoEntityMapper {
    ArticleBoEntityMapper INSTANCE = Mappers.getMapper(ArticleBoEntityMapper.class);

    ArticleBo entityToBo(final ArticleEntity articleEntity);

    List<ArticleBo> entityToBoList(final List<ArticleEntity> articleEntityList);

    ArticleEntity boToEntity(final ArticleBo articleBo);

    List<ArticleEntity> boToEntityList(final List<ArticleBo> articleBoList);
}
