package com.lavesh.rest.controller.user;

import com.lavesh.common.core.exception.BaseException;
import com.lavesh.serving.layer.component.ArticleComponent;
import com.lavesh.serving.layer.dto.BaseResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user/article")
public class ArticleUserControllerV1 {


    @Autowired
    ArticleComponent articleComponent;

    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponseDto<Object>> findArticleByCategory(
            @Parameter(name = "categoryName", description = "categoryName", required = true, example = "sports")
            @RequestParam(value = "categoryName", required = true) final String categoryName,
            @Parameter(name = "pageNumber", description = "page Number", required = true, example = "0")
            @RequestParam(value = "pageNumber", required = true) final Integer pageNumber,
            @Parameter(name = "pageSize", description = "page Size", required = true, example = "5")
            @RequestParam(value = "pageSize", required = true) final Integer pageSize) throws BaseException {
        return articleComponent.findArticleByCategory(categoryName, pageNumber, pageSize);
    }

    @GetMapping(value = "/relevance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponseDto<Object>> findArticleByRelevanceThreshold(
            @Parameter(name = "threshold", description = "threshold for relevance score", required = true, example = "0.7")
            @RequestParam(value = "threshold", required = true) final Double threshold,
            @Parameter(name = "pageNumber", description = "page Number", required = true, example = "0")
            @RequestParam(value = "pageNumber", required = true) final Integer pageNumber,
            @Parameter(name = "pageSize", description = "page Size", required = true, example = "5")
            @RequestParam(value = "pageSize", required = true) final Integer pageSize) throws BaseException {
        return articleComponent.findArticleByRelevanceThreshold(threshold, pageNumber, pageSize);
    }

    @GetMapping(value = "/source", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponseDto<Object>> findArticleBySource(
            @Parameter(name = "source", description = "source", required = true, example = "Times now")
            @RequestParam(value = "source", required = true) final String source,
            @Parameter(name = "pageNumber", description = "page Number", required = true, example = "0")
            @RequestParam(value = "pageNumber", required = true) final Integer pageNumber,
            @Parameter(name = "pageSize", description = "page Size", required = true, example = "5")
            @RequestParam(value = "pageSize", required = true) final Integer pageSize) throws BaseException {
        return articleComponent.findArticleBySource(source, pageNumber, pageSize);
    }

    @GetMapping(value = "/nearby", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponseDto<Object>> findArticlesNearLocation(
            @Parameter(name = "latitude", description = "latitude", required = true, example = "10.5")
            @RequestParam(value = "latitude", required = true) final Double latitude,
            @Parameter(name = "longitude", description = "longitude", required = true, example = "10.5")
            @RequestParam(value = "longitude", required = true) final Double longitude,
            @Parameter(name = "radius", description = "radius", required = true, example = "1.5")
            @RequestParam(value = "radius", required = true) final Double radius,
            @Parameter(name = "pageNumber", description = "page Number", required = true, example = "0")
            @RequestParam(value = "pageNumber", required = true) final Integer pageNumber,
            @Parameter(name = "pageSize", description = "page Size", required = true, example = "5")
            @RequestParam(value = "pageSize", required = true) final Integer pageSize) throws BaseException {
        return articleComponent.findArticlesNearLocation(latitude, longitude, radius, pageNumber, pageSize);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponseDto<Object>> findArticleByFilter(
            @Parameter(name = "searchText", description = "Search test", required = true)
            @RequestParam(value = "searchText", required = true) final String searchText,
            @Parameter(name = "pageNumber", description = "page Number", required = true, example = "0")
            @RequestParam(value = "pageNumber", required = true) final Integer pageNumber,
            @Parameter(name = "pageSize", description = "page Size", required = true, example = "5")
            @RequestParam(value = "pageSize", required = true) final Integer pageSize) throws BaseException {
        return articleComponent.findArticleByFilter(searchText, pageNumber, pageSize);
    }
}
