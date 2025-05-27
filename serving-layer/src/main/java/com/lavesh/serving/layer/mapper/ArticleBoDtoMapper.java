package com.lavesh.serving.layer.mapper;

import com.lavesh.common.core.bo.ArticleBo;
import com.lavesh.serving.layer.dto.ArticleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ArticleBoDtoMapper {

    ArticleBoDtoMapper INSTANCE = Mappers.getMapper(ArticleBoDtoMapper.class);

    ArticleBo dtoToBo(final ArticleDto articleDto);

    List<ArticleBo> dtoToBoList(final List<ArticleDto> articleDtoList);

    ArticleDto boToDto(final ArticleBo articleBo);

    List<ArticleDto> boToDtoList(final List<ArticleBo> articleBoList);
}
