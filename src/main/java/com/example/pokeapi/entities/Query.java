package com.example.pokeapi.entities;

import com.example.pokeapi.entities.PokemonEntities.Ability;
import com.example.pokeapi.entities.PokemonEntities.GameIndice;
import com.example.pokeapi.entities.PokemonEntities.Pokemon;
import com.example.pokeapi.entities.PokemonEntities.Type;
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

    @DBRef
    private GameIndice game;

    @DBRef
    private Ability ability;

    @DBRef
    private Type type;

    public Query() {
    }

    public Query(String queryString, List<Pokemon> pokemons) {
        this.queryString = queryString;
        this.pokemons = pokemons;
    }

    public Query(String queryString, GameIndice game) {
        this.queryString = queryString;
        this.game = game;
    }

    public Query(String queryString, Ability ability) {
        this.queryString = queryString;
        this.ability = ability;
    }

    public Query(String queryString, Type type) {
        this.queryString = queryString;
        this.type = type;
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

    public GameIndice getGame() {
        return game;
    }

    public void setGame(GameIndice game) {
        this.game = game;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

