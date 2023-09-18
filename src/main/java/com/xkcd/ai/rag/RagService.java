package com.xkcd.ai.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.client.Generation;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.loader.impl.JsonLoader;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.SystemPromptTemplate;
import org.springframework.ai.prompt.messages.Message;
import org.springframework.ai.prompt.messages.UserMessage;
import org.springframework.ai.retriever.impl.VectorStoreRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.impl.InMemoryVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RagService {

    private static final Logger logger = LoggerFactory.getLogger(RagService.class);

    @Value("classpath:/data/bikes.json")
    private Resource bikesResource;

    @Value("classpath:/prompts/system-qa.st")
    private Resource systemBikePrompt;

    private final AiClient aiClient;
    private final EmbeddingClient embeddingClient;

    public RagService(AiClient aiClient, EmbeddingClient embeddingClient) {
        this.aiClient = aiClient;
        this.embeddingClient = embeddingClient;
    }

    public Generation retrieve(String message) {

        // Step 1 - Load JSON document as Documents

        logger.info("Loading JSON as Documents");
        JsonLoader jsonLoader = new JsonLoader(bikesResource,
                "name", "price", "shortDescription", "description");
        List<Document> documents = jsonLoader.load();
        logger.info("Loading JSON as Documents");

        // Step 2 - Create embeddings and save to vector store

        logger.info("Creating Embeddings...");
        VectorStore vectorStore = new InMemoryVectorStore(embeddingClient);
        vectorStore.add(documents);
        logger.info("Embeddings created.");

        // Step 3 retrieve related documents to query

        VectorStoreRetriever vectorStoreRetriever = new VectorStoreRetriever(vectorStore);
        logger.info("Retrieving relevant documents");
        List<Document> similarDocuments = vectorStoreRetriever.retrieve(message);
        logger.info(String.format("Found %s relevant documents.", similarDocuments.size()));

        // Step 4 Embed documents into SystemMessage with the `system-qa.st` prompt template

        Message systemMessage = getSystemMessage(similarDocuments);
        UserMessage userMessage = new UserMessage(message);

        // Step 4 - Ask the AI model

        logger.info("Asking AI model to reply to question.");
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        logger.info(prompt.toString());
        AiResponse response = aiClient.generate(prompt);
        logger.info("AI responded.");
        logger.info(response.getGeneration().toString());
        return response.getGeneration();
    }

    private Message getSystemMessage(List<Document> similarDocuments) {

        String documents = similarDocuments.stream().map(entry -> entry.getContent()).collect(Collectors.joining("\n"));
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemBikePrompt);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("documents", documents));
        return systemMessage;

    }
}
