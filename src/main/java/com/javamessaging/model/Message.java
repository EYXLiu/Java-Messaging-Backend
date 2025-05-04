package com.javamessaging.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String content;

    @Indexed
    private String senderId;

    @Indexed(expireAfter = "2592000s")
    private Instant timestamp = Instant.now();

    public Message() {}
    public Message(String content, String senderId, String channelId) {
        this.content = content;
        this.senderId = senderId;
    }

    public String getContent() {
        return this.content;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
