package com.example.pokeapi.services;


import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.entities.PokemonNamesAndUrl;
import com.example.pokeapi.entities.Query;
import com.example.pokeapi.repositories.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
@ConfigurationProperties(value = "pokemon.api", ignoreUnknownFields = false)
public class QueryService {

    private final RestTemplate restTemplate;
    private String url;
    private List<Pokemon> foundPokemons;


    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private PokemonDtoService pokemonDtoService;

    @Autowired
    private PokemonService pokemonService;

    public QueryService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Pokemon> findPokemonByName(String name, String currentQuery){
        var queryExist = queryRepository.findByQueryString(currentQuery);
        var newQuery = new Query();
        List<Pokemon> foundPokemons = new ArrayList<>();

        if(queryExist.isEmpty()){
            var getPokemonNamesContainingName = pokemonDtoService.findAllPokemonContaining(name);
            if(getPokemonNamesContainingName.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemons found containing " + name);
            }
            else{
                for(PokemonNamesAndUrl pokemon : getPokemonNamesContainingName){
                    System.out.println(pokemon.getName());
                    var pokemonExistInDb = pokemonService.getByName(pokemon.getName());
                    if(pokemonExistInDb == null){
                        currentQuery = url + "pokemon/" + pokemon.getName();
                        var fetchedPokemon = restTemplate.getForObject(currentQuery, Pokemon.class);
                        System.out.println("Fetched" + fetchedPokemon.getName());
                        pokemonService.savePokemon(fetchedPokemon);
                        var savedPokemon = pokemonService.getByName(pokemon.getName());
                        if(savedPokemon != null){
                            System.out.println(savedPokemon.getName() + savedPokemon.getId());
                            foundPokemons.add(savedPokemon);

                        }
                        else{
                            System.out.println(pokemon.getName() + " can not be found in db");
                        }
                    }
                    else{
                        foundPokemons.add(pokemonExistInDb);
                        }
                    }
                newQuery.setPokemons(foundPokemons);
                newQuery.setQueryString(currentQuery);
                this.saveQueryToDb(newQuery);
                foundPokemons.clear();
                }
            return foundPokemons;

            }
        else{
            return queryExist.get().getPokemons();
        }
        }



    public List<Pokemon> findPokemonByAttributes(String name, Integer weight, String type){
       var currentQuery = this.generateQuery(name, weight, type);
      List<Pokemon> foundPokemons;
       if(name != null){
           foundPokemons = this.findPokemonByName(name, currentQuery);
       }
        var queryExists = queryRepository.findByQueryString(currentQuery);
        var newQuery = new Query();

        if(queryExists.isEmpty()){
            /*
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

            return List.of(pokemon);*/
        return null;
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

    public String generateQuery(String name, Integer weight, String type){
        if(name == null && weight != null && type != null){
            return url + "weight=" + weight + "type=" + type;
        }
        else if (name != null && weight != null && type != null ){
            return url + "name=" + name + "weight=" + weight + "type=" + type;
        }
        else if (name != null && weight != null && type == null){
            return url + "name=" + name + "weight=" + weight;
        }
        else if(name != null && weight == null && type != null){
            return url + "name=" + name + "type=" + type;
        }
        else if(name == null && weight == null && type != null){
            return url + "type/" + type;
        }
        else if(name != null & weight == null && type == null){
            return url + "pokemon/" + name;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search parameters doesnt match");
    }
    public List<Pokemon> findPokemonByNameAndWeight(String name, int weight) {
        return null;
    }

    public void saveQueryToDb(Query query){
        queryRepository.save(query);
        // ok
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
