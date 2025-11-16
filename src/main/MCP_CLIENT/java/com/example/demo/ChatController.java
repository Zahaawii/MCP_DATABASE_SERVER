package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder, ToolCallbackProvider tools) {
        Arrays.stream(tools.getToolCallbacks()).forEach(t -> {
            log.info("Tool callback found: {}", t.getToolDefinition());
        });

        this.chatClient = builder
                .defaultToolCallbacks(tools)
                .build();
    }

    @GetMapping("/chat")
    public String chat() {
        return chatClient.prompt()
                .user("Show me all the blog post made by test")
                .call()
                .content();
    }
}
