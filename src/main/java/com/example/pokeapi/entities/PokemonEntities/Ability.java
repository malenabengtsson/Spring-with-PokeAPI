package com.example.pokeapi.entities.PokemonEntities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Document(collection = "Ability")
public class Ability {

    @Id
    private String id;
    private String name;
    private List<String> linkedPokemons;

    public Ability() {
    }

    public Ability(String name, List<String> linkedPokemons) {
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
