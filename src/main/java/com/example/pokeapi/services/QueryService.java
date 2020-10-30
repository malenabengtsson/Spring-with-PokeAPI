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

    public List<Pokemon> findPokemonByAttributes(String name, String game, String type, String ability){
       var combination = this.generateCombinations(name, game, type, ability);
       var currentQuery = this.generateQuery(combination, name, game, type, ability);
        System.out.println("Combination: " + combination);
        System.out.println("Query: " + currentQuery);

        var queryExists = queryRepository.findByQueryString(currentQuery);

        if(queryExists.isEmpty()){
               var matchedPokemons = pokemonService.findPokemon(combination, name, game, type, ability);
               var newQuery = new Query(currentQuery, matchedPokemons);
               this.saveQueryToDb(newQuery);
               return matchedPokemons;

        }
        else{
            return queryExists.get().getPokemons();
        }

    }

    public String generateCombinations(String name, String game, String type, String ability){
        if(name == null && game != null && type != null && ability == null){
            return "GameType";
        }
        else if (name != null && game != null && type != null && ability == null ){
            return "NameGameType";

        }
        else if (name != null && game != null && type == null && ability == null){
            return "NameWeight";

        }
        else if(name != null && game == null && type != null && ability == null){
            return "NameType";

        }
        else if(name != null && game != null && type != null && ability != null ){
            return "NameGameTypeAbility";

        }
        else if(name == null && game != null && type != null && ability != null ){
            return "GameTypeAbility";

        }
        else if(name == null && game != null && type == null && ability == null ){
            return "GameAbility";

        }
        else if(name != null && game == null && type != null && ability != null){
            return "NameTypeAbility";

        }
        else if(name != null && game != null && type == null && ability != null){
            return "NameGameAbility";
        }
        else if(name == null && game == null && type != null && ability != null){
            return "TypeAbility";
        }
        else if(name == null && game == null && type == null && ability != null){
            return "Ability";
        }
        else if(name == null && game == null && type != null && ability == null){
            return "Type";
        }
        else if(name != null & game == null && type == null && ability == null){
            return "Name";
        }
        else if(name == null & game != null && type == null && ability == null){
            return "Game";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search parameters doesnt match");
    }
    public String generateQuery(String combination, String name, String game, String type, String ability){
        String abiltyString = "";
        if(ability != null){
            abiltyString = ability.replace(" ", "-");
        }

        if(combination.equals("WeightType")){
            return url + "game=" + game + "type=" + type;
        }
        else if (combination.equals("NameWeightType")){
            return url + "name=" + name + "game=" + game + "type=" + type;
        }
        else if (combination.equals("NameWeight")){
            return url + "name=" + name + "game=" + game;
        }
        else if(combination.equals("NameType")){
            return url + "name=" + name + "type=" + type;
        }
        else if(combination.equals("NameWeightTypeAbility")){
            return url + "name=" + name + "game=" + game + "type=" + type + "ability=" + abiltyString;
        }
        else if(combination.equals("WeightTypeAbility")){
            return  url + "game=" + game + "type=" + type + "ability=" + abiltyString;
        }
        else if(combination.equals("WeightAbility")){
            return url +  "game=" + game + "ability=" + abiltyString;
        }
        else if(combination.equals("NameTypeAbility")){
            return url + "name=" + name + "type=" + type + "ability=" + abiltyString;
        }
        else if(combination.equals("NameWeightAbility")){
            return url + "name=" +  name + "game=" + game +  "ability=" + abiltyString;
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
            return url + "game=" + game;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search parameters doesnt match");
    }
    public void saveQueryToDb(Query query){
        queryRepository.save(query);
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
