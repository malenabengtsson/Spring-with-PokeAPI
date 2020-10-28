package com.example.pokeapi.dto.PokemonDetailDtos.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LinkedPokemonDto {

    private String name;

    public LinkedPokemonDto() {
    }

    public LinkedPokemonDto(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


