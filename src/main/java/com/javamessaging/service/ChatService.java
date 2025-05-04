package com.javamessaging.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.javamessaging.model.Message;
import com.javamessaging.repository.MessageRepository;

@Service
public class ChatService {
    
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Async("taskExecutor")
    public void sendToKafka(Message message) {
        kafkaTemplate.send("chat-messages", message);
    }

    @Async("taskExecutor")
    public void saveToMongo(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getRecentMessages() {
        return messageRepository.findTop50ByOrderByTimestampDesc();
    }

    @KafkaListener(topics = "chat-messages")
    public void listen(Message message) {
        saveToMongo(message);
    }
}
