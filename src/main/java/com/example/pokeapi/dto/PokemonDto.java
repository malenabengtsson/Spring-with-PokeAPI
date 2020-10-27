package com.example.pokeapi.dto;

import com.example.pokeapi.dto.PokemonDetailDtos.Abilities.AbilitiesDto;
import com.example.pokeapi.dto.PokemonDetailDtos.GameIndices.GameIndicesDto;
import com.example.pokeapi.dto.PokemonDetailDtos.Type.TypesDto;
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
    private List<TypesDto> types;
    @JsonProperty("abilities")
    private List<AbilitiesDto> abilities;
    @JsonProperty("game_indices")
    private List<GameIndicesDto> game_indices;


    public PokemonDto() {
    }

    public PokemonDto(String order, String name, int height, int weight, List<TypesDto> types, List<AbilitiesDto> abilities, List<GameIndicesDto> game_indices) {
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

    public List<TypesDto> getTypes() {
        return types;
    }

    public void setTypes(List<TypesDto> types) {
        this.types = types;
    }

    public List<AbilitiesDto> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<AbilitiesDto> abilities) {
        this.abilities = abilities;
    }

    public List<GameIndicesDto> getGame_indices() {
        return game_indices;
    }

    public void setGame_indices(List<GameIndicesDto> game_indices) {
        this.game_indices = game_indices;
    }
}

