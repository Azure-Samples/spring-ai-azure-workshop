# Spring AI - Hello World

The code for this example is in the package `com.xkcd.ai.helloworld`.

In that package there is a Spring REST Controller named `SimpleAiController`.

The `SimpleAiController` accepts HTTP GET requests at `http://localhost:8080/ai/simple`

There is optional `message` parameter whose default value is "Tell me a joke".
pl
The response to the request is from the Azure OpenAI Service.

## Building and running

Run the project from your IDE or use the Maven command line
```
./mvnw spring-boot:run
```

## Access the endpoint

To get a response to the default request of "Tell me a joke" using the `http` command line utility

```shell
http http://localhost:8080/ai/simple
```
or using `curl`
```shell
curl http://localhost:8080/ai/simple
```

A sample response is

```text
Why don't scientists trust atoms?

Because they make up everything!
```

Now using the `text` request parameter:

```shell
http GET localhost:8080/ai/simple message=='Tell me a joke about a cow.' 
```
or using `curl`
```shell
curl --get  --data-urlencode 'message=Tell me a joke about a cow.' http://localhost:8080/ai/simple 
```
A sample response is

```text
Why did the cow go to outer space? To see the moooon!
```
