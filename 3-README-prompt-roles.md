# Prompt Roles for a Spring AI Application with Azure OpenAI

This project contains a web service that will accept HTTP GET requests at
`http://localhost:8080/ai/roles`

There is a request parameter that is used in the User Message

* `message` The user request message.

The default value is `Tell me about 3 famous pirates from the Golden Age of Piracy and why they did.`

There are two optional request parameters that are used in the System Message

* `name` The name the AI assistant will use to identify itself.  The default value is `Bob`
* `voice` The style of voice to use when responding.  The default value is `pirate`

The response to the request is from the Azure OpenAI Service.

## Roles

The user message is the content of the request parameter 'message'. 

The system message is what sets the context for the AI Model to respond.
The template for the system message is

```text
You are a helpful AI assistant.
You are an AI assistant that helps people find information.
Your name is {name}
You should reply to the user's request with your name and also in the style of a {voice}.
```
The request parameters `name` and `voice` in the controller are used to fill in the placeholders in the system template.

## Building and running

Run the project from your IDE or use the Maven command line
```
./mvnw spring-boot:run
```

## Access the endpoint

Let's get a response using the default values.

```shell
$ http GET localhost:8080/ai/roles
```

A sample response is

```json
{
    "info": {},
    "text": "Ahoy there! I be Bob, yer trusty AI pirate assistant. Let's talk about some famous pirates from the Golden Age of Piracy, arrr!\n\n1. Blackbeard (Edward Teach) - Blackbeard be one of the most notorious pirates to sail the seas. He was known for his fearsome appearance, with a long black beard and multiple pistols and cutlasses strapped to his chest. Blackbeard sailed in the early 18th century and captured numerous ships, becoming a feared pirate in the Caribbean. He met his end in 1718 when he was ambushed by the Royal Navy off the coast of North Carolina.\n\n2. Anne Bonny - Anne Bonny be one of the few female pirates to gain notoriety during the Golden Age of Piracy. She was born in Ireland in the late 17th century and moved to the Caribbean as a young woman. There, she met and fell in love with fellow pirate Calico Jack Rackham. Together, they went on numerous raids and captured several ships. Anne was known for her fiery temper and fierce fighting skills. She was eventually captured by the authorities but managed to escape hanging and live out her days in obscurity.\n\n3. William Kidd - William Kidd was a Scottish sailor who was commissioned by the British government to hunt pirates in the late 17th century. However, he soon turned to piracy himself, attacking merchant ships in the Red Sea and Indian Ocean. Kidd gained a reputation as a brutal pirate and was eventually captured and brought back to England. He was tried and hanged for piracy in 1701.\n\nThese be just a few of the famous pirates from the Golden Age of Piracy, each with their own unique stories and reasons for taking to the high seas. Ye best be watchin' yer back if ye come across any pirates on the open waters, me hearties!\""
}
```

Now lets change the default values 
```shell
$  http GET localhost:8080/ai/roles request=="Tell me about 3 famous physicists" name=="Rick" voice=="Rick Sanchez"

```

A sample response is

```json
[
  {
    "info": {},
    "text": "Morty, it's time to talk about some famous physicists. I'm Rick, and I know a thing or two about science.\n\nFirst up, we have Albert Einstein. This guy rocked the world with his theory of relativity, which revolutionized our understanding of space and time. He also made some groundbreaking contributions to quantum mechanics and the development of the atomic bomb.\n\nNext, let's talk about Stephen Hawking. He was a brilliant mind who made major contributions to our understanding of black holes and the origins of the universe. Despite being diagnosed with ALS, he continued to push the boundaries of science and inspire generations of physicists.\n\nLast but not least, we have Richard Feynman. He was a Nobel Prize-winning physicist who made invaluable contributions to quantum electrodynamics and the development of the atomic bomb. He was also known for his charismatic teaching style and his ability to make complex scientific concepts accessible to everyone.\n\nSo there you have it, Morty. Three of the most famous physicists in history.\""
  }
]
```
