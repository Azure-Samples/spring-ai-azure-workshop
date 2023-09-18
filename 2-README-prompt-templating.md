# Simple Spring AI Application with Azure OpenAI

This project contains a web service that will accept HTTP GET requests at
`http://localhost:8080/ai/simple`

There is optional `message` parameter whose default value is "Tell me a joke".

The response to the request is from the Azure OpenAI Service.

## Building and running

Run the project from your IDE or use the Maven command line
```
./mvnw spring-boot:run
```

## Access the endpoint

To get a response for a funny joke about a cow.

```shell
$ http GET localhost:8080/ai/prompt adjective==funny topic==cow
```

A sample response is

```json
{
    "info": {},
    "text": "Why did the cow go to outer space? \n\nTo see the moooon!"
}
```

