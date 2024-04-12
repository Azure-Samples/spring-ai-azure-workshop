# Spring AI - Stuffing the Prompt

## Introduction

In this exercise, you will learn about the technique known as 'stuffing the prompt' and how it can be applied to enhance generative AI models like Azure OpenAI.

By incorporating data into the prompt, you enable AI models to generate more accurate and contextually relevant responses.

For this example, we'll explore a query about Curling in the 2022 Olympics. The dataset used to train the AI model only goes up until September 2021. 
Therefore, without furnishing additional data in the prompt, the model won't be capable of answering the question.

The technique simply includes a document within the prompt sent to Azure OPenAI that provides information about Curling in the 2022 Olympics.

## Code Location and walkthrough

The code for this example is in the package `com.xkcd.ai.stuff`.

In that package there is a Spring REST Controller named `StuffController`.

The `PromptTemplateController` accepts HTTP GET requests at `http://localhost:8080/ai/stuff` with one optional parameter.


* `message` The question to ask about the Curling competition 2022 winter olymptics.

The default value is
```
Which athletes won the gold medal in curling at the 2022 Winter Olympics?
```

The response to the request is from the Azure OpenAI Service.

## Building and running

Run the project from your IDE or use the Maven command line

```
./mvnw spring-boot:run
```

## Initial interaction

Using the `http` utility to send a request without any additional information in the prompt:

```shell
http GET localhost:8080/ai/stuff
```
or using `curl`
```shell
curl http://localhost:8080/ai/stuff
```

This will result in a response like this:

```json
{
  "completion": "I'm sorry, I cannot predict future events as I am an AI language model and do not have information beyond what has been recorded."
}
```

## Stuffing the prompt

Now we will provide relevant information for Azure OpenAI to refer to reference.

We'll use Wikipedia article on Curling at the 2022 Winter Olympics.[Curling a the 2022 Winter Olympics ](https://en.wikipedia.org/wiki/Curling_at_the_2022_Winter_Olympics).

In the `resources\docs\` directory, there's a file named `wikipedia-curling.md` containing the article's content in Markdown format.

To stuff the prompt, send a request with the `stuffit` request parameter set to true.

```shell
http GET localhost:8080/ai/stuff stuffit=true
```
or using `curl`
```shell
curl --get  --data-urlencode 'stuffit=true' http://localhost:8080/ai/stuff 
```


The response will include information about gold medalists in curling at the 2022 Winter Olympics:

```json
{
    "completion": "The gold medalists in curling at the 2022 Winter Olympics were as follows:\n-Men's tournament: Brad Gushue, Mark Nichols, Brett Gallant, Geoff Walker, and Marc Kennedy (alternate) from Canada.\n-Women's tournament: Eve Muirhead, Vicky Wright, Jennifer Dodds, Hailey Duff, and Mili Smith (alternate) from Great Britain.\n-Mixed doubles tournament: Stefania Constantini and Amos Mosaner from Italy."
}
```


## Implementation

The classes in Spring AI help to implement this use-case is a simple manner.

The following is the implementation of the Controller method.

```java
@GetMapping("/ai/stuff")
public Completion completion(@RequestParam(value = "message", defaultValue = "Which athletes won the gold medal in curling at the 2022 Winter Olympics?'") String message,
                             @RequestParam(value = "stuffit", defaultValue = "false") boolean stuffit) {
    PromptTemplate promptTemplate = new PromptTemplate(qaPromptResource);
    Map<String, Object> map = new HashMap<>();
    map.put("question", message);
    if (stuffit) {
        map.put("context", docsToStuffResource);
    } else {
        map.put("context", "");
    }
    Prompt prompt = promptTemplate.create(map);
    AiResponse aiResponse = aiClient.generate(prompt);
    return new Completion(aiResponse.getGeneration().getText());
}
```

