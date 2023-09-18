package com.xkcd.ai.prompttemplate;

import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.Generation;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PromptTemplateController {

    private final AiClient aiClient;

    @Value("classpath:/prompts/joke-prompt.st")
    private Resource jokeResource;


    @Autowired
    public PromptTemplateController(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    @GetMapping("/ai/prompt")
    public Generation completion(@RequestParam(value = "adjective", defaultValue = "funny") String adjective,
                                 @RequestParam(value = "topic", defaultValue = "cows") String topic) {

        PromptTemplate promptTemplate = new PromptTemplate(jokeResource);

        Prompt prompt = promptTemplate.create(Map.of("adjective", adjective, "topic", topic));

        return aiClient.generate(prompt).getGeneration();
    }
}
