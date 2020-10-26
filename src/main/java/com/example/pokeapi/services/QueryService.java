package com.example.pokeapi.services;

import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.entities.Query;
import com.example.pokeapi.repositories.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@ConfigurationProperties(value = "pokemon.api", ignoreUnknownFields = false)
public class QueryService {

    private final RestTemplate restTemplate;
    private String url;
    private List<Optional<Pokemon>> currentPokemons;

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private PokemonDtoService pokemonDtoService;

    @Autowired
    private PokemonService pokemonService;

    public QueryService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }



    public List<Pokemon> findPokemonByName(String name){
        var currentQuery =  url + "pokemon/" + name;
        var queryExists = queryRepository.findByQueryString(currentQuery);
        var newQuery = new Query();

        if(queryExists.isEmpty()){
            var pokemon = restTemplate.getForObject(currentQuery, Pokemon.class);
            for(Pokemon poke: List.of(pokemon)){
                pokemonService.savePokemon(poke);
            }

            for(Pokemon poke : List.of(pokemon)){
                var pokemonWithName = pokemonService.getByName(poke.getName());
                if(pokemonWithName.isEmpty()){
                    System.out.println("Empty");
                }
                else{
                    System.out.println("name success");
                    // var newQuery = new Query(currentQuery, pokemonWithName);
                    newQuery.setPokemons(pokemonWithName);
                    newQuery.setQueryString(currentQuery);
                    this.saveQueryToDb(newQuery);
                }
            }

            return List.of(pokemon);
        }
        else{
            System.out.println("Get id and fetch pokemons");
           var id =  queryExists.get().getId();
           var chosenQuery = queryRepository.findById(id.toString());
            for(Pokemon poke : chosenQuery.get().getPokemons()){
                System.out.println(poke.getName());
            }
            var pokemons = chosenQuery.get().getPokemons();

            return pokemons;
        }

    }

    public void saveQueryToDb(Query query){
        queryRepository.save(query);
        // ok
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
