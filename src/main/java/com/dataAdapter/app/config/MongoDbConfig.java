package com.dataAdapter.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDbConfig {
    public
    @Bean com.mongodb.client.MongoClient mongoClient() {
        return com.mongodb.client.MongoClients.create(
                "mongodb://localhost:27017/fileAdapter");
    }
}
