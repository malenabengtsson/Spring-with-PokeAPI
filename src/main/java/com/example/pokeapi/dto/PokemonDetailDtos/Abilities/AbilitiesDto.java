package com.example.pokeapi.dto.PokemonDetailDtos.Abilities;

import com.example.pokeapi.dto.PokemonDetailDtos.Type.TypeDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AbilitiesDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("pokemon")
    List<AbilityDto> pokemon;

    public AbilitiesDto() {
    }

    public AbilitiesDto(String name, List<AbilityDto> pokemon) {
        this.name = name;
        this.pokemon = pokemon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AbilityDto> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<AbilityDto> pokemon) {
        this.pokemon = pokemon;
    }
}
