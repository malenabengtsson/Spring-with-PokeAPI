package com.example.pokeapi.services;

import com.example.pokeapi.dto.PokemonNameAndUrlDto;
import com.example.pokeapi.entities.*;
import com.example.pokeapi.repositories.PokemonRepository;
import com.example.pokeapi.repositories.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@ConfigurationProperties(value = "pokemon.api", ignoreUnknownFields = false)
public class PokemonService {

    private String url;

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    private QueryService queryService;

    @Autowired
    private PokemonDtoService pokemonDtoService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private AbilityService abilityService;

    public List<Pokemon> findPokemon(String combination, String name, Integer maxWeight, String type, String ability) {
        if (combination.equals("Name")) {
            return this.findPokemonByName(name);
        }
        if (combination.equals("Type")) {
            return this.findPokemonByType(type);
        }
        if (combination.equals("NameWeightTypeAbility")) {
            return this.findPokemonByNameWeightTypeAndAbility(name, maxWeight, type, ability);
        }
        if(combination.equals("Ability")){
            return this.findPokemonByAbility(ability);
        }
        return null;

    }

    public List<Pokemon> findPokemonByNameWeightTypeAndAbility(String name, Integer maxWeight, String type, String ability) {

        List<Pokemon> matchedPokemon = new ArrayList<>();
        List<Pokemon> pokemonToBeAdded = new ArrayList<>();
        var getPokemonNames = this.getPokemonNames(name);
        for (Pokemon pokemon : getPokemonNames) {
            if (maxWeight.intValue() <= pokemon.getWeight()) {
                for (Ability abilityName : pokemon.getAbilities()) {
                    System.out.println(abilityName.getName());
                    for (Type typeName : pokemon.getType()) {
                        System.out.println(typeName.getName());
                        if (abilityName.getName().equals(ability)) {
                            if (typeName.getName().equals(type)) {
                                pokemonToBeAdded.add(pokemon);
                            }
                        }
                    }

                }

            }
        }
        if (!pokemonToBeAdded.isEmpty()) {
            for (Pokemon isPokemonInDb : pokemonToBeAdded) {
                var isPokemonSaved = this.getByName(isPokemonInDb.getName());
                if (isPokemonSaved != null) {
                    System.out.println(isPokemonInDb.getName());
                    matchedPokemon.add(isPokemonInDb);
                } else {
                    this.savePokemon(isPokemonInDb);
                    var savedPokemon = this.getByName(isPokemonInDb.getName());
                    System.out.println(savedPokemon.getName());
                    matchedPokemon.add(savedPokemon);
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found with that combination");
        }

        return matchedPokemon;
    }

    public List<Pokemon> findPokemonByName(String name) {
        List<Pokemon> foundPokemons = new ArrayList<>();

        var fetchedPokemon = this.getPokemonNames(name);

        for (Pokemon pokemon : fetchedPokemon) {
            System.out.println("in fetch loop: " + pokemon.getName());
            var fetchPokemonFromDb = this.getByName(pokemon.getName());
            if (fetchPokemonFromDb != null) {
                foundPokemons.add(pokemon);
            } else {
                this.savePokemon(pokemon);
                var savedPokemon = this.getByName(pokemon.getName());
                foundPokemons.add(savedPokemon);
            }
        }

        return foundPokemons;
    }

    public List<Pokemon> findPokemonByType(String type) {
        return typeService.getPokemonsWithType(type);

    }

    public List<Pokemon> findPokemonByWeight(Integer maxWeight){
        return null;
    }
    public List<Pokemon> findPokemonByAbility(String ability){
    return abilityService.getPokemonsWithAbility(ability);
    }



    public Pokemon getByName(String name) {
        var foundPokemon = pokemonRepository.findByName(name);
        if (foundPokemon == null) {
            return null;
        } else {
            return foundPokemon;
        }
    }

    public List<Pokemon> getPokemonNames(String name) {
        List<Pokemon> foundPokemon = new ArrayList<>();
        var getPokemonNamesContainingName = pokemonDtoService.findAllPokemonContaining(name);
        if (getPokemonNamesContainingName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemons found containing " + name);
        } else {
            for (PokemonNamesAndUrl pokemon : getPokemonNamesContainingName) {
                var pokemonExistInDb = this.getByName(pokemon.getName());
                if (pokemonExistInDb == null) {
                    var fetchedPokemon = pokemonDtoService.findAllPokemonWith(pokemon.getName());
                    foundPokemon.add(fetchedPokemon);
                } else {
                    foundPokemon.add(pokemonExistInDb);
                }

            }

        }
        return foundPokemon;
    }


    public void savePokemon(Pokemon pokemon) {
        System.out.println("in save");
        var newPokemon = new Pokemon(pokemon.getIndexNumber(), pokemon.getName(), pokemon.getHeight(), pokemon.getWeight(), pokemon.getType(), pokemon.getAbilities(), pokemon.getGame_indices());
        pokemonRepository.save(newPokemon);
    }

    public List<Pokemon> findAll() {
        return pokemonRepository.findAll();
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
