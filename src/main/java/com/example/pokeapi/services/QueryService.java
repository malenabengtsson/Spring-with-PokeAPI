package com.example.pokeapi.services;


import com.example.pokeapi.entities.*;
import com.example.pokeapi.repositories.QueryRepository;
import com.example.pokeapi.services.PokemonServices.AbilityService;
import com.example.pokeapi.services.PokemonServices.Dtos.PokemonDtoService;
import com.example.pokeapi.services.PokemonServices.GameIndiceService;
import com.example.pokeapi.services.PokemonServices.PokemonService;
import com.example.pokeapi.services.PokemonServices.TypeService;
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

    @Autowired
    private GameIndiceService gameIndiceService;

    @Autowired
    private AbilityService abilityService;

    @Autowired
    private TypeService typeService;

    public QueryService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Pokemon> findPokemonByAttributes(String name, String game, String type, String ability){
       var combination = this.generateCombinations(name, game, type, ability);
       var currentQuery = this.generateQuery(combination, name, game, type, ability);
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

    public GameIndice findGame(String game){
        var currentQuery = url + "game=" + game.toLowerCase();

        var queryExists = queryRepository.findByQueryString(currentQuery);

        if(queryExists.isEmpty()){
            var fetchedGame = gameIndiceService.findGame(game.toLowerCase());
            var newQuery = new Query(currentQuery, fetchedGame);
            this.saveQueryToDb(newQuery);
            return fetchedGame;
        }
        return queryExists.get().getGame();
    }
    public Ability findAbility(String ability){
        var currentQuery = url + "ability=" + ability.toLowerCase();

        var queryExists = queryRepository.findByQueryString(currentQuery);
        if(queryExists.isEmpty()){
            var fetchedAbility = abilityService.getAbility(ability.toLowerCase());
            var newQuery = new Query(currentQuery, fetchedAbility);
            this.saveQueryToDb(newQuery);
            return fetchedAbility;
        }
        return queryExists.get().getAbility();
    }
    public Type findType(String type){
        var currentQuery = url + "type=" + type.toLowerCase();

        var queryExists = queryRepository.findByQueryString(currentQuery);
        if(queryExists.isEmpty()){
            var fetchedType = typeService.getTypes(type.toLowerCase());
            var newQuery = new Query(currentQuery, fetchedType);
            this.saveQueryToDb(newQuery);
            return fetchedType;
        }
        return queryExists.get().getType();
    }

    public String generateCombinations(String name, String game, String type, String ability){
        if(name == null && game != null && type != null && ability == null){
            return "GameType";
        }
        else if (name != null && game != null && type != null && ability == null ){
            return "NameGameType";

        }
        else if (name != null && game != null && type == null && ability == null){
            return "NameGame";

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
        else if(name != null && game == null && type != null && ability != null){
            return "NameTypeAbility";

        }
        else if(name != null && game != null && type == null && ability != null){
            return "NameGameAbility";
        }
        else if(name == null && game == null && type != null && ability != null){
            return "TypeAbility";
        }
        else if(name != null & game == null && type == null && ability == null){
            return "Name";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search parameters doesnt match");
    }
    public String generateQuery(String combination, String name, String game, String type, String ability){
        String abiltyString = "";
        if(ability != null){
            abiltyString = ability.replace(" ", "-");
        }

        if(combination.equals("GameType")){
            return url + "game=" + game + "type=" + type;
        }
        else if (combination.equals("NameGameType")){
            return url + "name=" + name + "game=" + game + "type=" + type;
        }
        else if (combination.equals("NameGame")){
            return url + "name=" + name + "game=" + game;
        }
        else if(combination.equals("NameType")){
            return url + "name=" + name + "type=" + type;
        }
        else if(combination.equals("NameGameTypeAbility")){
            return url + "name=" + name + "game=" + game + "type=" + type + "ability=" + abiltyString;
        }
        else if(combination.equals("GameTypeAbility")){
            return  url + "game=" + game + "type=" + type + "ability=" + abiltyString;
        }
        else if(combination.equals("NameTypeAbility")){
            return url + "name=" + name + "type=" + type + "ability=" + abiltyString;
        }
        else if(combination.equals("NameGameAbility")){
            return url + "name=" +  name + "game=" + game +  "ability=" + abiltyString;
        }
        else if(combination.equals("TypeAbility")){
            return url + "type="+ type + "ability=" + abiltyString;
        }
        else if(combination.equals("Name")){
            return url + "name=" + name;
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
