package com.example.pokeapi.services.PokemonServices;

import com.example.pokeapi.dto.PokemonDetailDtos.GameIndices.GameIndicesPlaceholderDto;
import com.example.pokeapi.dto.PokemonDto;
import com.example.pokeapi.entities.GameIndice;
import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.entities.Type;
import com.example.pokeapi.repositories.GameIndiceRepository;
import com.example.pokeapi.services.PokemonServices.Dtos.GameDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameIndiceService {

    @Autowired
    private GameIndiceRepository gameIndiceRepository;

    @Autowired
    private GameDtoService gameDtoService;

    public List<GameIndice> getGame(PokemonDto pokemon){
        List<GameIndice> gameList = new ArrayList<>();
        for(GameIndicesPlaceholderDto games : pokemon.getGame_indices()){
            var nameOfGame = games.getVersion().getName().replace("-", " ");
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

    public GameIndice findGame(String game){
        var gameExists = gameIndiceRepository.findByGameVersion(game);
        if(gameExists == null){
            var chosenGame = gameDtoService.getGameVersion(game);
            this.saveGame(chosenGame);
            var savedGame = gameIndiceRepository.findByGameVersion(game);
            return savedGame;
        }
        else {
            return gameExists;
        }
    }

    public boolean doesGameEquals(String game, Pokemon pokemon){
        for(GameIndice gameName : pokemon.getGame_indices()){
            if(gameName.getGameVersion().equals(game)){
                return true;
            }
        }
        return false;
    }
    public void saveGame(GameIndice game){
        gameIndiceRepository.save(game);

    }



}
