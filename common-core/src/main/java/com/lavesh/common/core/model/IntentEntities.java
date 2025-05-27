package com.lavesh.common.core.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lavesh.common.core.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
public class IntentEntities {

    private List<GeoJsonPoint> nearby;
    private List<String> source;
    private List<CategoryEnum> category;
    private List<String> general;

    public IntentEntities() {
        this.nearby = new ArrayList<>();
        this.source = new ArrayList<>();
        this.category = new ArrayList<>();
        this.general = new ArrayList<>();
    }
}
