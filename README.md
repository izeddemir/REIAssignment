# READ ME
## Scope
This project is a REI assignment.

## Dependencies
 - Google Guava (27.0.1-jre)
 - Apache Collections4 (4.0)
 - Apache Math3 (3.6.1)
 - Dictionary API

## Assumptions
 - Assumed that if a word has duplicate characters each will be treated as unique letters when creating words from it. 
 Let's say the given word is `SEATTLE` then words generated out of that can use `T` twice at most; 
 `["TEST", "LATTE", "SET", ...]`.
 
 - Assumed that `DictionaryClient` class is a client for `Dictionary API` that sends HTTP requests.
 `DictionaryClient` can throw number of errors which are captured and thrown as `DictionaryClientException` class.

 - Dictionary API is mocked as instructed by doing an arbitrary check on the size of a word to make a decision.
 This behavior is mocked appropriately to test different behaviours of `WordFinder`class based on `DictionaryClient` 

## How to run Unit Tests
 - Option1: The Maven way: Navigate to root directory for `REIAssignment` project in command-line. Run `mvn clean test`.
 
 - Option2: Open the project in `IntelliJ` IDE then from Maven tab click on green arrow to trigger 
 the life-cycle events
 
 - Option3: Open the project in `IntelliJ` IDE then navigate to `src/test/java/WordFinderTest` then 
 run the entire class.

## Contact
izeddemir@outlook.com