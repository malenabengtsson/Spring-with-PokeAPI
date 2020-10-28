package com.example.pokeapi.services;

import com.example.pokeapi.dto.PokemonDetailDtos.Type.TypePlaceHolderDataDto;
import com.example.pokeapi.dto.PokemonDetailDtos.Type.TypePlaceholderDto;
import com.example.pokeapi.dto.PokemonDetailDtos.Type.TypesDto;
import com.example.pokeapi.dto.PokemonDto;
import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.entities.Type;
import com.example.pokeapi.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private TypeDtoService typeDtoService;

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private PokemonDtoService pokemonDtoService;


    public List<Type> getType(PokemonDto pokemon){
        List<Type> foundTypes = new ArrayList<>();
        for(TypePlaceholderDto type : pokemon.getTypes()) {
            var answer = typeRepository.findByName(type.getType().getName());
            if(answer == null){
                var newType = typeDtoService.getType(type.getType().getName());
                this.saveType(newType);
                var fetchedType = typeRepository.findByName(type.getType().getName());
                foundTypes.add(fetchedType);
            }
            else{
                foundTypes.add(answer);
            }

        }
        if(foundTypes.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type cannot be found");
        }
        return foundTypes;
    }

    public List<Pokemon> getPokemonsWithType(String name){
        System.out.println("In getPokemonWithType method");
        List<Pokemon> matchedPokemon = new ArrayList<>();
        var chosenType = typeRepository.findByName(name);
        if(chosenType == null){
            System.out.println("in if");
            var type = typeDtoService.getType(name);
            this.saveType(type);
            for(String pokemon : type.getLinkedPokemons()){
                var foundPokemon = pokemonService.getPokemonNames(pokemon);
                for(Pokemon poke : foundPokemon){
                    var pokemonExistInDb = pokemonService.getByName(poke.getName());
                    if(pokemonExistInDb == null){
                        pokemonService.savePokemon(poke);
                        var savedPokemon = pokemonService.getByName(poke.getName());
                        matchedPokemon.add(savedPokemon);
                    }
                    else{
                        matchedPokemon.add(pokemonExistInDb);
                    }
                }


            }
        }
        else{
            for(String pokemonName : chosenType.getLinkedPokemons()){
                var foundPokemon = pokemonService.getByName(pokemonName);
                System.out.println(foundPokemon == null);
                if(foundPokemon == null){
                    var fetchPokemon = pokemonDtoService.findAllPokemonWith(pokemonName);
                    pokemonService.savePokemon(fetchPokemon);
                    var savedPokemon = pokemonService.getByName(pokemonName);
                    matchedPokemon.add(savedPokemon);
                }
                else {
                    matchedPokemon.add(foundPokemon);
                }
            }
        }
        return matchedPokemon;
    }

    public void saveType(Type newType){
        typeRepository.save(newType);
    }
}
