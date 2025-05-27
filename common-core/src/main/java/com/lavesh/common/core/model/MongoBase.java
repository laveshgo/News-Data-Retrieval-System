package com.lavesh.common.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
public class MongoBase {

    @Id
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Field(name = "_id")
    @Column(name = "_id")
    private ObjectId _id;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, hidden = true)
    @Field(name = "id")
    @Column(name = "id")
    private String id;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, hidden = true)
    @Column(name = "status")
    @Field(name = "status")
    private Integer status;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, hidden = true)
    @Column(name = "createdAt")
    @Field(name = "createdAt")
    private Date createdAt;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, hidden = true)
    @Column(name = "updatedAt")
    @Field(name = "updatedAt")
    private Date updatedAt;

}
