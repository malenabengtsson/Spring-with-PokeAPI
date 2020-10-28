package com.example.pokeapi.dto.PokemonDetailDtos.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TypesDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("pokemon")
    List<TypeDto> pokemon;

    public TypesDto() {
    }

    public TypesDto(String name, List<TypeDto> pokemon) {
        this.name = name;
        this.pokemon = pokemon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TypeDto> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<TypeDto> pokemon) {
        this.pokemon = pokemon;
    }
}
