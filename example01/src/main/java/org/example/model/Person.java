package org.example.model;

import org.springframework.stereotype.Component;

@Component
public class Person {
    private String name = "Hari";
    private Parrot parrot;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(Parrot parrot) {
        this.parrot = parrot;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Parrot: " + parrot;
    }
}
