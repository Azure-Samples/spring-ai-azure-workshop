# Using an Output Parser a Spring AI Application with Azure OpenAI

This project contains a web service that will accept HTTP GET requests at `http://localhost:8080/ai/output`

There is a request parameter that is used in the User Message

* `actor` The user request message.

The default value is `Jeff Bridges`

## BeanOutputParser

The `BeanOutputParser` generates an OpenAI JSON compliant schema for a JavaBean and provides instructions to use that schema when replying to a request.

The output parser then takes the String response from the AI model uses Jackson to deserialize the String to a JavaBean.

There is an web endpoint at `/ai/output` that passed in the hardcoded request to `Generate the filmography for a random actor.`

The reply from the AI model is mapped to the Java class `ActorsFilms` and returned in the web response.

## Building and running

Run the project from your IDE or use the Maven command line
```
./mvnw spring-boot:run
```

## Access the endpoint

Let's get a response using the default values.

```shell
$ http GET localhost:8080/ai/output
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
$  http GET localhost:8080/ai/output actor=="Bill Murray"
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


