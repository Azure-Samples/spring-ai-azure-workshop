# Spring AI - Retrieval Augmented Generation

The code for this example is in the package `com.xkcd.ai.rag`.

In that package there is a Spring REST Controller named `RagController`.

The `RagController` accepts HTTP GET requests at `http://localhost:8080/ai/rag` with one optional parameter

* `message` The user question that can be answered using the data provided to the model.
 
The defaultValue is `What bike is good for city commuting?`.

## Code Walkthrough

The `RagController` delegates to the `RagService`. The `RagService` uses two Spring Resources.

* `classpath:/data/bikes.json` Contains the catalog information about bikes.  This is the set of data that we are bringing to the AI Model
* `classpath:/prompts/system-qa.st` Contains the system prompt.  Information about bikes that are similar to the use question will be 'stuffed' into the System prompt.


The steps of processing are

### Load the documents

```java
        JsonLoader jsonLoader = new JsonLoader(bikesResource,
                "name", "price", "shortDescription", "description");
        List<Document> documents = jsonLoader.load();
```

Create embeddings for the documents.  This calls the Azure OpenAI embedding endpoint.

```java
        VectorStore vectorStore = new InMemoryVectorStore(embeddingClient);
        vectorStore.add(documents);
```
### Find documents similar to the query

```java
        List<Document> similarDocuments = vectorStore.similaritySearch(message);
```

### Create a Prompt

The `Prompt` is created from a System message and a User message.  The System message contains the similar documents retrieved from the `VectorStore`.  The User message is the user's input to the `RagController` request parameter `message`

```java
        Message systemMessage = getSystemMessage(similarDocuments);
        UserMessage userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
```

### Get the response

```java
        AiResponse response = aiClient.generate(prompt);
```

The response to the request is from the Azure OpenAI Service.

## Building and running

Run the project from your IDE or use the Maven command line
```
./mvnw spring-boot:run
```

## Access the endpoint

To get a response to the default request of "What bike is good for city commuting?"

```shell
http http://localhost:8080/ai/rag
```
or using curl
```shell
curl http://localhost:8080/ai/rag
```

A sample response is

```json
{
  "info": {},
  "text": "Both the SwiftRide Hybrid and the AgileEon 9X are good options for city commuting, as they are designed for daily commutes and recreational rides. They both have efficient electric assist, integrated lights, and components that provide a comfortable and reliable cycling experience. Ultimately, the choice depends on your personal preferences and needs."
}
```

Now using the `message` request parameter to ask about a specific bike.

```shell
$  http GET localhost:8080/ai/rag message=="Tell me some details about the SwiftRide Hybrid"
```

A sample response is

```json
{
    "info": {},
    "text": "The SwiftRide Hybrid is a versatile and efficient bike designed for riders who want a smooth and enjoyable ride on various terrains. It features a lightweight and durable aluminum frame, a powerful electric motor that offers a speedy assist, a removable and fully-integrated 500Wh battery, a 10-speed Shimano drivetrain, hydraulic disc brakes for precise stopping power, wide puncture-resistant tires for stability, and integrated lights for enhanced visibility. The bike is priced at $3999.99."
}
```

