package com.example.pokeapi.services;

import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.entities.PokemonNamesAndUrl;
import com.example.pokeapi.repositories.PokemonNamesAndUrlRepository;
import com.example.pokeapi.repositories.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ConfigurationProperties(value = "pokemon.api", ignoreUnknownFields = false)
public class PokemonDtoService {
    private final RestTemplate restTemplate;
    private String url;


    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private PokemonNamesAndUrlRepository pokemonNamesAndUrlRepository;

    @Autowired
    private QueryService queryService;
    public PokemonDtoService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<PokemonNamesAndUrl> findAllPokemonContaining(String name){
        var pokemons = pokemonNamesAndUrlRepository.findAll();
        pokemons = pokemons.stream().filter(pokemon -> pokemon.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        return pokemons;
    }

    public List<Pokemon> findAllPokemonWith(String name){
        var urlWithPokemon = url + "pokemon/" + name;
        System.out.println(urlWithPokemon);
        var pokemon = restTemplate.getForObject(urlWithPokemon, Pokemon.class);
        if(pokemon == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemons found");
        }
        else{

            System.out.println("Pokemon " + pokemon.getName());
            return List.of(pokemon);
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
