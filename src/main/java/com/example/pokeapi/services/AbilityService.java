package com.example.pokeapi.services;

import com.example.pokeapi.dto.PokemonDetailDtos.Abilities.AbilitiesPlaceholderDto;
import com.example.pokeapi.dto.PokemonDetailDtos.Abilities.AbilityDto;
import com.example.pokeapi.dto.PokemonDto;
import com.example.pokeapi.entities.Ability;
import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.repositories.AbilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AbilityService {

     @Autowired
     private AbilityRepository abilityRepository;

     @Autowired
     private AbilityDtoService abilityDtoService;

     @Autowired
     private PokemonService pokemonService;

     @Autowired
     private PokemonDtoService pokemonDtoService;


    public List<Ability> getAbility(PokemonDto pokemon){
        List<Ability> chosenAbility = new ArrayList<>();

        for(AbilitiesPlaceholderDto abilities : pokemon.getAbilities()){
            var abilityName = abilities.getAbility().getName().replace("-", " ");
            var abilityExist = abilityRepository.findByName(abilityName);
            if(abilityExist == null){
                var newAbility = abilityDtoService.getAbility(abilityName);
                this.saveAbility(newAbility);
                var fetchedAbility = abilityRepository.findByName(abilityName);
                chosenAbility.add(fetchedAbility);
        }
            else{
                chosenAbility.add(abilityExist);
            }
        }

        return chosenAbility;
    }

    public List<Pokemon> getPokemonsWithAbility(String ability){
        System.out.println("in method getAbility");
        List<Pokemon> matchedPokemons = new ArrayList<>();
        var abilityExist = abilityRepository.findByName(ability);
        if(abilityExist == null){
            System.out.println("in ability doesn exist");
            var fetchedAbility = abilityDtoService.getAbility(ability);
            this.saveAbility(fetchedAbility);
            for(String pokemonName : fetchedAbility.getLinkedPokemons()){
                var foundPokemon = pokemonService.getPokemonNames(pokemonName);
                for (Pokemon pokemon : foundPokemon){
                    var pokemonExistInDb = pokemonService.getByName(pokemon.getName());
                    if(pokemonExistInDb == null){
                        pokemonService.savePokemon(pokemon);
                        var savedPokemon = pokemonService.getByName(pokemon.getName());
                        matchedPokemons.add(savedPokemon);
                    }
                    else{
                        matchedPokemons.add(pokemonExistInDb);
                    }
                }
            }

        }
        else{
            System.out.println(abilityExist.getName());
            for(String pokemonName : abilityExist.getLinkedPokemons()){
                var foundPokemon = pokemonService.getByName(pokemonName);
                System.out.println(foundPokemon == null);
                if(foundPokemon == null){
                    var fetchPokemon = pokemonDtoService.findAllPokemonWith(pokemonName);
                    pokemonService.savePokemon(fetchPokemon);
                    var savedPokemon = pokemonService.getByName(pokemonName);
                    matchedPokemons.add(savedPokemon);
                }
                else {
                    matchedPokemons.add(foundPokemon);
                }

        }

        }
        return matchedPokemons;
    }

    public void saveAbility(Ability newAbility){
        abilityRepository.save(newAbility);
    }

}
