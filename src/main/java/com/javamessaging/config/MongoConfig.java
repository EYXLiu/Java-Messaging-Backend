package com.javamessaging.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.index.Index;

import com.javamessaging.config.converter.DateToInstantConverter;
import com.javamessaging.config.converter.InstantToDateConverter;
import com.javamessaging.model.Message;

@Configuration
public class MongoConfig {
    
    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
            new InstantToDateConverter(),
            new DateToInstantConverter()
        ));
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory factory) {
        MongoTemplate template = new MongoTemplate(factory);
        template.indexOps(Message.class).ensureIndex(
            new Index().on("timestamp", Sort.Direction.DESC)
        );
        return template;
    }
}
