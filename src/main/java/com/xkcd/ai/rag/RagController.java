package com.xkcd.ai.rag;

import org.springframework.ai.client.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagController {

    private final RagService ragService;

    @Autowired
    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/ai/rag")
    public Generation generate(@RequestParam(value = "message", defaultValue = "What bike is good for city commuting?") String message) {
        return ragService.retrieve(message);
    }
}
