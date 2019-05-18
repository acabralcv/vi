package com.api.config;


import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * https://youtu.be/7ciWYVx3ZrA
 */
public class GridFsConfig extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String serverName;

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(serverName);
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

//    @Override
//    public  Mongo mongo() throws Exception{
//
//    }
}
