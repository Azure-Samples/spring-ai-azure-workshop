# Spring AI - Output Parsers

The code for this example is in the package `com.xkcd.ai.output`.

In that package there is a Spring REST Controller named `OutputParserController`.

The `OutputParserController` accepts HTTP GET requests at `http://localhost:8080/ai/output` with one optional parameter

* `actor` The actor's name.  The default value is `Jeff Bridges`

The actors name is used in the hardcoded text for the prompt

```text
String userMessage = """
        Generate the filmography for the actor {actor}.
        {format}
        """;
```

The `format` variable is obtained from the `OutputParser`

## BeanOutputParser

The `BeanOutputParser` generates an OpenAI JSON compliant schema for a JavaBean and provides instructions to use that schema when replying to a request.

```java
var outputParser = new BeanOutputParser<>(ActorsFilms.class);
String format = outputParser.getFormat();
```

The response from the Azure OpenAI Service is then parsed into the class `ActorsFilms`

```java
ActorsFilms actorsFilms = outputParser.parse(generation.getText());
```

## Building and running

Run the project from your IDE or use the Maven command line
```
./mvnw spring-boot:run
```

## Access the endpoint

Let's get a response using the default values.

```shell
http GET localhost:8080/ai/output
```
or using `curl`
```shell
curl http://localhost:8080/ai/output
```

The response is

```json
{
    "actor": "Jeff Bridges",
    "movies": [
        "The Last Picture Show",
        "Fat City",
        "Bad Company",
        "Lolly-Madonna XXX",
        "Thunderbolt and Lightfoot",
        "Rancho Deluxe",
        "Hearts of the West",
        "Stay Hungry",
        "King Kong",
        "Somebody Killed Her Husband",
        "The American Success Company",
        "Winter Kills",
        "Texasville",
        "The Fabulous Baker Boys",
        "Cold Feet",
        "The Fisher King",
        "American Heart",
        "Wild Bill",
        "Blown Away",
        "The Mirror Has Two Faces",
        "The Big Lebowski",
        "Arlington Road",
        "Simpatico",
        "The Muse",
        "The Contender",
        "K-PAX",
        "Masked and Anonymous",
        "Seabiscuit",
        "The Door in the Floor",
        "Against All Enemies",
        "Tideland",
        "Surf's Up",
        "Iron Man",
        "How to Lose Friends & Alienate People",
        "The Open Road",
        "Crazy Heart",
        "Tron: Legacy",
        "True Grit",
        "The Giver",
        "Seventh Son",
        "Hell or High Water",
        "Kingsman: The Golden Circle",
        "Only the Brave",
        "Bad Times at the El Royale",
        "Living in the Future's Past",
        "The Old Man & the Gun",
        "Bad Times at the El Royale",
        "The Little Prince",
        "The Only Living Boy in New York",
        "Kingsman: The Golden Circle",
        "Tales from the Loop"
    ]
}
```
Now lets change the default values
```shell
http GET localhost:8080/ai/output actor=="Bill Murray"
```
or using `curl`
```shell
curl --get  --data-urlencode 'actor=Bill Murray' http://localhost:8080/ai/output 
```

A sample response is

```json
{
  "actor": "Bill Murray",
  "movies": [
    "Meatballs",
    "Where the Buffalo Roam",
    "Caddyshack",
    "Stripes",
    "Tootsie",
    "Ghostbusters",
    "The Razor's Edge",
    "Little Shop of Horrors",
    "Scrooged",
    "Ghostbusters II",
    "What About Bob?",
    "Groundhog Day",
    "Mad Dog and Glory",
    "Ed Wood",
    "Kingpin",
    "Space Jam",
    "Larger Than Life",
    "The Man Who Knew Too Little",
    "Wild Things",
    "Rushmore",
    "Cradle Will Rock",
    "Hamlet",
    "Charlie's Angels",
    "Osmosis Jones",
    "The Royal Tenenbaums",
    "Lost in Translation",
    "Coffee and Cigarettes",
    "Garfield: The Movie",
    "Broken Flowers",
    "The Life Aquatic with Steve Zissou",
    "The Lost City",
    "Garfield: A Tail of Two Kitties",
    "The Darjeeling Limited",
    "Get Smart",
    "City of Ember",
    "The Limits of Control",
    "Zombieland",
    "Fantastic Mr. Fox",
    "Get Low",
    "Passion Play",
    "Moonrise Kingdom",
    "Hyde Park on Hudson",
    "The Monuments Men",
    "St. Vincent",
    "Aloha",
    "Rock the Kasbah",
    "The Jungle Book",
    "Ghostbusters",
    "Isle of Dogs",
    "The Dead Don't Die",
    "Zombieland: Double Tap",
    "On the Rocks",
    "The French Dispatch"
  ]
}
```


