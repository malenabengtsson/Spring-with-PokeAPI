package com.example.pokeapi.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Type")
public class Type {

    @Id
    private String id;
    private String name;
    private List<String> linkedPokemons;

    public Type() {
    }

    public Type(String name, List<String> linkedPokemons) {
        this.name = name;

        this.linkedPokemons = linkedPokemons;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLinkedPokemons() {
        return linkedPokemons;
    }

    public void setLinkedPokemons(List<String> linkedPokemons) {
        this.linkedPokemons = linkedPokemons;
    }
}
