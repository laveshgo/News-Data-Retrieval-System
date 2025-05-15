package com.lavesh.rest.controller.admin;

import com.lavesh.common.core.exception.BaseException;
import com.lavesh.serving.layer.component.ArticleComponent;
import com.lavesh.serving.layer.dto.ArticleDto;
import com.lavesh.serving.layer.dto.BaseResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/article")
public class ArticleAdminControllerV1 {


    @Autowired
    ArticleComponent articleComponent;


    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponseDto<Object>> createArticleList(
            @RequestBody List<ArticleDto> articleDtoList) throws BaseException {
        return articleComponent.createArticleList(articleDtoList);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponseDto<Object>> createArticle(
            @RequestBody ArticleDto articleDto) throws BaseException {
        return articleComponent.createArticle(articleDto);
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponseDto<Object>> findArticleById(
            @Parameter(name = "id", description = "Id for Template", required = true,
                    example = "g2e2df4f-7b46-439d-997f-2a7a98c28f2g")
            @PathVariable(value = "id") final String id) throws BaseException {
        return articleComponent.findArticleById(id);
    }
}
