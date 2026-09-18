package com.jm.common.RequiredKnowledge;

public class MappingAttributes {
}
/*

| Annotation        | HTTP Method        | Common attributes                                                      | Example                                      |
        | ----------------- | ------------------ | ---------------------------------------------------------------------- | -------------------------------------------- |
        | `@RequestMapping` | Any / configurable | `path`, `value`, `method`, `params`, `headers`, `consumes`, `produces` | `@RequestMapping(path="/users", method=GET)` |
        | `@GetMapping`     | GET                | `path`, `value`, `params`, `headers`, `consumes`, `produces`           | `@GetMapping("/users")`                      |
        | `@PostMapping`    | POST               | `path`, `value`, `params`, `headers`, `consumes`, `produces`           | `@PostMapping("/users")`                     |
        | `@PutMapping`     | PUT                | `path`, `value`, `params`, `headers`, `consumes`, `produces`           | `@PutMapping("/users/{id}")`                 |
        | `@DeleteMapping`  | DELETE             | `path`, `value`, `params`, `headers`, `consumes`, `produces`           | `@DeleteMapping("/users/{id}")`              |
        | `@PatchMapping`   | PATCH              | `path`, `value`, `params`, `headers`, `consumes`, `produces`           | `@PatchMapping("/users/{id}")`               |
*/


/*
Attributes-
        | Attribute  | Meaning                                     | Example                       |
        | ---------- | ------------------------------------------- | ----------------------------- |
        | `path`     | URL path                                    | `path="/users"`               |
        | `value`    | Same purpose as `path`                      | `value="/users"`              |
        | `method`   | HTTP method — mainly with `@RequestMapping` | `method=GET`                  |
        | `params`   | Request must contain a particular parameter | `params="id"`                 |
        | `headers`  | Request must contain a particular header    | `headers="X-API-VERSION=1"`   |
        | `consumes` | What request body format the API accepts    | `consumes="application/json"` |
        | `produces` | What response format the API produces       | `produces="application/json"` |
*/

/*
mapping  header - Call this method only if the request has mentioned id and value
headers → filtering/matching

Request header - take the header from the request and give its value to method
@RequestHeader → extracting/reading
*/
