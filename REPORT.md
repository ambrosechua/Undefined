
# Report

## Project overview

See [README](README.md) for a quick overview. 

Undefined aims to be a self-hosted streaming service. You run your own music server, where your entire music library is 
stored. The [server](https://github.com/ambrosechua/Undefined-server) is a standard HTTP endpoint, data served as JSON, 
and serves up the album covers and music files. It's written in Node.js, also as a part of this project. 

## Mechanism and architecture

Uses a simple client-server model, with HTTP in OSI Layer 7. Data is represented in JSON and sent when the client sends 
a `GET` request. The current live API self-hosted by me is available at [http://undefined.ambrose.makerforce.io/](http://undefined.ambrose.makerforce.io/)

The parser (starting in `LibraryManager`) is JSON.org's reference implementation library for Java. Passing the `JSONObject`
to `Library`, it iterates over each child object, and constructs an entire tree of tracks. A `Library` contains multiple 
`Artist`s, that contains multiple `Album`s, that contains multiple `Track`s. This allows a easy and simple representaion
of an entire music library. 

`LibraryManager` is also used to establish the HTTP request to the endpoint, and schedule occasinal updates. 

`PlayManager` is a class to manage music playback of sequential `Media`. Because JavaFX's `MediaPlayer` has a very limited, 
single-`Media`-object, un-reusable API, `PlayManager` allows databinding similar to `MediaPlayer` but extending the 
capabilities of `MediaPlayer`. 

To simplify management of interface elements, there exists multiple custom controllers to bind and manage elements in a 
modular way. `TrackListController` can display a list of `Track`s in a table, optionally with a header showing information 
on the list (e.g. artist, album art). `CoverListController` displayes a list of `CoverItemController`s, and is useable
on any `ItemList<Item>`, allowing it to represent both `Album`s and `Artist`s. 

`InterfaceController` takes all of this and binds it together with databinding and event listeners, as most of the data
is `Observable`, with also observable states to represent various phases in IO or playback. 

## Testing 

To debug, I used the built-in debugger in IntelliJ for debugging deeply, and occasionally `System.out` certain properties.
Most testing is done manually, and due to time contraints, I did not manage to test the program whole. 

The program is too complex to be represented as an information processor. Therefore, it's hard to predict output. 

I tested it once on a MacBook Pro with a Retina display. The icons used are two times the actual pixels, therefore it 
rendered crystal-clear on the @2x display. Pretty cool. 

## Reflections

### What are some obstacles faced? 

The server was painstakingly written in asynchronous Node.js, using JavaScript Promises in an abusive manner. It took a
while to debug that into a working condition. 

A large roadblock came when it was time to decide on what to use for audio playback. The internal `MediaPlayer` library was
too weak in MP3 and couldn't decode other formats, so I took a look at Java's `SourceDataLine` and it seemed a little too
complex to use given a short time, so I also took a look at BasicPlayer but it's API was limited. I gave up and decided 
to use the `MediaPlayer` API. 

### What have you learnt through the project?

I learnt about the vast capabilities of `ObjectProperty<T>` and the various things I encountered while developing a JavaFX
application. I picked up some skills on developing JavaFX and Java in general, which further reinforced my weak knowledge
on Java. 

### What could you have done better if more time was given?

I would have developed this project with better organised classes and properly architectured the various function calls
and event mechanisms. I would also expand it and fix the various bugs as listed in [README.md](README.md), making it a
complete [dogfoodable](https://en.wikipedia.org/wiki/Eating_your_own_dog_food) project, with a website, easier to use
[nw.js](http://nwjs.io/) server and better internationalization and security. 