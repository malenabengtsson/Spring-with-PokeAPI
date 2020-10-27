package com.example.pokeapi.services;

import com.example.pokeapi.dto.PokemonDetailDtos.Type.TypesDto;
import com.example.pokeapi.dto.PokemonDto;
import com.example.pokeapi.entities.Type;
import com.example.pokeapi.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    public Type getType(PokemonDto pokemon){
        for(TypesDto type : pokemon.getTypes()){
            var answer = typeRepository.findByName(type.getType().getName());
            if(answer == null){
                var newType = new Type(type.getType().getName(), type.getType().getUrl());
                this.saveType(newType);
                var fetchedType = typeRepository.findByName(type.getType().getName());
                return fetchedType;
            }
            else{
                return answer;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type cannot be found");
    }

    public void saveType(Type newType){
        typeRepository.save(newType);
    }
}
