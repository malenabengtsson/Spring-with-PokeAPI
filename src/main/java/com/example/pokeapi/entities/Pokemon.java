package com.example.pokeapi.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Document(collection = "Pokemon")
public class Pokemon {

    @Id
    private String id;
    private String indexNumber;
    private String name;
    private int height;
    private int weight;

    @DBRef
    private List<Type> type;
    @DBRef
    private List<Ability> abilities;
    @DBRef
    private List<GameIndice> game_indices;


    public Pokemon() {
    }

    public Pokemon(String indexNumber, String name, int height, int weight, List<Type> type, List<Ability> abilities, List<GameIndice> game_indices) {
        this.indexNumber = indexNumber;
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

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
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


    public List<Type> getType() {
        return type;
    }

    public void setType(List<Type> type) {
        this.type = type;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public List<GameIndice> getGame_indices() {
        return game_indices;
    }

    public void setGame_indices(List<GameIndice> game_indices) {
        this.game_indices = game_indices;
    }
}



