package com.example.pokeapi.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Document(collection = "Query")
public class Query {
    @Id
    private String id;
    private String queryString;
    @DBRef
    private List<Pokemon> pokemons;

    public Query() {
    }

    public Query(String queryString, List<Pokemon> pokemons) {
        this.queryString = queryString;
        this.pokemons = pokemons;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}

