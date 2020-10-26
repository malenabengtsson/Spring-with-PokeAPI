package com.example.pokeapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Document(collection = "Pokemon")
public class Pokemon {

    @Id
    private String id;
    private String order;
    private String name;
    private int height;
    private int weight;
    private String type;
    private List<Object> abilities;
    private List<Object> game_indices;


    public Pokemon() {
    }

    public Pokemon(String order, String name, int height, int weight, String type, List<Object> abilities, List<Object> game_indices) {
        this.order = order;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.type = type;
        this.abilities = abilities;
        this.game_indices = game_indices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Object> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Object> abilities) {
        this.abilities = abilities;
    }

    public List<Object> getGame_indices() {
        return game_indices;
    }

    public void setGame_indices(List<Object> game_indices) {
        this.game_indices = game_indices;
    }
}



