# ang-test-backend
Contains code for the back end test as described at https://join.autogeneral.com.au/

## Endpoints
* `GET /tasks/validateBrackets?input=<url_encoded_input>` - Checks if brackets in a string are balanced.
* `POST /todo` - Creates a todo item.
* `GET /todo/{id}` - Returns the todo item having a given ID.
* `PATCH /todo/{id}` - Updates a todo item having a given ID.

# Implementation assumptions
1. Todo item IDs are 32-bit integers. Any non-integer value or a value bigger than the maximum value of a 32-bit 
integer is considered invalid.
2. Newly created todo items are stored in an in-memory map for ease of implementation.
3. Instead of `ToDoValidationError`, I've used a generic `ValidationError` class to describe any input validation 
errors (empty input or input that does not confirm to the minimum / maximum size).
 

# Lessons learnt
1. Writing code that works in a multi-threaded environment can be tricky to write. I've tried using concurrent data 
structures and have deliberately avoided the explicit use of `synchronized` methods and blocks.

