package com.example.checkrecognzer.services;

import com.example.checkrecognzer.tools.ProductCheckTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder, ProductCheckTool productCheckTool, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder.defaultSystem("""
                        Ты помощник для работы с чеками и покупками.
                        У тебя есть доступ к базе данных чеков.
                        Когда пользователь спрашивает о чеках, покупках или истории транзакций,
                        используй доступные функции для получения актуальной информации.
                        Отвечай на русском языке.
                        """)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor()
                )
                .defaultTools(productCheckTool)
                .build();
    }

    public String sendMessage(String message) {
        return chatClient.prompt()
                .user(userMessage -> userMessage
                        .text(message)
                )
                .call()
                .content();
    }
}