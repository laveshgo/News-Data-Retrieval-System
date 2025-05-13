package com.lavesh.serving.layer.component;

import com.lavesh.common.core.exception.BaseException;
import com.lavesh.serving.layer.dto.BaseResponseDto;
import com.lavesh.serving.layer.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ArticleComponent {

    public ResponseEntity<BaseResponseDto<Object>> viewAllArticles() throws BaseException {
        return ResponseUtil.buildSuccessResponse("Fetched successfully");
    }
}
