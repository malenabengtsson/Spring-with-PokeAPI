package com.example.pokeapi.controllers;

import com.example.pokeapi.dto.PokemonDetailDtos.Type.TypesDto;
import com.example.pokeapi.dto.PokemonNameAndUrlDto;
import com.example.pokeapi.entities.GameIndice;
import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PokemonController {

   @Autowired
   private QueryService queryService;

    @Autowired
    private PokemonDtoService pokemonDtoService;

    @Autowired
    private PokemonNamesAndUrlDtoService pokemonNamesAndUrlDtoService;

    @Autowired
    private TypeDtoService typeDtoService;

    @Autowired
    private GameDtoService gameDtoService;

    @GetMapping
    public ResponseEntity<List<Pokemon>> findPokemonByAttributes(@RequestParam(required = false) String name, @RequestParam(required = false) String game, @RequestParam(required = false) String type, @RequestParam(required = false) String ability) {
        var getPokemon = queryService.findPokemonByAttributes(name, game, type, ability);
        return ResponseEntity.ok(getPokemon);
    }

    @GetMapping("/pokemon")
    public ResponseEntity<PokemonNameAndUrlDto> getAllPokemons() {
        var answer = pokemonNamesAndUrlDtoService.getAllPokemons();
        return ResponseEntity.ok(answer);

    }

    @GetMapping("/test")
    public ResponseEntity<Pokemon> test(@RequestParam String name ){
        var answer = pokemonDtoService.findAllPokemonWith(name);
        return ResponseEntity.ok(answer);

    }

    @GetMapping("/version")
    public ResponseEntity<GameIndice> version(@RequestParam String name){
        var answer = gameDtoService.getGameVersion(name);
        return ResponseEntity.ok(answer);
    }

}
