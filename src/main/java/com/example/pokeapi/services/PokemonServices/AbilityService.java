package com.example.pokeapi.services.PokemonServices;

import com.example.pokeapi.dto.PokemonDetailDtos.Abilities.AbilitiesPlaceholderDto;
import com.example.pokeapi.dto.PokemonDto;
import com.example.pokeapi.entities.Ability;
import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.entities.Type;
import com.example.pokeapi.repositories.AbilityRepository;
import com.example.pokeapi.services.PokemonServices.Dtos.AbilityDtoService;
import com.example.pokeapi.services.PokemonServices.Dtos.PokemonDtoService;
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


     public Ability getAbility(String ability){
         var abilityName = abilityRepository.findByName(ability);
         if(abilityName == null){
             var fetchedAbility = abilityDtoService.getAbility(ability);
             this.saveAbility(fetchedAbility);
             var savedAbility = abilityRepository.findByName(ability);
             return savedAbility;
         }
         return abilityName;
     }
    public List<Ability> getAbilityFrom(PokemonDto pokemon){
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
        List<Pokemon> matchedPokemons = new ArrayList<>();
        var abilityExist = abilityRepository.findByName(ability);
        if(abilityExist == null){
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
            for(String pokemonName : abilityExist.getLinkedPokemons()){
                var foundPokemon = pokemonService.getByName(pokemonName);
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

    public boolean doesAbilityEquals(String ability, Pokemon pokemon){
            for(Ability abilityName : pokemon.getAbilities()){
                if(abilityName.getName().equals(ability)){
                    return true;
                }
            }
            return false;

    }

    public void saveAbility(Ability newAbility){
        abilityRepository.save(newAbility);
    }

}
