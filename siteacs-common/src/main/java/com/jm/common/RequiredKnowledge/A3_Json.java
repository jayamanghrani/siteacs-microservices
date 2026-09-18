package com.jm.common.RequiredKnowledge;

public class A3_Json {

}

/*
 * ============================================================
 * JSON (JavaScript Object Notation)
 * ============================================================
 *
 * -> Derived from JavaScript.
 * -> Lightweight Data Interchange Format.
 * -> Used to exchange data between applications.
 *
 * Other Data Interchange Format:
 * -> XML
 *
 * Why JSON became popular?
 * -> Less verbose than XML.
 * -> Easy to read and write.
 * -> Easy to parse.
 * -> Supported by almost every programming language.
 *
 * Every language provides libraries to:
 * 1. Convert Object -> JSON
 * 2. Convert JSON -> Object
 *
 * ============================================================
 * Java Libraries
 * ============================================================
 *
 * Jackson
 * Gson
 *
 * Spring Boot uses Jackson by default.
 * (No need to add Jackson manually in most Spring Boot projects.)
 *
 * ============================================================
 * Serialization
 * ============================================================
 *
 * Java Object
 *      |
 *      V
 *     JSON
 *
 * Example:
 *
 * Employee emp
 *      |
 *      V
 * {"id":101,"name":"Rahul"}
 *
 * ============================================================
 * Deserialization
 * ============================================================
 *
 * JSON
 *      |
 *      V
 * Employee Object
 *
 * Jackson performs both:
 *
 * -> Serialization
 * -> Deserialization
 *
 * ============================================================
 * JSON Example
 * ============================================================
 *
 * {
 *     "id": 101,
 *     "name": "Rahul",
 *     "skills": ["Java", "Spring Boot"],
 *     "address": {
 *         "city": "Bhopal",
 *         "state": "MP"
 *     }
 * }
 *
 * JSON Rules:
 * -> Data is stored as Key : Value pairs.
 * -> Keys are always inside double quotes.
 * -> Strings are inside double quotes.
 * -> Objects are enclosed in { }
 * -> Arrays are enclosed in [ ]
 *
 * ============================================================
 * @RequestBody
 * ============================================================
 *
 * Used to read JSON from the HTTP Request Body.
 *
 * Client sends:
 *
 * {
 *     "id":101,
 *     "name":"Rahul"
 * }
 *
 * Spring sees @RequestBody.
 *
 *        Request Body (JSON)
 *                 |
 *                 V
 *      HttpMessageConverter
 *                 |
 *                 V
 *            Jackson
 *                 |
 *                 V
 *         Employee Object
 *
 * Example:
 *
 * @PostMapping("/employees")
 * public void saveEmployee(@RequestBody Employee emp){
 *
 * }
 *
 * emp.getId() -> 101
 * emp.getName() -> Rahul
 *
 * Mostly used with:
 * POST
 * PUT
 * PATCH
 *
 * ============================================================
 * Receiving JSON as String
 * ============================================================
 *
 * Sometimes we receive raw JSON as String.
 *
 * @PostMapping("/employees")
 * public void save(@RequestBody String json){
 *
 *      ObjectMapper mapper = new ObjectMapper();
 *
 *      Employee emp = mapper.readValue(json, Employee.class);
 *
 * }
 *
 * Here Spring gives only the JSON String.
 * We manually convert JSON -> Employee.
 *
 * ============================================================
 * @RequestParam
 * ============================================================
 *
 * Used to read Query Parameters.
 *
 * URL:
 *
 * GET /employees?id=101&name=Rahul
 *
 * Example:
 *
 * @GetMapping("/employees")
 * public void getEmployee(
 *      @RequestParam int id,
 *      @RequestParam String name){
 *
 * }
 *
 * Result:
 *
 * id = 101
 * name = Rahul
 *
 * Used for:
 *
 * -> Search
 * -> Filter
 * -> Sorting
 * -> Pagination
 *
 * ============================================================
 * @PathVariable
 * ============================================================
 *
 * Used to read values from the URL Path.
 *
 * URL:
 *
 * GET /employees/101
 *
 * Example:
 *
 * @GetMapping("/employees/{id}")
 * public Employee getEmployee(@PathVariable int id){
 *
 * }
 *
 * id = 101
 *
 * Used when the value identifies a specific resource.
 *
 * ============================================================
 * @RequestParam vs @PathVariable
 * ============================================================
 *
 * PathVariable
 *
 * GET /employees/101
 *                ↑
 *         Resource Identity
 *
 *
 * RequestParam
 *
 * GET /employees?id=101&city=Bhopal
 *                ↑
 *        Extra Information / Filter
 *
 * ============================================================
 * Without @RequestBody
 * ============================================================
 *
 * Example:
 *
 * @GetMapping("/employees")
 * public void getEmployee(Employee emp){
 *
 * }
 *
 * URL:
 *
 * /employees?id=101&name=Rahul
 *
 * Spring automatically binds:
 *
 * id=101
 * name=Rahul
 *        |
 *        V
 * Employee Object
 *
 * This is called Spring Data Binding.
 *
 * JSON Body is NOT read here.
 *
 * ============================================================
 * Quick Revision
 * ============================================================
 *
 * @RequestBody
 * Request Body (JSON)
 *          |
 *          V
 *      Jackson
 *          |
 *          V
 *   Java Object
 *
 *
 * @RequestParam
 * URL ?id=101
 *          |
 *          V
 * Java Variable
 *
 *
 * @PathVariable
 * URL /employees/101
 *          |
 *          V
 * Java Variable
 *
 */