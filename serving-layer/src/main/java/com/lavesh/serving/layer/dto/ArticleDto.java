package com.lavesh.serving.layer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lavesh.common.core.model.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDto extends Article {
}
