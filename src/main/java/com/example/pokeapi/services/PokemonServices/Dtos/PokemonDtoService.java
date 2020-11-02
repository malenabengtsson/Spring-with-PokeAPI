package com.example.pokeapi.services.PokemonServices.Dtos;

import com.example.pokeapi.dto.PokemonDto;
import com.example.pokeapi.entities.PokemonEntities.Pokemon;
import com.example.pokeapi.entities.PokemonEntities.PokemonNamesAndUrl;
import com.example.pokeapi.repositories.PokemonNamesAndUrlRepository;
import com.example.pokeapi.repositories.PokemonRepository;
import com.example.pokeapi.services.PokemonServices.AbilityService;
import com.example.pokeapi.services.PokemonServices.GameIndiceService;
import com.example.pokeapi.services.PokemonServices.TypeService;
import com.example.pokeapi.services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


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

    @Autowired
    private TypeService typeService;

    @Autowired
    private AbilityService abilityService;

    @Autowired
    private GameIndiceService gameIndiceService;

    public PokemonDtoService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<PokemonNamesAndUrl> findAllPokemonContaining(String name) {
        var pokemons = pokemonNamesAndUrlRepository.findAll();
        pokemons = pokemons.stream().filter(pokemon -> pokemon.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        return pokemons;
    }

    public Pokemon findAllPokemonWith(String name) {
        var urlWithPokemon = url + "pokemon/" + name;
        var pokemon = restTemplate.getForObject(urlWithPokemon, PokemonDto.class);
        var type = typeService.getListOfTypes(pokemon);
        var ability = abilityService.getAbilityFrom(pokemon);
        var gameList = gameIndiceService.getGame(pokemon);


        return new Pokemon(pokemon.getOrder(), pokemon.getName(), pokemon.getHeight(), pokemon.getWeight(), type, ability , gameList);
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
