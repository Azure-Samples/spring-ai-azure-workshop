package com.xkcd.ai.rag.config;

import org.springframework.ai.client.AiClient;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.xkcd.ai.rag.RagService;

@Configuration
public class RagConfiguration {

    @Bean
    public RagService ragService(AiClient aiClient, EmbeddingClient embeddingClient) {
        return new RagService(aiClient, embeddingClient);
    }

}
