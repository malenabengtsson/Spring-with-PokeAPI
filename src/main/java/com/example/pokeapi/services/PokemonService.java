package com.example.pokeapi.services;

import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.repositories.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PokemonService {

    @Autowired
    PokemonRepository pokemonRepository;

    public List<Pokemon> getByName(String name){
        var foundPokemon = pokemonRepository.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't find Pokemon with name: %s", name)));
        return List.of(foundPokemon);
    }

    public void savePokemon(Pokemon pokemon){
        var newPokemon = new Pokemon(pokemon.getOrder(), pokemon.getName(), pokemon.getHeight(), pokemon.getWeight(), pokemon.getTypes(), pokemon.getAbilities(), pokemon.getGame_indices());
        pokemonRepository.save(newPokemon);
    }
}
