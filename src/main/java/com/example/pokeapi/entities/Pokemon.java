package com.example.pokeapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pokemon {
    @JsonProperty("order")
    private String id;
    private String name;
    private int height;
    private int weight;

    public Pokemon() {
    }

    public Pokemon(String id, String name, int height, int weight) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
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
}


