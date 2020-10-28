package com.example.pokeapi.dto.PokemonDetailDtos.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TypeDto {

    private LinkedPokemonDto pokemon;


    public TypeDto() {
    }

    public TypeDto(LinkedPokemonDto pokemon) {
        this.pokemon = pokemon;
    }

    public LinkedPokemonDto getPokemon() {
        return pokemon;
    }

    public void setPokemon(LinkedPokemonDto pokemon) {
        this.pokemon = pokemon;
    }
}
