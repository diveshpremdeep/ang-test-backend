# ang-test-backend
Contains code for the back end test as described at https://join.autogeneral.com.au/.

## Running the application locally
1. Ensure that Java 8 and Maven are installed and configured correctly on your machine. For details, refer to
 [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and 
 [Maven](https://maven.apache.org/index.html).
2. Ensure that Git is installed and configured correctly on your machine. For details, refer to
 [Git](https://git-scm.com/downloads).
3. Clone the `ang-test-backend` repository from https://github.com/diveshpremdeep/ang-test-backend.
4. Navigate to the `ang-test-backend` folder and run
   ```
    mvn spring-boot:run
   ```
   This will start a local server on port 8080, which can then be used to test the application.

## Endpoints
* `GET /tasks/validateBrackets?input=<url_encoded_input>` - Checks if brackets in a string are balanced.
* `POST /todo` - Creates a todo item.
* `GET /todo/{id}` - Returns the todo item having a given ID.
* `PATCH /todo/{id}` - Updates a todo item having a given ID.   

## Implementation assumptions
1. Todo item IDs are positive 32-bit integers. Any other value is considered invalid.
2. Newly created todo items are stored in an in-memory map for ease of implementation.
3. Instead of `ToDoValidationError`, I've used a generic `ValidationError` class to describe any input validation 
errors (empty input or input that does not confirm to the minimum / maximum size).

## What could have been done better
1. The service layer of the application could be abstracted behind an interface.
2. The minimum and maximum size of valid input could be configured as application properties.

## Miscellaneous notes
1. I've used [Project Lombok](https://projectlombok.org/) to automatically generate getters, setters, constructors, 
`toString()` and `hashCode()` implementations of the model classes.

 

## Lessons learnt
1. Writing code that works in a multi-threaded environment can be tricky to write. I've tried using concurrent data 
structures and have deliberately avoided the explicit use of `synchronized` methods and blocks.

