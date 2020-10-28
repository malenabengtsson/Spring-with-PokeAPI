package com.example.pokeapi.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Type")
public class Type {

    @Id
    private String id;
    private String name;
    private String url;
    private List<Pokemon> pokemons;

    public Type() {
    }

    public Type(String name, String url, List<Pokemon> pokemons) {
        this.name = name;
        this.url = url;
        this.pokemons = pokemons;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}
