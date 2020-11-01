package com.example.pokeapi.services.PokemonServices.Dtos;

import com.example.pokeapi.dto.PokemonNameAndUrlDto;
import com.example.pokeapi.dto.PokemonDetailDtos.ResultDto;
import com.example.pokeapi.entities.PokemonNamesAndUrl;
import com.example.pokeapi.repositories.PokemonNamesAndUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@ConfigurationProperties(value = "pokemon.api")
public class PokemonNamesAndUrlDtoService {

    private final RestTemplate restTemplate;
    private String url;
  //  private String totalAmountOfPokemons;

    @Autowired
    private PokemonNamesAndUrlRepository pokemonNamesAndUrlRepository;

    public PokemonNamesAndUrlDtoService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    public PokemonNameAndUrlDto getAllPokemons(){
        //var urlAllPokemons = url + "pokemon?limit=" + totalAmountOfPokemons;
        var urlAllPokemons = url + "pokemon?limit=1050";
        var list = restTemplate.getForObject(urlAllPokemons, PokemonNameAndUrlDto.class);

        for(ResultDto res : list.getResults()){
            this.addPokemonsAndUrlToDb(res);
        }

        return list;
    }
    public void addPokemonsAndUrlToDb(ResultDto res){
        var entry = new PokemonNamesAndUrl(res.getName(), res.getUrl());
        var pokemonExists = pokemonNamesAndUrlRepository.findByName(entry.getName());
        if(pokemonExists.isEmpty()){
            pokemonNamesAndUrlRepository.save(entry);
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }
/*
    public void setTotalAmountOfPokemons(String totalAmountOfPokemons) {
        this.totalAmountOfPokemons = totalAmountOfPokemons;
    }*/
}
