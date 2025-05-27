package com.lavesh.serving.layer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lavesh.common.core.enums.CategoryEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleOutputDto {

    private String title;
    private String description;
    private Date publication_date;
    private String source_name;
    private List<CategoryEnum> categoryList;
    private Double relevance_score;
    private String summary;
}
