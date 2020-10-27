package com.example.pokeapi.services;

import com.example.pokeapi.dto.PokemonDetailDtos.GameIndices.GameIndicesDto;
import com.example.pokeapi.dto.PokemonDto;
import com.example.pokeapi.entities.GameIndice;
import com.example.pokeapi.repositories.GameIndiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameIndiceService {

    @Autowired
    private GameIndiceRepository gameIndiceRepository;

    public List<GameIndice> getGame(PokemonDto pokemon){
        List<GameIndice> gameList = new ArrayList<>();
        for(GameIndicesDto games : pokemon.getGame_indices()){
            var nameOfGame = games.getVersion().getName().replace("-", " ");
            System.out.println(nameOfGame);
           var gameExists = gameIndiceRepository.findByGameVersion(nameOfGame);
           if(gameExists == null){
               var newGame = new GameIndice(nameOfGame, games.getVersion().getUrl());
               this.saveGame(newGame);
               var savedGame = gameIndiceRepository.findByGameVersion(nameOfGame);
               gameList.add(savedGame);

           }
           else{
               gameList.add(gameExists);
           }
        }
        return gameList;
    }

    public void saveGame(GameIndice game){
        gameIndiceRepository.save(game);

    }



}