package com.javamessaging.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.javamessaging.model.Message;
import com.javamessaging.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ChatController {
    
    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat")
    public void handleChat(Message message) {
        chatService.sendToKafka(message);
    }

    @GetMapping("/chat/history")
    public List<Message> getChatHistory() {
        return chatService.getRecentMessages();
    }
    
}
