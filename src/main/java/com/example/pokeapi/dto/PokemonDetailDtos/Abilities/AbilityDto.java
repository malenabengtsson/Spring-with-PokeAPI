package com.example.pokeapi.dto.PokemonDetailDtos.Abilities;

import com.example.pokeapi.dto.PokemonDetailDtos.Type.LinkedPokemonDto;

public class AbilityDto {

    private LinkedPokemonDto pokemon;

    public AbilityDto() {
    }

    public AbilityDto(LinkedPokemonDto pokemon) {
        this.pokemon = pokemon;
    }

    public LinkedPokemonDto getPokemon() {
        return pokemon;
    }

    public void setPokemon(LinkedPokemonDto pokemon) {
        this.pokemon = pokemon;
    }
}
