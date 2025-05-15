package com.lavesh.serving.layer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private List<String> category;
    private Double relevance_score;
    private String summary;
}
