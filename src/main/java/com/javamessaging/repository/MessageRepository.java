package com.javamessaging.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.javamessaging.model.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
    
    List<Message> findTop50ByOrderByTimestampDesc();

    List<Message> findBySenderId(String id);
}
