package com.lavesh.common.core.entity;

import com.lavesh.common.core.model.Article;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

@Data
@Entity
@Document(collection = "article")
public class ArticleEntity extends Article {
}
