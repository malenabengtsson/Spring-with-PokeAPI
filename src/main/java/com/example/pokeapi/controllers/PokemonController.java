package com.example.pokeapi.controllers;

import com.example.pokeapi.dto.PokemonNameAndUrlDto;
import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.services.PokemonDtoService;
import com.example.pokeapi.services.PokemonNamesAndUrlDtoService;
import com.example.pokeapi.services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class PokemonController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private PokemonNamesAndUrlDtoService pokemonNamesAndUrlDtoService;

    @GetMapping("&name={name}")
    public ResponseEntity<List<Pokemon>> findAllPokemons(@PathVariable String name){
        System.out.println("In controller");
        var getPokemon = queryService.findPokemon(name);

        // var pokemons = pokemonService.findAllPokemonWith(name);

         return ResponseEntity.ok(getPokemon);
    }

    @GetMapping("pokemon")
    public ResponseEntity<PokemonNameAndUrlDto> getAllPokemons(){
        var answer = pokemonNamesAndUrlDtoService.getAllPokemons();
        return ResponseEntity.ok(answer);
    }
}
