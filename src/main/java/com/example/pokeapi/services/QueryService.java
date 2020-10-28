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

import java.util.List;


@Service
@ConfigurationProperties(value = "pokemon.api", ignoreUnknownFields = false)
public class QueryService {

    private final RestTemplate restTemplate;
    private String url;


    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private PokemonDtoService pokemonDtoService;

    @Autowired
    private PokemonService pokemonService;

    public QueryService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Pokemon> findPokemonByAttributes(String name, Integer maxWeight, String type, String ability){
       var combination = this.generateCombinations(name, maxWeight, type, ability);
       var currentQuery = this.generateQuery(combination, name, maxWeight, type, ability);
        System.out.println("Combination: " + combination);
        System.out.println("Query: " + currentQuery);

        var queryExists = queryRepository.findByQueryString(currentQuery);

        if(queryExists.isEmpty()){
               var matchedPokemons = pokemonService.findPokemon(combination, currentQuery, name, maxWeight, type, ability);
               var newQuery = new Query(currentQuery, matchedPokemons);
               this.saveQueryToDb(newQuery);
               return matchedPokemons;

        }
        else{
            return queryExists.get().getPokemons();
        }

    }

    public String generateCombinations(String name, Integer maxWeight, String type, String ability){
        if(name == null && maxWeight != null && type != null && ability == null){
            return "WeightType";
        }
        else if (name != null && maxWeight != null && type != null && ability == null ){
            return "NameWeightType";

        }
        else if (name != null && maxWeight != null && type == null && ability == null){
            return "NameWeight";

        }
        else if(name != null && maxWeight == null && type != null && ability == null){
            return "NameType";

        }
        else if(name != null && maxWeight != null && type != null && ability != null ){
            return "NameWeightTypeAbility";

        }
        else if(name == null && maxWeight != null && type != null && ability != null ){
            return "WeightTypeAbility";

        }
        else if(name == null && maxWeight != null && type == null && ability == null ){
            return "WeightAbility";

        }
        else if(name != null && maxWeight == null && type != null && ability != null){
            return "NameTypeAbility";

        }
        else if(name != null && maxWeight != null && type == null && ability != null){
            return "NameWeightAbility";
        }
        else if(name == null && maxWeight == null && type != null && ability != null){
            return "TypeAbility";
        }
        else if(name == null && maxWeight == null && type == null && ability != null){
            return "Ability";
        }
        else if(name == null && maxWeight == null && type != null){
            return "Type";
        }
        else if(name != null & maxWeight == null && type == null){
            return "Name";
        }
        else if(name == null & maxWeight != null && type == null){
            return "Weight";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search parameters doesnt match");
    }
    public String generateQuery(String combination, String name, Integer maxWeight, String type, String ability){
        String abiltyString = "";
        if(ability != null){
            abiltyString = ability.replace(" ", "-");
        }

        if(combination.equals("WeightType")){
            return url + "maxWeight=" + maxWeight + "type=" + type;
        }
        else if (combination.equals("NameWeightType")){
            return url + "name=" + name + "maxWeight=" + maxWeight + "type=" + type;
        }
        else if (combination.equals("NameWeight")){
            return url + "name=" + name + "maxWeight=" + maxWeight;
        }
        else if(combination.equals("NameType")){
            return url + "name=" + name + "type=" + type;
        }
        else if(combination.equals("NameWeightTypeAbility")){
            return url + "name=" + name + "maxWeight=" + maxWeight + "type=" + type + "ability=" + abiltyString;
        }
        else if(combination.equals("WeightTypeAbility")){
            return  url + "maxWeight=" + maxWeight + "type=" + type + "ability=" + abiltyString;
        }
        else if(combination.equals("WeightAbility")){
            return url +  "maxWeight=" + maxWeight + "ability=" + abiltyString;
        }
        else if(combination.equals("NameTypeAbility")){
            return url + "name=" + name + "type=" + type + "ability=" + abiltyString;
        }
        else if(combination.equals("NameWeightAbility")){
            return url + "name=" +  name + "maxWeight=" + maxWeight +  "ability=" + abiltyString;
        }
        else if(combination.equals("TypeAbility")){
            return url + "type="+ type + "ability=" + abiltyString;
        }
        else if(combination.equals("Ability")){
            return url + "ability=" + abiltyString;
        }
        else if(combination.equals("Type")){
            return url + "type=" + type;
        }
        else if(combination.equals("Name")){
            return url + "name=" + name;
        }
        else if(combination.equals("Weight")){
            return url + "maxWeight=" + maxWeight;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search parameters doesnt match");
    }
    public void saveQueryToDb(Query query){
        queryRepository.save(query);
        // ok
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
