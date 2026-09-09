package org.example.model;

import org.springframework.stereotype.Component;

@Component
public class Parrot {
    private String name = "Mithu";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
