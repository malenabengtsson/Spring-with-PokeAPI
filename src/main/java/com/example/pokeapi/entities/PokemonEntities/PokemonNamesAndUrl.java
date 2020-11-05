package com.example.pokeapi.entities.PokemonEntities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PokemonNamesAndUrl")
public class PokemonNamesAndUrl {

    @Id
    private String id;
    private String name;
    private String url;

    public PokemonNamesAndUrl() {
    }

    public PokemonNamesAndUrl(String name, String url) {
        this.name = name;
        this.url = url;
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
}
