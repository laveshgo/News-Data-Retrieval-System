package com.lavesh.serving.layer.component;

import com.lavesh.common.core.bo.ArticleBo;
import com.lavesh.common.core.constant.AppConstant;
import com.lavesh.common.core.enums.ErrorCodeEnum;
import com.lavesh.common.core.enums.SuccessCodeEnum;
import com.lavesh.common.core.exception.BaseException;
import com.lavesh.common.core.model.SuccessCode;
import com.lavesh.common.core.util.AppUtil;
import com.lavesh.common.service.ArticleService;
import com.lavesh.serving.layer.dto.ArticleDto;
import com.lavesh.serving.layer.dto.ArticleOutputDto;
import com.lavesh.serving.layer.dto.BaseResponseDto;
import com.lavesh.serving.layer.mapper.ArticleBoDtoMapper;
import com.lavesh.serving.layer.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ArticleComponent {

    @Autowired
    ArticleService articleService;

    public ResponseEntity<BaseResponseDto<Object>> createArticle(ArticleDto articleDto) {
        ArticleBo articleBo = ArticleBoDtoMapper.INSTANCE.dtoToBo(articleDto);
        articleBo.setCreatedAt(new Date());
        articleBo.setUpdatedAt(articleBo.getCreatedAt());
        articleBo.setStatus(AppConstant.ONE);
        articleBo.setLocation(articleDto.getLatitude(), articleDto.getLongitude());
        articleBo = articleService.save(articleBo);
        return ResponseUtil.buildSuccessResponse(ArticleBoDtoMapper.INSTANCE.boToDto(articleBo));
    }

    public ResponseEntity<BaseResponseDto<Object>> findArticleById(String id) throws BaseException {

        ArticleBo articleBo = articleService.findArticleById(id);

        log.info("Fetched articleBo {} with id {}", AppUtil.toJson(articleBo), id);
        if (ObjectUtils.isEmpty(articleBo)) {
            ResponseUtil.buildSuccessResponse(new SuccessCode(
                    SuccessCodeEnum.NO_CONTENT, AppConstant.NO_SUCH_IDENTIFIER));
        }

        return ResponseUtil.buildSuccessResponse(generateArticleOutputDto(articleBo));
    }

    public ResponseEntity<BaseResponseDto<Object>> createArticleList(List<ArticleDto> articleDtoList) {
        for (ArticleDto articleDto : articleDtoList) {
            ArticleBo articleBo = articleService.findArticleById(articleDto.getId());
            if (ObjectUtils.isNotEmpty(articleBo))
                continue;
            articleBo = ArticleBoDtoMapper.INSTANCE.dtoToBo(articleDto);
            articleBo.setCreatedAt(new Date());
            articleBo.setUpdatedAt(articleBo.getCreatedAt());
            articleBo.setStatus(AppConstant.ONE);
            articleBo.setLocation(articleDto.getLatitude(), articleDto.getLongitude());
            articleBo = articleService.save(articleBo);
            log.info("saving articleBo {}", AppUtil.toJson(articleBo));
        }
        return ResponseUtil.buildSuccessResponse();
    }

    public ResponseEntity<BaseResponseDto<Object>> findArticleByCategory(String categoryName, Integer pageNumber, Integer pageSize) {
        List<ArticleBo> articleBoList = articleService.findArticleListByCategory(categoryName, pageNumber, pageSize);
        if (ObjectUtils.isEmpty(articleBoList)) {
            ResponseUtil.buildSuccessResponse(new SuccessCode(
                    SuccessCodeEnum.NO_CONTENT, AppConstant.NO_ARTICLE_WITH_CATEGORY));
        }
        List<ArticleOutputDto> articleOutputDtoList = new ArrayList<>();
        for (ArticleBo articleBo : articleBoList) {
            articleOutputDtoList.add(generateArticleOutputDto(articleBo));
        }
        return ResponseUtil.buildSuccessResponse(articleOutputDtoList);
    }

    public ResponseEntity<BaseResponseDto<Object>> findArticleByRelevanceThreshold(Double threshold, Integer pageNumber, Integer pageSize) {
        List<ArticleBo> articleBoList = articleService.findArticleByRelevanceThreshold(threshold, pageNumber, pageSize);
        if (ObjectUtils.isEmpty(articleBoList)) {
            ResponseUtil.buildSuccessResponse(new SuccessCode(
                    SuccessCodeEnum.NO_CONTENT, AppConstant.NO_ARTICLE_WITH_RELEVANCE));
        }
        List<ArticleOutputDto> articleOutputDtoList = new ArrayList<>();
        for (ArticleBo articleBo : articleBoList) {
            articleOutputDtoList.add(generateArticleOutputDto(articleBo));
        }
        return ResponseUtil.buildSuccessResponse(articleOutputDtoList);
    }

    public ResponseEntity<BaseResponseDto<Object>> findArticleBySource(String source, Integer pageNumber, Integer pageSize) {
        List<ArticleBo> articleBoList = articleService.findArticleBySource(source, pageNumber, pageSize);
        if (ObjectUtils.isEmpty(articleBoList)) {
            ResponseUtil.buildSuccessResponse(new SuccessCode(
                    SuccessCodeEnum.NO_CONTENT, AppConstant.NO_ARTICLE_FROM_SOURCE));
        }
        List<ArticleOutputDto> articleOutputDtoList = new ArrayList<>();
        for (ArticleBo articleBo : articleBoList) {
            articleOutputDtoList.add(generateArticleOutputDto(articleBo));
        }
        return ResponseUtil.buildSuccessResponse(articleOutputDtoList);
    }

    public ResponseEntity<BaseResponseDto<Object>> findArticleByFilter(String searchText, Integer pageNumber, Integer pageSize) {
        if (!AppUtil.checkStringLength(searchText)) {
            return ResponseUtil.buildBadRequestResponseWithCustomMessage(ErrorCodeEnum.BAD_REQUEST, AppConstant.YOUR_REQUEST_CAN_NOT_PROCESS);
        }
        List<ArticleBo> articleBoList = articleService.findArticleByFilter(searchText, pageNumber, pageSize);
        if (ObjectUtils.isEmpty(articleBoList)) {
            ResponseUtil.buildSuccessResponse(new SuccessCode(
                    SuccessCodeEnum.NO_CONTENT, AppConstant.NO_SUCH_ARTICLE));
        }
        List<ArticleOutputDto> articleOutputDtoList = new ArrayList<>();
        for (ArticleBo articleBo : articleBoList) {
            articleOutputDtoList.add(generateArticleOutputDto(articleBo));
        }
        return ResponseUtil.buildSuccessResponse(articleOutputDtoList);
    }

    public ResponseEntity<BaseResponseDto<Object>> findArticlesNearLocation(Double latitude, Double longitude, Double radius, Integer pageNumber, Integer pageSize) {
        List<ArticleBo> articleBoList = articleService.findArticlesNearLocation(latitude, longitude, radius, pageNumber, pageSize);
        if (ObjectUtils.isEmpty(articleBoList)) {
            ResponseUtil.buildSuccessResponse(new SuccessCode(
                    SuccessCodeEnum.NO_CONTENT, AppConstant.NO_ARTICLE_FROM_NEARBY_LOCATION));
        }
        List<ArticleOutputDto> articleOutputDtoList = new ArrayList<>();
        for (ArticleBo articleBo : articleBoList) {
            articleOutputDtoList.add(generateArticleOutputDto(articleBo));
        }
        return ResponseUtil.buildSuccessResponse(articleOutputDtoList);
    }

    private ArticleOutputDto generateArticleOutputDto(ArticleBo articleBo) {
        ArticleOutputDto articleOutputDto = new ArticleOutputDto();
        articleOutputDto.setTitle(articleBo.getTitle());
        articleOutputDto.setDescription(articleBo.getDescription());
        articleOutputDto.setCategory(articleBo.getCategory());
        articleOutputDto.setRelevance_score(articleBo.getRelevance_score());
        articleOutputDto.setSource_name(articleBo.getSource_name());
        articleOutputDto.setPublication_date(articleBo.getPublication_date());

        return articleOutputDto;
    }
}
