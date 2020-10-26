package com.example.pokeapi.controllers;

import com.example.pokeapi.dto.PokemonDetailDtos.TypeDto;
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
@RequestMapping("/api/v1")
public class PokemonController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private PokemonNamesAndUrlDtoService pokemonNamesAndUrlDtoService;

    @GetMapping
    //Must be logged in
    public ResponseEntity<List<Pokemon>> findPokemonByAttributes(@RequestParam(required = false) String name, @RequestParam(required = false) Integer weight, @RequestParam(required = false) String type) {
        var getPokemon = queryService.findPokemonByAttributes(name, weight, type);
        return ResponseEntity.ok(getPokemon);
    }

    @GetMapping("/pokemon")
    public ResponseEntity<PokemonNameAndUrlDto> getAllPokemons() {
        var answer = pokemonNamesAndUrlDtoService.getAllPokemons();
        return ResponseEntity.ok(answer);

    }
}
