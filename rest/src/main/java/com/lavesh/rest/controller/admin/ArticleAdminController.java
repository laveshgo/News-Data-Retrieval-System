package com.lavesh.rest.controller.admin;

import com.lavesh.serving.layer.dto.BaseResponseDto;
import com.lavesh.serving.layer.util.ResponseUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/article")
public class ArticleAdminController {


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponseDto<Object>> migrateLatestEntityPayment() {
        return ResponseUtil.buildSuccessResponse("Hello");
    }
}
