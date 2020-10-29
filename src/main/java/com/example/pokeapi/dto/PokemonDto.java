package com.example.pokeapi.dto;

import com.example.pokeapi.dto.PokemonDetailDtos.Abilities.AbilitiesPlaceholderDto;
import com.example.pokeapi.dto.PokemonDetailDtos.GameIndices.GameIndicesPlaceholderDto;
import com.example.pokeapi.dto.PokemonDetailDtos.Type.TypePlaceholderDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PokemonDto {


    @JsonProperty("order")
    private String order;

    @JsonProperty("name")
    private String name;

    @JsonProperty("height")
    private int height;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("types")
    private List<TypePlaceholderDto> types;
    @JsonProperty("abilities")
    private List<AbilitiesPlaceholderDto> abilities;
    @JsonProperty("game_indices")
    private List<GameIndicesPlaceholderDto> game_indices;


    public PokemonDto() {
    }

    public PokemonDto(String order, String name, int height, int weight, List<TypePlaceholderDto> types, List<AbilitiesPlaceholderDto> abilities, List<GameIndicesPlaceholderDto> game_indices) {
        this.order = order;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.types = types;
        this.abilities = abilities;
        this.game_indices = game_indices;
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

    public List<TypePlaceholderDto> getTypes() {
        return types;
    }

    public void setTypes(List<TypePlaceholderDto> types) {
        this.types = types;
    }

    public List<AbilitiesPlaceholderDto> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<AbilitiesPlaceholderDto> abilities) {
        this.abilities = abilities;
    }

    public List<GameIndicesPlaceholderDto> getGame_indices() {
        return game_indices;
    }

    public void setGame_indices(List<GameIndicesPlaceholderDto> game_indices) {
        this.game_indices = game_indices;
    }
}

