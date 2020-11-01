package com.example.pokeapi.services.PokemonServices.Dtos;

import com.example.pokeapi.dto.PokemonDetailDtos.Type.LinkedPokemonDto;
import com.example.pokeapi.dto.PokemonDetailDtos.Type.TypeDto;
import com.example.pokeapi.dto.PokemonDetailDtos.Type.TypesDto;
import com.example.pokeapi.entities.Type;
import com.example.pokeapi.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@ConfigurationProperties(value = "pokemon.api", ignoreUnknownFields = false)
public class TypeDtoService {

    private final RestTemplate restTemplate;
    private String url;

    @Autowired
    private TypeRepository typeRepository;

    public TypeDtoService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Type getType(String type){
        var typeUrl = url + "type/" + type;

        var typeExist = typeRepository.findByName(type);
        if(typeExist == null){
            var newType = new Type();
            TypesDto fetchedType = null;
            try{
                 fetchedType = restTemplate.getForObject(typeUrl, TypesDto.class);
            } catch(Exception exception){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No type found with name: " + type);
            }

            List<String> linkedPokemonNames = new ArrayList<>();

            newType.setName(fetchedType.getName());
            for(TypeDto pokemonType : fetchedType.getPokemon()){
                linkedPokemonNames.add(pokemonType.getPokemon().getName());
            }
            newType.setLinkedPokemons(linkedPokemonNames);
            return newType;
        }
        return typeExist;

    }

    public void setUrl(String url) {
        this.url = url;
    }
}
