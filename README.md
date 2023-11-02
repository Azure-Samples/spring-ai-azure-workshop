# Spring AI Workshop

## Prerequisites

Create an OpenAI resource and a deployment in Azure Portal - [Instructions here](https://learn.microsoft.com/en-us/azure/ai-services/openai/how-to/create-resource?pivots=web-portal)

For the Type, choose option 1: Allow all networks

Before you begin make sure to set the following environment variables and values in application.properties

### Add the OpenAI API Key and Endpoint from the Azure Portal > < Your OpenAI Resource> > Keys and Endpoint

```shell
export SPRING_AI_AZURE_OPENAI_API_KEY=<INSERT KEY HERE>
export SPRING_AI_AZURE_OPENAI_ENDPOINT=<INSERT ENDPOINT URL HERE>
```

Next, set the following environment variables and values in application.properties to match your deployment in Open AI Studio.  In this example, the Deployment name is `gpt-35-turbo-16k` and the Model name is `gpt-35-turbo-16k`.

### 
```shell
spring.ai.azure.openai.model=gpt-35-turbo-16k
spring.ai.azure.openai.temperature=0.7
spring.ai.azure.openai.deploymentOrModelId=gpt-35-turbo-16k
```

## Workshop Overview

The workshop consists of six examples, each with a dedicated `README` file.

All six workshop examples are organized into individual Java packages within this project. In each package, you'll find a Spring @RestController class that serves as the entry point for showcasing the discussed functionality.

To interact with the @RestController, you will be using the `http` utility as a user-friendly alternative to `curl`.

Detailed instructions and exercises for each example can be found in their respective README files:

* 1-README-tell-me-a-joke.md 
* 2-README-prompt-templating.md 
* 3-README-prompt-roles.md 
* 4-README-output-parser.md 
* 5-README-stuff-prompt.md 
* 6-README-retrieval-augmented-generation.md

These guides will walk you through the workshop exercises.