package com.lavesh.common.core.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@PropertySource({"classpath:ndrs-db.properties"})
@EnableMongoRepositories(
        basePackages = {"com.lavesh.common.core.repo"},
        mongoTemplateRef = "ndrsMongoTemplate"
)
public class NDRSDataSourceConfiguration {

    @Primary
    @Bean(name = "ndrsProperties")
    @ConfigurationProperties(prefix = "app.ndrs.datasource")
    public MongoProperties ndrsProperties() throws Exception {
        return new MongoProperties();
    }

    @Primary
    @Bean(name = "ndrsDatabaseFactory")
    public MongoDatabaseFactory ndrsDatabaseFactory(
            @Qualifier("ndrsProperties") MongoProperties mongoProperties) throws Exception {
        return new SimpleMongoClientDatabaseFactory(
                mongoProperties.getUri()
        );
    }

    @Primary
    @Bean(name = "ndrsMongoTemplate")
    public MongoTemplate ndrsMongoTemplate() throws Exception {
        return new MongoTemplate(ndrsDatabaseFactory(ndrsProperties()));
    }
}
