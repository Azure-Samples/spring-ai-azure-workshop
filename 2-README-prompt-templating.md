# Spring AI - Prompt Templating 

The code for this example is in the package `com.xkcd.ai.prompttemplate`.

In that package there is a Spring REST Controller named `PromptTemplateController`.

The `PromptTemplateController` shows how to use the StringTemplate Engine and the Spring AI `PromptTemplate` class.
In the `resources\prompts` directory is the file `joke-prompt`.  
That file is loaded using the Spring `Resource` abstraction in the controller as shown below


```java
    @Value("classpath:/prompts/joke-prompt.st")
    private Resource jokeResource;
```

The files contents are

```text
Tell me a {adjective} joke about {topic}
```

The `PromptTemplateController` accepts HTTP GET requests at `http://localhost:8080/ai/prompt` with two optional parameters

* `adjective`, whose default value is `funny`
* `topic`, whose default value is `cows`

The response to the request is from the Azure OpenAI Service.

## Building and running

Run the project from your IDE or use the Maven command line
```
./mvnw spring-boot:run
```

## Access the endpoint

To get a response for a funny joke about a cow.

```shell
http GET localhost:8080/ai/prompt adjective==funny topic==cow
```
or using `curl`
```shell
curl --get  --data-urlencode 'adjective=funny' --data-urlencode 'topic=cow' http://localhost:8080/ai/prompt 
```

A sample response is

```json
{
    "info": {},
    "text": "Why did the cow go to outer space? \n\nTo see the moooon!"
}
```

