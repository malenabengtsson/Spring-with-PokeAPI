package com.example.pokeapi.services;


import com.example.pokeapi.entities.PokemonEntities.*;
import com.example.pokeapi.entities.Query;
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

    public List<Pokemon> findPokemonByAttributes(String name, String game, String type, String ability) {
        var combination = this.generateCombinations(name, game, type, ability);
        var currentQuery = this.generateQuery(combination, name, game, type, ability);
        var queryExists = queryRepository.findByQueryString(currentQuery);

        if (queryExists.isEmpty()) {
            var matchedPokemons = pokemonService.findPokemon(combination, name, game, type, ability);
            var newQuery = new Query(currentQuery, matchedPokemons);
            this.saveQueryToDb(newQuery);
            return matchedPokemons;

        } else {
            return queryExists.get().getPokemons();
        }

    }

    public GameIndice findGame(String game) {
        var currentQuery = url + "game=" + game.toLowerCase();

        var queryExists = queryRepository.findByQueryString(currentQuery);

        if (queryExists.isEmpty()) {
            var fetchedGame = gameIndiceService.findGame(game.toLowerCase());
            var newQuery = new Query(currentQuery, fetchedGame);
            this.saveQueryToDb(newQuery);
            return fetchedGame;
        }
        return queryExists.get().getGame();
    }

    public Ability findAbility(String ability) {
        var currentQuery = url + "ability=" + ability.toLowerCase();

        var queryExists = queryRepository.findByQueryString(currentQuery);
        if (queryExists.isEmpty()) {
            var fetchedAbility = abilityService.getAbility(ability.toLowerCase());
            var newQuery = new Query(currentQuery, fetchedAbility);
            this.saveQueryToDb(newQuery);
            return fetchedAbility;
        }
        return queryExists.get().getAbility();
    }

    public Type findType(String type) {
        var currentQuery = url + "type=" + type.toLowerCase();

        var queryExists = queryRepository.findByQueryString(currentQuery);
        if (queryExists.isEmpty()) {
            var fetchedType = typeService.getTypes(type.toLowerCase());
            var newQuery = new Query(currentQuery, fetchedType);
            this.saveQueryToDb(newQuery);
            return fetchedType;
        }
        return queryExists.get().getType();
    }

    public String generateCombinations(String name, String game, String type, String ability) {
        if (name == null && game != null && type != null && ability == null) {
            return "GameType";
        } else if (name != null && game != null && type != null && ability == null) {
            return "NameGameType";

        } else if (name != null && game != null && type == null && ability == null) {
            return "NameGame";

        } else if (name != null && game == null && type != null && ability == null) {
            return "NameType";

        } else if (name != null && game != null && type != null && ability != null) {
            return "NameGameTypeAbility";

        } else if (name == null && game != null && type != null && ability != null) {
            return "GameTypeAbility";

        } else if (name != null && game == null && type != null && ability != null) {
            return "NameTypeAbility";

        } else if (name != null && game != null && type == null && ability != null) {
            return "NameGameAbility";
        } else if (name != null && game == null && type == null && ability != null) {
            return "NameAbility";
        } else if (name == null && game == null && type != null && ability != null) {
            return "TypeAbility";
        } else if (name != null & game == null && type == null && ability == null) {
            return "Name";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search parameters doesnt match");
    }

    public String generateQuery(String combination, String name, String game, String type, String ability) {
        String abiltyString = "";
        if (ability != null) {
            abiltyString = ability.replace(" ", "-");
        }

        if (combination.equals("GameType")) {
            return String.format("%sgame=%s&type=%s", url, game, type);
        } else if (combination.equals("NameGameType")) {
            return String.format("%sname=%s&game=%s&type=%s", url, name, game, type);
        } else if (combination.equals("NameGame")) {
            return String.format("%sname=%s&game=%s", url, name, game);
        } else if (combination.equals("NameType")) {
            return String.format("%sname=%s&type=%s", url, name, type);
        } else if (combination.equals("NameGameTypeAbility")) {
            return String.format("%sname=%s&game=%s&type=%s&ability=%s", url, name, game, type, abiltyString);
        } else if (combination.equals("GameTypeAbility")) {
            return String.format("%sgame=%s&type=%s&ability=%s", url, game, type, abiltyString);
        } else if (combination.equals("NameTypeAbility")) {
            return String.format("%sname=%s&type=%s&ability=%s", url, name, type, abiltyString);
        } else if (combination.equals("NameGameAbility")) {
            return String.format("%sname=%s&game=%s&ability=%s", url, name, game, abiltyString);
        } else if (combination.equals("TypeAbility")) {
            return String.format("%stype=%s&ability=%s", url, type, abiltyString);
        } else if (combination.equals("NameAbility")) {
            return String.format("%sname=%s&ability=%s", url, name, abiltyString);
        } else if (combination.equals("Name")) {
            return String.format("%sname=%s", url, name);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search parameters doesnt match");
    }

    public void saveQueryToDb(Query query) {
        queryRepository.save(query);
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
