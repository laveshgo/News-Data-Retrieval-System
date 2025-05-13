package com.lavesh.rest.controller.admin;

import com.lavesh.common.core.exception.BaseException;
import com.lavesh.serving.layer.component.ArticleComponent;
import com.lavesh.serving.layer.dto.BaseResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/article")
public class ArticleAdminController {


    @Autowired
    ArticleComponent articleComponent;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponseDto<Object>> viewAllArticles() throws BaseException {
        return articleComponent.viewAllArticles();
    }
}
