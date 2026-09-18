package com.jm.common.RequiredKnowledge;

public class Employee {

    String id;
    String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Employee() {
    }
}
