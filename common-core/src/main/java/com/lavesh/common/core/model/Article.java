package com.lavesh.common.core.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
public class Article extends MongoBase {

    @Field(name = "title")
    @Column(name = "title")
    private String title;

    @Field(name = "description")
    @Column(name = "description")
    private String description;

    @Field(name = "url")
    @Column(name = "url")
    private String url;

    @Field(name = "publication_date")
    @Column(name = "publication_date")
    private Date publication_date;

    @Field(name = "source_name")
    @Column(name = "source_name")
    private String source_name;

    @Field(name = "category")
    @Column(name = "category")
    private List<String> category;

    @Field(name = "relevance_score")
    @Column(name = "relevance_score")
    private Double relevance_score;

    @Field(name = "latitude")
    @Column(name = "latitude")
    private Double latitude;

    @Field(name = "longitude")
    @Column(name = "longitude")
    private Double longitude;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    public void setLocation(double latitude, double longitude) {
        this.location = new GeoJsonPoint(longitude, latitude);
    }
}
