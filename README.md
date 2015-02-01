Jane  [![Build Status](https://travis-ci.org/psgs/Jane.png?branch=master)](https://travis-ci.org/psgs/Jane)    [![Coverage Status](https://coveralls.io/repos/psgs/Jane/badge.png)](https://coveralls.io/r/psgs/Jane)    [![Dependency Status](http://www.versioneye.com/user/projects/53982d2683add738da000016/badge.png)](http://www.versioneye.com/user/projects/53982d2683add738da000016)
========

Jane is a computer voice-interface written in Java. She's based off IronMan's Jarvis and Robocop's voice interface.
Jane will listen to a user's computer and trigger modules held in her "modules" package, when what she hears corresponds to a set of pre-defined input strings.

Jane is built on Windows, however many of her functions should be platform independent.
Jane does use several Desktop and SystemTrayIcon classes which may be specific to Windows, however.

# Modules
Modules available by default include:

- [x] BreakingNews - Will speak tweets by @BreakingNews as they are tweeted
- [ ] Agence France Presse (AFP) - Will speak tweets by @AFP as they are tweeted
- [x] Google Search - Speaks Google search results
- [ ] Panic - A module that will initiate a panic sequence when the user says "Panic, Panic, Panic"
- [x] ReadTweet - A module that can read a tweet out loud
- [ ] Twitter Notifications
- [x] Time - A module that speaks the current time and date
- [ ] GitHub Jobs - Speaks Jobs available in the user's area that may be of interest

# Development

Jane is currently under heavy development. Some features may not be quite finished yet.

Feel free to hack on Jane and create new modules! New modules can be written in Java and placed in the "modules" package to be run.
Modules should be initialised automatically; adding extra code to trigger the module isn't necessary.

### Building

Jane uses Maven to compile her code.
To compile Jane, [Apache Maven 3](http://maven.apache.org/) must be installed.
She can then be copiled using ```mvn clean install```. Using ```mvn clean install -U``` will also update her dependencies.

A Google API key can be obtained by following [several simple steps](http://www.chromium.org/developers/how-tos/api-keys).

#### Unit Tests

Jane's unit tests can be run using ```mvn test```.
Jane also uses [Coveralls](https://coveralls.io/r/psgs/Jane) to track test coverage and [Travis-CI](https://travis-ci.org/psgs/Jane) to track build successes.

#### Dependencies

Jane uses a multitude of dependencies, including:

* [CMU Sphinx](http://cmusphinx.sourceforge.net/)
* [GTranslate](https://code.google.com/p/java-google-translate-text-to-speech/)
* [Java Flac Encoder](http://javaflacencoder.sourceforge.net/)
* [JLayer, by JavaZoom](http://www.javazoom.net/javalayer/javalayer.html)
* [Twitter4J](http://twitter4j.org/)
* [JUnit](http://junit.org/)
* [Reflections](https://code.google.com/p/reflections/)
* [Mockito](https://code.google.com/p/mockito/)
* [JSoup](http://jsoup.org/)
* [Google Gson](https://code.google.com/p/google-gson/)

Some dependencies are removed from production code, as they're only required by certain modules or for testing purposes.

#### Principles

Jane focuses on the user. Development of Jane is user-oriented. She must be able to be useful to anybody.
Jane uses minimum bandwidth and CPU and can function under tough conditions. She loves playing survival games.
Jane's easy for anybody to use; she can be comprehensively secured, allowing for total secure and anonymous use.
