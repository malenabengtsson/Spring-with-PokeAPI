package com.example.pokeapi.services.PokemonServices;

import com.example.pokeapi.dto.PokemonDetailDtos.Type.TypePlaceholderDto;
import com.example.pokeapi.dto.PokemonDto;
import com.example.pokeapi.entities.PokemonEntities.Pokemon;
import com.example.pokeapi.entities.PokemonEntities.Type;
import com.example.pokeapi.repositories.TypeRepository;
import com.example.pokeapi.services.PokemonServices.Dtos.PokemonDtoService;
import com.example.pokeapi.services.PokemonServices.Dtos.TypeDtoService;
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

    public List<Type> getListOfTypes(PokemonDto pokemon){
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

    public Type getTypes(String name){
        var typeName = typeRepository.findByName(name);
        if(typeName == null){
            var fetchedType = typeDtoService.getType(name);
            this.saveType(fetchedType);
            var savedType = typeRepository.findByName(name);
            return savedType;
        }
        return typeName;
    }

    public List<Pokemon> getPokemonsWithType(String name){
        List<Pokemon> matchedPokemon = new ArrayList<>();
        var chosenType = typeRepository.findByName(name);
        if(chosenType == null){
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

    public boolean doesTypeEquals(String type, Pokemon pokemon){
        for(Type typeName : pokemon.getType()){
            if(typeName.getName().equals(type)){
                return true;
            }
        }
        return false;
    }

    public void saveType(Type newType){
        typeRepository.save(newType);
    }
}
