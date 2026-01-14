package com.example.checkrecognzer.services;

import com.example.checkrecognzer.models.ProductCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.StructuredOutputValidationAdvisor;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.io.InputStream;
import java.util.List;

@Service
public class CheckRecognizerService {
    private final ChatClient chatClient;
    private final ProductCheckService productCheckService;

    public CheckRecognizerService(ChatClient.Builder chatClientBuilder, ProductCheckService productCheckService) {
        var validationAdvisor = StructuredOutputValidationAdvisor.builder()
                .outputType(ProductCheck.class)
                .maxRepeatAttempts(3)
                .advisorOrder(BaseAdvisor.HIGHEST_PRECEDENCE + 1000)
                .build();
        this.chatClient = chatClientBuilder.defaultAdvisors(validationAdvisor).build();
        this.productCheckService = productCheckService;
    }

    public ProductCheck getCheckInfo(InputStream imageInputStream, String contentType) {
        ProductCheck productCheck = chatClient.prompt()
                .user(userMessage -> userMessage
                        .media(MimeTypeUtils.parseMimeType(contentType), new InputStreamResource(imageInputStream))
                        .text("Extract sales receipt data.")
                )
                .call()
                .entity(ProductCheck.class);

        productCheckService.createCheck(productCheck);
        return productCheck;
    }
}