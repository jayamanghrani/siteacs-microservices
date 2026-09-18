package com.jm.common.RequiredKnowledge;

import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
public class A4_Employee {

    public A4_Employee()
    {
        System.out.println("A4_Employee class object created.....");
    }
    @GetMapping("/employee")
    public List<Employee> getEmployee() {

       List<Employee> emp = List.of(
                new Employee("101", "Jaya"),
                new Employee("102", "Ravi"),
                new Employee("103", "Kiran")
        );
    return emp;
    }

    @PostMapping("/employee1")
    public void saveEmployee1(@RequestBody Employee e) {

        System.out.println("Employee ID: " + e.getId());
        System.out.println("Employee Name: " + e.getName());
    }

    @PostMapping("/employees2")
    public void saveEmployee2(@RequestBody String json) {

        ObjectMapper mapper = new ObjectMapper();
        Employee obj = mapper.readValue(json, Employee.class);
        System.out.println("Employee ID: " + obj.getId());
        System.out.println("Employee Name: " + obj.getName());
    }

    // url - /employee?id=1&name=jaya
    @PostMapping("/employee3")
    public void saveEmployee3(@RequestParam String id, @RequestParam String name) {
        Employee e = new Employee(id,name);
        System.out.println("Employee ID: " + e.getId());
        System.out.println("Employee Name: " + e.getName());
    }

    // url - /employee/101/Jaya
    @PostMapping("/employee/{Id}/{Name}")
    public void saveEmployee4(@PathVariable("Id") String id,@PathVariable("Name") String name) {
        Employee e = new Employee(id, name);
        System.out.println("Employee ID: " + e.getId());
        System.out.println("Employee Name: " + e.getName());
    }
}
