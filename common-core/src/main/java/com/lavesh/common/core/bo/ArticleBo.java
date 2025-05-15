package com.lavesh.common.core.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lavesh.common.core.model.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
public class ArticleBo extends Article {
}
