package com.example.pokeapi.services;

import com.example.pokeapi.dto.PokemonDetailDtos.ResultDto;
import com.example.pokeapi.entities.GameIndice;
import com.example.pokeapi.repositories.GameIndiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@ConfigurationProperties(value = "pokemon.api", ignoreUnknownFields = false)
public class GameDtoService {

     @Autowired
     private GameIndiceRepository gameIndiceRepository;

    private final RestTemplate restTemplate;
    private String url;

    public GameDtoService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public GameIndice getGameVersion(String name){
        var correctName = name.replace(" ", "-");
        var gamesUrl = url + "version/" + correctName;
        var newGameIndice = new GameIndice();
        var gameExists = gameIndiceRepository.findByGameVersion(name);
        if(gameExists == null){
            var fetchedGame = restTemplate.getForObject(gamesUrl, ResultDto.class);
            newGameIndice.setGameVersion(fetchedGame.getName().replace("-", " "));
            newGameIndice.setGameUrl(fetchedGame.getUrl());
            return newGameIndice;
        }
        return null;

    }

    public void setUrl(String url) {
        this.url = url;
    }
}
